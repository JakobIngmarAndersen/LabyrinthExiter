import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Labyrint {
  protected int columns;
  protected int rows;
  protected Rute [][] grid;
  protected Lenkeliste<String> exitPath;
  protected Lenkeliste<Lenkeliste<Rute>> exitList;

  private Labyrint(int rows, int columns, Rute[][] grid) {
    this.columns = columns;
    this.rows = rows;
    this.grid = grid;
    this.exitPath = new Lenkeliste<String>();
    this.exitList = new Lenkeliste<Lenkeliste<Rute>>();
  }

  public static Labyrint lesFraFil(File file) throws FileNotFoundException{
    //bruker scanner for aa lese inn fil
    Scanner scan = new Scanner(file);
    String firstLine = scan.nextLine();
    String [] data = firstLine.split(" ");
    int rows = Integer.parseInt(data[0]);
    int columns = Integer.parseInt(data[1]);
    if (rows < columns){
      int tmp = rows; 
      rows = columns; 
      columns = tmp; 
      
    }

    Rute[][] grid = new Rute[rows][columns];
    Labyrint myLabyrint = new Labyrint(rows, columns, grid); //lager myLabyrint fra kontruktoeren

    char white = '.';
    String activeLine;
    char activeChar;


    //dobbel for-loekke som finner hvert rutefelt og sjekker om det skal vaere sort, hvit eller appning
    for (int y = 0; y < columns; y++) {
      activeLine = scan.nextLine();
      for (int x = 0; x < rows; x++) {
        activeChar = activeLine.charAt(x);
        if (activeChar == white) {
          if (y == 0 || y == columns - 1|| x == 0 || x == rows - 1) { //ser om "." er i utkanten av labyrint altsaa utgang
            grid[x][y] = new Aapning(x,y, myLabyrint);
          }
          else {
            grid[x][y] = new HvitRute(x,y, myLabyrint); //Vanlig hvit naar det ikke er utgang
          }
        }
        else {
          grid[x][y] = new SortRute(x,y, myLabyrint); //er den ikke hvit er den sort
        }
      }
    }
    //Setter opp naboer
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < columns; y++) {
        if (x > 0){
          grid[x][y].nW = grid[x - 1][y];
        }
        if (x < rows - 1){
          grid[x][y].nE = grid[x + 1][y];
        }
        if (y > 0){
          grid[x][y].nN = grid[x][y - 1];
        }
        if (y < columns - 1){
          grid[x][y].nS = grid[x][y + 1];
        }
      }
    }
    return myLabyrint;
  }

  public Lenkeliste<String> finnUtveiFra(int gridRow, int gridColumn) {
    this.exitList = new Lenkeliste<Lenkeliste<Rute>>();
    Lenkeliste<String> tmp = new Lenkeliste<String>(); //tmp liste for return for klargjoering av ny exitPath
    this.grid[gridRow][gridColumn].finnUtvei(this.grid[gridRow][gridColumn]);
    for (int x = 0; x < this.rows; x++) { //resetter visitedCounter for aa kunne finne utgang fra flere ruter
      for (int y = 0; y < this.columns; y++) {
          this.grid[x][y].visitedCounter = 0;
      }
    }
    for (int i = 0; i<this.exitPath.stoerrelse(); i++){ //kopierer exitPath til tmp
      tmp.leggTil(this.exitPath.hent(i));
    }
    this.exitPath = new Lenkeliste<String>();
  
    return tmp; 
  }

  //toString for aa kunne tegne ut labyrinten i terminalen
  public String toString(){
    String board = "";
    for (int y = 0; y < columns; y++) {
      String line = "";
      for (int x = 0; x < rows; x++) {
        line += (this.grid[x][y].tilTegn());
        }
        board += line + "\n";
      }
    return board;
  }

}
