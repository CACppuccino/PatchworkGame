package comp1140.ass2;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;


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
    private final Group sqiltBoard = new Group();
    private final Group tilesArea = new Group();
    ArrayList<Character> candi;
    TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {//throws Exception  {
        final double[] rotation = {0,90,180,270};
        // FIXME Task 5: implement the simple placement viewer
        HBox mPlacement = new HBox();
        Label error = new Label("invalide placement");
        if (placement.length()!=4 )
        {
//            throw new Exception("invalid placement");
            error.setTextFill(Color.valueOf("#3367D6"));
            mPlacement.getChildren().add(error);
            mPlacement.setLayoutX(300);
            mPlacement.setLayoutY(VIEWER_HEIGHT-30);
        }
        else {

            char tileName = placement.charAt(0),col = placement.charAt(2),row = placement.charAt(1),
                    rotate = placement.charAt(3) ;
//            if ()
            /*get the image path*/
            String tileN = tileName+(tileName>'a' && tileName<'h'?"_.png":".png");
            String tilePath = "file:"+(new File("src/comp1140/ass2/gui/assets/"+tileN)).getAbsolutePath();
            Image tile = new Image(tilePath);
            ImageView tileView = new ImageView(tile);
            /*resize the tile to fit the squiltboard*/
            double w = tile.getWidth()/50,h = tile.getHeight()/50;
            w *= 30; h*=30;
            if (rotate>='E' && rotate<='H')
            {
                tileView.setRotationAxis(Rotate.Y_AXIS);
                rotate = (char)(rotate-'E'+'A');
                tileView.setRotate(180);
            }
            tileView.setFitWidth(w);tileView.setFitHeight(h);
            /*rotation*/
            /*haven't included the E-H*/
            ObjectProperty<Image> tmp = tileView.imageProperty();
//            ImageView tmp = new ImageView();
            System.out.println(rotate);
            double r = rotation[rotate-'A'];
            ImageView tileNView = new ImageView();
            tileNView.setImage(tmp.getValue());
//            tileNView.setRotate(r);
            /* set the coordinate according to the input* */
            int x = (row-'A')*30,y = (col - 'A')*30;
            tileNView.setFitHeight(h);tileNView.setFitWidth(w);
            //indicates which board is going to be placed on the tile
//            int player = State.check_turn()==1?0:601;
            int player = 1;
            mPlacement.setLayoutX(player+20+x);mPlacement.setLayoutY(290+y);

            mPlacement.getChildren().add(tileNView);

        }
        controls.getChildren().add(mPlacement);
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
//        root.getChildren().add(sqiltBoard);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patchwork Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        timeBoard();
        timeToken1(0);
        squiltBoard1();
        squiltBoard2();
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
        Rectangle[][] sq = new Rectangle[9][9];
        HBox sq1 = new HBox();
        Random gen = new Random();
        sq1.setPrefSize(280,280);
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                sq[i][j] = new Rectangle(20+i*30,290+j*30,30,30);
                sq[i][j].setStroke(Color.color((double)i/9,(double) j/9,(double)(i+j)/18));
                sq[i][j].setFill(Color.WHEAT);
                root.getChildren().add(sq[i][j]);
            }
        }
        sq1.setLayoutX(20);sq1.setLayoutY(290);
//        sqiltBoard.getChildren().add(sq1);
    }

    //displays the squilt board for player 2.
    public void squiltBoard2(){
        Rectangle[][] sq2 = new Rectangle[9][9];
        HBox sq1 = new HBox();
        sq1.setPrefSize(280,280);
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                sq2[i][j] = new Rectangle(621+i*30,290+j*30,30,30);
                sq2[i][j].setStroke(Color.color((double)i/9,(double) j/9,(double)(i+j)/18));
                root.getChildren().add(sq2[i][j]);
            }
        }
        sq1.setLayoutX(20);sq1.setLayoutY(290);
    }

    //displays the time board for players.
    public void timeBoard(){
        File imgTimeboard = new File("src/comp1140/ass2/gui/assets/timeBoard.png");
        String tbPath = new String("file:"+imgTimeboard.getAbsolutePath());
        Image tb = new Image(tbPath);
        ImageView tbView = new ImageView();
        tbView.setImage(tb);
        tbView.setFitHeight(270);
        tbView.setFitWidth(270);
        HBox timeboard = new HBox();
        timeboard.getChildren().add(tbView);
        timeboard.setLayoutX(331.5);
        timeboard.setLayoutY(290);
        controls.getChildren().add(timeboard);

    }

    //displays the time token of player1,
    // call the drag() when token is dragged.
    public void timeToken1(double steps){
        Circle tt1 = new Circle();
        tt1.setRadius(10);
        tt1.setCenterX(321);
        tt1.setCenterY(300);
        tt1.setFill(Color.YELLOW);
        HBox tT1 = new HBox();
        root.getChildren().add(tt1);

    }

    //displays the time token of player2,
    // call the drag() when token is dragged.
    public void timeToken2(double steps){}

//    displays the candidates area which shows the three available tiles.
    public void candidateArea(){

    }

    // displays the two button, undo and confirm, call the relevant functions
    // about the interaction
    public void clickArea(){}


}
