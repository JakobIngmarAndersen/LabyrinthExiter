import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.*;

import java.io.File;
import java.util.List;
import javafx.stage.FileChooser;


public class GUI extends Application {
    public Labyrint activeLab; 
    public Rute brett[];

    final static String sort = "-fx-background-color: #000000;"; 
    final static String hvit = "-fx-background-color: #FFFFFF;"; 
    final static String blah = "-fx-background-color: #FF00FF;"; 
    static int solveCounter = 0;
    public GridPane labGrid;
    public GridPane solvedGrid;
    public GridPane labyrintknapper;
    public Lenkeliste<String> exits;
    public Text textbox;
    
    
    @Override
    public void start(Stage teater) { 
        labGrid = new GridPane(); 
        labGrid.setGridLinesVisible(true);
        labGrid.setLayoutX(20);
        labGrid.setLayoutY(150);

        solvedGrid = new GridPane();
        solvedGrid.setGridLinesVisible(false);
        solvedGrid.setLayoutX(20);
        solvedGrid.setLayoutY(150);

        solvedGrid = new GridPane();
        solvedGrid.setGridLinesVisible(false);
        solvedGrid.setLayoutX(20);
        solvedGrid.setLayoutY(150);

        textbox = new Text("Velkommen til labyrint-loeseren"); 
        textbox.setFont(new Font(20));
        textbox.setX(20);
        textbox.setY(20);


        LabVelger labVelger = new LabVelger();
        Button button1_1 = new Button("1");
        Button button1_2 = new Button("2");
        Button button1_3 = new Button("3");
        Button button1_4 = new Button("4");
        Button button1_5 = new Button("5");
        Button button1_6 = new Button("6");
        Button button1_7 = new Button("7");

        button1_1.setOnAction(labVelger);
        button1_2.setOnAction(labVelger);
        button1_3.setOnAction(labVelger);
        button1_4.setOnAction(labVelger);
        //button1_5.setOnAction(labVelger);
        //button1_6.setOnAction(labVelger);
        button1_7.setOnAction(labVelger);
        
        labyrintknapper = new GridPane();
        labyrintknapper.setGridLinesVisible(true);

        labyrintknapper.add(button1_1, 0, 0);
        labyrintknapper.add(button1_2, 1, 0);
        labyrintknapper.add(button1_3, 2, 0);
        labyrintknapper.add(button1_4, 3, 0);
        labyrintknapper.add(button1_5, 4, 0);
        labyrintknapper.add(button1_6, 5, 0);
        labyrintknapper.add(button1_7, 6, 0);
        labyrintknapper.setLayoutX(40);
        labyrintknapper.setLayoutY(40);

        Pane kulisser = new Pane();
        kulisser.setPrefSize(666, 999);
        kulisser.getChildren().add(textbox);
        kulisser.getChildren().add(labyrintknapper);
        kulisser.getChildren().add(labGrid);
        kulisser.getChildren().add(solvedGrid);
        
        
        Scene scene = new Scene(kulisser);
        teater.setTitle("Trykk paa labyrint du vil ha loest og saa rute du vil loese fra ");
        teater.setScene(scene);
        teater.show();  

    }

    public class GUIrute extends Button {
        char merke = ' ';
        GUIrute(){
            super(" "); 
            setFont(new Font(50));
            setPrefSize(20, 20);
        }
        void settMerke(char c) {
            setText(""+c);
            merke = c; 
        }
    }

    public class LabVelger implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            try{
                labyrintknapper.getChildren().remove(8);
                
            }
            catch(Exception q){

            }
            textbox.setText("");
            solvedGrid.getChildren().clear();
            Button clicked = (Button) e.getSource();
            String filename = (clicked.getText() + ".in");
            File file = new File(filename);
            
            gridMaker(file); 
        }
    }

   


    public void gridMaker(File file){
        
        labGrid.getChildren().clear();
         
        RuteVelger valgtRute = new RuteVelger();
        try{
            activeLab = Labyrint.lesFraFil(file);
        }
        catch(Exception ex){
            System.out.println("Labyrint ikke funnet");
        }
        for (int y = 0; y < activeLab.columns; y++){
            for (int x = 0; x < activeLab.rows; x++){
                Button button = new Button();
                if (!activeLab.grid[x][y].black){
                    button.setOnAction(valgtRute);
                    button.setStyle(hvit);
                }
                else {
                    button.setStyle(sort);
                }
                labGrid.add(button, x, y);   
            }
        }

    }

    public void gridSolver(){
        solvedGrid.getChildren().clear();
        textbox.setText("Antall loesninger: " + Integer.toString(activeLab.exitList.stoerrelse()));

        Lenkeliste<Lenkeliste<Rute>> exitliste = activeLab.exitList; 
        
        boolean[][] rutegrid = losningStringTilTabell(exitliste.hent(solveCounter), activeLab);
        solveCounter ++;
        for (int y = 0; y < activeLab.columns; y++){
            for (int x = 0; x < activeLab.rows; x++){
                Button button = new Button();
                if (activeLab.grid[x][y].black){
                    button.setStyle(sort);
                }
                else if (rutegrid[x][y]) {
                    button.setStyle(blah);
                }
                else {
                    button.setManaged(false);
                }
                solvedGrid.add(button, x, y); 
            }  
        }
        if (solveCounter == exitliste.stoerrelse()){
            solveCounter = 0; 
            labyrintknapper.getChildren().remove(8);
        }
         
        
    }

    public class LosningVelger implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
            
            gridSolver();
        }
    }

    public class RuteVelger implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e){
           

            LosningVelger losningVelger = new LosningVelger();
            Button nextsolve = new Button("Neste losning");
            nextsolve.setOnAction(losningVelger);
            nextsolve.setLayoutX(200);
            nextsolve.setLayoutY(40);
            labyrintknapper.add(nextsolve, 7, 0);

            
            Button clicked = (Button) e.getSource();
            int y = (labGrid.getRowIndex(clicked));
            int x = labGrid.getColumnIndex(clicked);

            Lenkeliste<String> exits = activeLab.finnUtveiFra(x, y);
            
            gridSolver();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    static boolean[][] losningStringTilTabell(Lenkeliste<Rute> exit, Labyrint labyrint) {
        boolean[][] losning = new boolean[labyrint.rows][labyrint.columns];
        
        for (int i = 0; i<exit.stoerrelse(); i++) {

            int tmpx = exit.hent(i).x;
            int tmpy = exit.hent(i).y;
            losning[tmpx][tmpy] = true; 
        }


        
        // java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        // java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
        // while (m.find()) {
        //     int x = Integer.parseInt(m.group(1));
        //     int y = Integer.parseInt(m.group(2));
        //     losning[y][x] = true;
        // }
        return losning;
    }
}