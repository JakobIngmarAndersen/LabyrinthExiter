import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.event.*;
import java.io.File;
import java.util.List;


public abstract class Application {
    abstract void start(Stage primaryStage);
    /* Her plasseres GUI-initieringen. */

    public static void launch(String[] args) {
    /* Kalles fra 'main' for å starte FX. */
    }
}

class Scene {
    Scene(Pane kulisser) {
    /* Lag en scene med angitte kulisser. */
    }
}
    
class Stage {
    void setScene(Scene value) {
    /* Angir hvilken scene som skal vises. */
    }
    void setTitle(String value) {
    /* Angir tittel til GUI-vinduet. */
    }
    void show() {
    /* Viser det som er i GUI-vinduet. */
    }
}
    
class Platform {
    static void exit() {
    /* Avslutter JavaFX på en pen måte. */
    }
    static void runLater(Runnable runnable) {
    /* Opprett en ny Event som vil bli kjørt en gang. */
    }
}

class Pane {
    List getChildren() {
    /* Få listen der kulisseelementene ligger. */
    }
    void setPrefSize(double prefWidth, double prefHeight) {
    /* Angi et ønske om størrelsen. */
    }
}

class GridPane extends Pane {
    void add(Object elem, int col, int row) {
    /* Setter inn i posisjon [col,row] i rutenettet. */
    }
    void setGridLinesVisible(boolean value) {
    /* Angi om selve rutenettet skal tegnes. */
    }
    void setLayoutX(double value) {
    /* Angi ønsket x-koordinat for øvre venstre hjørne. */
    }
    void setLayoutY(double value) {
    /* Angi ønsket y-koordinat for øvre venstre hjørne. */
    }
}

class FileChooser {
    File showOpenDialog(Stage teater) {
    /* Åpner et dialogvindu for å velge en fil. */
    }
}

interface EventHandler {
    void handle(ActionEvent event);
    /* Angi hva som skal skje når hendelsen inntreffer. */
}

class Button {
    Button(String text) {
         /* Lager en bryter med angitt tekst. */
    }
    void setFont(Font value) {
    /* Angi font for teksten. */
    }
    void setOnAction(EventHandler value) {
    /* Angi hvem som skal lytte etter trykk. */
    }
    void setPrefSize(double prefWidth, double prefHeight) {
    /* Angi ønsket størrelse for bryteren. */
    }
    void setText(String value) {
    /* Erstatt teksten med en ny. */
    }
    void setLayoutX(double value) {
    /* Angi x-koordinaten til første tegn i teksten. */
    }
    void setLayoutY(double value) {
    /* Angi y-koordinaten til grunnlinjen i teksten. */
    }
}

class Rectangle {
    Rectangle(double width, double height) {
    /* Opprett et rektangel med gitte dimensjoner. */
    }
    void setFill(Color value) {
    /* Angi rektangelets farge. */
    }
    void setStroke(Color value) {
    /* Angi farge på rektangelets rand. */
    }
    void setStrokeWidth(double value) {
    /* Angi hvor tykk rektangelets rand skal tegnes. */
    }
    void setX(double value) {
    /* Angi x-koordinaten til øvre venstre hjørne. */
    }
    void setY(double value) {
    /* Angi y-koordinaten til øvre venstre hjørne. */
    }
}

class Color {
    final static Color BLACK = rgb(0, 0, 0);
    final static Color WHITE = rgb(255, 255, 255);
    /* De to vanligste fargene. */
    static Color rgb(int red, int green, int blue) {
    /* Lager en farge basert på RGB-verdier. */
    return BLACK;
    }
}

class Text {
    Text(String text) {
    /* En tekst med angitt innhold. */
    }
    void setFont(Font value) {
    /* Angi font for teksten. */
    }
    void setText(String value) {
    /* Erstatt teksten med en ny. */
    }
    void setX(double value) {
    /* Angi x-koordinaten til første tegn i teksten. */
    }
    void setY(double value) {
    /* Angi y-koordinaten til grunnlinjen i teksten. */
    }
}

class Font {
    Font(double size) {
     /* Hent systemfonten i angitt størrelse. */
    }
}
