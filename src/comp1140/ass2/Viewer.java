package comp1140.ass2;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


/**
 * A very simple viewer for piece placements in the Patchwork game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {
    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        // FIXME Task 5: implement the simple placement viewer

    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Display");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(250);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
        root.getChildren().add(controls);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patchwork Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        timeBoard();
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //describe the window attributes
//    public void canvas(){
//
//    }

    // show the final window when the game ends.Including two players'
    // socres and who wins the game. Show a restart button as well.
    public void finalWindow(){

    }

    //displays the squilt board for player 1.
    public void squiltBoard1(){

    }

    //displays the squilt board for player 2.
    public void squiltBoard2(){}

    //displays the time board for players.
    public void timeBoard(){
        Image tb = new Image("file:./gui/assets/timeBoard.png");//("../gui/assets/timeBoard.png");
        ImageView tbView = new ImageView();
        tbView.setImage(tb);
        tbView.setFitHeight(300);
        tbView.setFitWidth(300);
        HBox timeboard = new HBox();
        timeboard.getChildren().add(tbView);
        timeboard.setLayoutX(200);
        timeboard.setLayoutY(500);
        controls.getChildren().add(timeboard);

    }

    //displays the time token of player1,
    // call the drag() when token is dragged.
    public void timeToken1(){}

    //displays the time token of player2,
    // call the drag() when token is dragged.
    public void timeToken2(){}

//    displays the candidates area which shows the three available tiles.
    public void candidateArea(){}

    // displays the two button, undo and confirm, call the relevant functions
    // about the interaction
    public void clickArea(){}


}
