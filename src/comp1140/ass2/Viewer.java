package comp1140.ass2;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.awt.*;
import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static comp1140.ass2.PatchworkGame.*;


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
    private static final int BOARD1X = 20;
    private static final int BOARD2X = 621;
    private static final int BOARDY = 290;
    private static final int BOARD_SIZE = 270;

    private static String c;
    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group sqiltBoard = new Group();
    private final Group tilesArea = new Group();
    ArrayList<Character> candi;
    TextField textField;

    static Text turn;
    static Button confirm;
    static Button advance;
    static Circle tt1;
    static Circle tt2;
    static String p = "";

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        controls.getChildren().clear();
        for (int i = 0; i < placement.length(); ) {
            if (placement.charAt(i) == '.') {
                i++;
                continue;
            }
            String p = placement.substring(i, i + 4);
            final double[] rotation = {0, 90, 180, 270, 0, 90, 180, 270};
            // FIXME Task 5: implement the simple placement viewer
            HBox mPlacement = new HBox();
            Label error = new Label("invalide placement");

            char tileName = p.charAt(0), col = p.charAt(2), row = p.charAt(1),
                    rotate = p.charAt(3);
//
            /*get the image path*/
            String tileN = tileName + (tileName > 'a' && tileName < 'h' ? "_.png" : ".png");
            String tilePath = "file:" + (new File("src/comp1140/ass2/gui/assets/" + tileN)).getAbsolutePath();
            Image tile = new Image(tilePath);
            ImageView tileView = new ImageView(tile);
            /*resize the tile to fit the squiltboard*/

            double w = tile.getWidth() / 50, h = tile.getHeight() / 50;
            w *= 30;
            h *= 30;
            tileView.setFitWidth(w);
            tileView.setFitHeight(h);
            /*rotation*/
            double r = rotation[rotate - 'A'];
            if (rotate > 'D') {
                tileView.setScaleX(-1);
            }
            tileView.setRotate(r);
            /* set the coordinate according to the input* */
            int x = (row - 'A') * 30;
            int y = (col - 'A') * 30;
            if ((int) (r / 90) % 2 == 1) {
                x += (int) (h / 2 - w / 2);
                y += (int) (w / 2 - h / 2);
            }
            //indicates which board is going to be placed on the tile
            //int player = State.check_turn()==1?0:601;
            int player = 1;
            mPlacement.setLayoutX(player + 20 + x);
            mPlacement.setLayoutY(290 + y);
            mPlacement.getChildren().add(tileView);

            controls.getChildren().add(mPlacement);
            i += 4;
        }
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
                System.out.println(new String(three));
                if (PatchworkGame.isPlacementValid(c, textField.getText())) System.out.println("b");
                clickArea();

            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(250);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        root.getChildren().add(hb);
        root.getChildren().add(controls);
//        root.getChildren().add(sqiltBoard);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patchwork Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(sqiltBoard);
        timeBoard();
        c = PatchworkGame.initPathCircle();
        if (c.indexOf('A') != 0) c = c.substring(c.indexOf('A') + 1) + c.substring(0, c.indexOf('A') - 1);
        candidateArea(c);
        timeToken();
        squiltBoard1();
        squiltBoard2();
        makeControls();
        PatchworkGame.three = c.substring(0, 3).toCharArray();
        root.getChildren().add(tilesArea);
        clickArea();
        turn = new Text("Player 1's Turn");
        turn.setLayoutX(400);
        turn.setLayoutY(200);
        root.getChildren().add(turn);
        confirm = new Button("Are you sure?");
        confirm.setDisable(true);
        confirm.setLayoutX(10);
        confirm.setLayoutY(10);
        root.getChildren().add(confirm);
        advance = new Button("Advance");
        advance.setLayoutX(10);
        advance.setLayoutY(200);
        advance.setOnMouseClicked(event -> {
            p += ".";
            int t=State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
            PatchworkGame.isPlacementValid(c, p);
            moveToken(t,(t==1?p1:p2).timecount);
            turn.setText("Player " + t + "'s turn");
            clickArea();
        });
        root.getChildren().add(advance);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //describe the window attributes
//    public void canvas(){
//
//    }

    // show the final window when the game ends.Including two players'
    // socres and who wins the game. Show a restart button as well.
    public void finalWindow() {

    }

    //displays the squilt board for player 1.
    public void squiltBoard1() {
        Rectangle[][] sq = new Rectangle[9][9];
        HBox sq1 = new HBox();
        Random gen = new Random();
        sq1.setPrefSize(280, 280);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sq[i][j] = new Rectangle(BOARD1X + i * 30, BOARDY + j * 30, 30, 30);
                sq[i][j].setStroke(Color.color((double) i / 9, (double) j / 9, (double) (i + j) / 18));
                sq[i][j].setFill(Color.WHEAT);
                root.getChildren().add(sq[i][j]);
            }
        }
        sq1.setLayoutX(BOARD1X);
        sq1.setLayoutY(BOARDY);
//        sqiltBoard.getChildren().add(sq1);
    }

    //displays the squilt board for player 2.
    public void squiltBoard2() {
        Rectangle[][] sq2 = new Rectangle[9][9];
        HBox sq1 = new HBox();
        sq1.setPrefSize(280, 280);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sq2[i][j] = new Rectangle(BOARD2X + i * 30, BOARDY + j * 30, 30, 30);
                sq2[i][j].setStroke(Color.color((double) i / 9, (double) j / 9, (double) (i + j) / 18));
                root.getChildren().add(sq2[i][j]);
            }
        }
        sq1.setLayoutX(BOARD1X);
        sq1.setLayoutY(BOARDY);
    }

    //displays the time board for players.
    public void timeBoard() {
        File imgTimeboard = new File("src/comp1140/ass2/gui/assets/timeBoard.png");
        String tbPath = new String("file:" + imgTimeboard.getAbsolutePath());
        Image tb = new Image(tbPath);
        ImageView tbView = new ImageView();
        tbView.setImage(tb);
        tbView.setFitHeight(270);
        tbView.setFitWidth(270);
        HBox timeboard = new HBox();
        timeboard.getChildren().add(tbView);
        timeboard.setLayoutX(331.5);
        timeboard.setLayoutY(290);
        timeboard.setOnMouseClicked(event -> {
            System.out.println(event.getSceneX()+", "+event.getSceneY());
        });
        root.getChildren().add(timeboard);

    }

    //displays the time token of player1,
    // call the drag() when token is dragged.
    public void timeToken() {
        tt1 = new Circle();
        tt1.setRadius(10);
        tt1.setCenterX(321);
        tt1.setCenterY(300);
        tt1.setFill(Color.YELLOW);
        tt2 = new Circle();
        tt2.setRadius(10);
        tt2.setCenterX(321);
        tt2.setCenterY(300);
        tt2.setFill(Color.BLUE);
        root.getChildren().add(tt1);
        root.getChildren().add(tt2);
    }

    //    displays the candidates area which shows the three available tiles.
    public void candidateArea(String init) {
        HBox[] hb = new HBox[init.length()];
        for (int i = 0; i < init.length(); i++) {
            hb[i] = new HBox();
            String cc;
            if (init.charAt(i) <= 'Z') cc = "" + init.charAt(i);
            else cc = init.charAt(i) + "_";
            File imgTimeboard = new File("src/comp1140/ass2/gui/assets/" + cc + ".png");
            String tPath = "file:" + imgTimeboard.getAbsolutePath();
//            System.out.println(tPath);
            Image tile = new Image(tPath);
            ImageView tbView = new ImageView();
            tbView.setImage(tile);
            tbView.setFitHeight(25);
            tbView.setFitWidth(25);

            hb[i].getChildren().add(tbView);
            hb[i].setLayoutX(200 + 30 * (i % 17));
            hb[i].setLayoutY(VIEWER_HEIGHT - (i < 17 ? 130 : 100));

            root.getChildren().add(hb[i]);
        }
        //        hb.setLayoutX(270);
//        hb.setLayoutY(120);
//        tilesArea.getChildren().add(hb);
//        root.getChildren().add(tilesArea);
    }


    // displays the two button, undo and confirm, call the relevant functions
    // about the interaction
    public void clickArea() {
        tilesArea.getChildren().clear();
        if (p1.specialH || p2.specialH) {
            tilesArea.getChildren().add(new DraggableTile('h', 400, 20, 30));
        } else {
            for (int i = 0; i < 3; i++) {
                tilesArea.getChildren().add(new DraggableTile(PatchworkGame.three[i], 250 + i * 150, 20, 30));
            }
        }
    }

    class DraggableTile extends ImageView {
        int tileCost;
        char tName;
        int homeX, homeY;
        double mouseX, mouseY;
        boolean draggable = true;

        DraggableTile(char tName, int x, int y, int scale) {
            homeX = x;
            homeY = y;
            this.tName = tName;
            int index;
            String tS;
            if (tName <= 'Z') {
                tS = tName + ".png";
                index = tName - 'A';
            } else {
                tS = tName + "_.png";
                index = tName - 'a' + 26;
            }
            File tPath = new File("src/comp1140/ass2/gui/assets/" + tS);
            System.out.println(tPath);
            setImage(new Image("file:" + tPath.getAbsolutePath()));
            setFitWidth((int) (getImage().getWidth() / 50 * scale));
            setFitHeight((int) (getImage().getHeight() / 50 * scale));

            setLayoutX(x);
            setLayoutY(y);
            tileCost = PatchworkGame.tileCost[index];


            /* event handler*/
            setOnScroll(event -> {
                setRotate((getRotate() + 90) % 360);
                event.consume();
            });

            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                if (State.check_turn(PatchworkGame.p1, PatchworkGame.p2) == 1) {
                    if (!State.affordPartch(PatchworkGame.p1.buttonCount, tileCost))
                        draggable = false;
                } else {
                    if (!State.affordPartch(PatchworkGame.p1.buttonCount, tileCost)) {
                        draggable = false;
                    }
                }
                if (!draggable)
                    event.consume();
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });
            setOnMouseDragged(event -> {      // mouse is being dragged
                if (!draggable) event.consume();
                toFront();
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                setLayoutX(getLayoutX() + movementX);
                setLayoutY(getLayoutY() + movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });
            setOnMouseReleased(event -> {     // drag is complete
                confirm.setDisable(false);
                confirm.setOnMouseClicked(event1 -> {
                    if (getLayoutX() != homeX) {
                        String m = makeString();
                        System.out.println(new String(PatchworkGame.three));
                        System.out.println(c);
                        int t=State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                        if (PatchworkGame.isPlacementValid(c, p + m)) {
                            p += m;
                            root.getChildren().add(this);
                            tilesArea.getChildren().remove(this);
                            setOnScroll(null);
                            moveToken(t, (t==1?p1:p2).timecount);
                            t=State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                            turn.setText("Player " + t + "'s turn");
                            clickArea();

                        } else {
                            setLayoutX(homeX);
                            setLayoutY(homeY);
                        }
                        event.consume();
                    }
                });
                snap();
            });
        }

        String makeString() {
            int x = (int) getLayoutX();
            x -= x > 600 ? BOARD2X : BOARD1X;
            int y = (int) getLayoutY() - BOARDY;
            String s = "" + tName;
            s += (char) ('A' + x / 30);
            s += (char) ('A' + y / 30);
            s += (char) ('A' + (int) getRotate() / 90);
            return s;
        }

        void snap() {
            if (State.check_turn(PatchworkGame.p1, PatchworkGame.p2) == 1 && getLayoutX() > BOARD1X && (getLayoutX() + getFitWidth() < BOARD1X + BOARD_SIZE + 30)
                    && getLayoutY() > BOARDY && (getLayoutY() + getFitHeight() < BOARDY + BOARD_SIZE + 30)) {
                setLayoutX((((int) getLayoutX() - BOARD1X) / 30) * 30 + BOARD1X);
                setLayoutY((((int) getLayoutY() - BOARDY) / 30) * 30 + BOARDY);
            } else if (State.check_turn(PatchworkGame.p1, PatchworkGame.p2) == 2 && getLayoutX() > BOARD2X && (getLayoutX() + getFitWidth() < BOARD2X + BOARD_SIZE + 30)
                    && getLayoutY() > BOARDY && (getLayoutY() + getFitHeight() < BOARDY + BOARD_SIZE + 30)) {
                setLayoutX((((int) getLayoutX() - BOARD2X) / 30) * 30 + BOARD2X);
                setLayoutY((((int) getLayoutY() - BOARDY) / 30) * 30 + BOARDY);
            } else {
                setLayoutX(homeX);
                setLayoutY(homeY);
            }
        }
    }

    void moveToken(int player, int position) {
        Circle tt = player == 1 ? tt1 : tt2;
        System.out.println(position);
        int[] p = new int[][]{{0, 1}, {0, 3}, {0, 4}, {0, 5}, {0, 6}, {0, 7},
                {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}, {6, 7}, {7, 7},
                {7, 6}, {7, 5}, {7, 4}, {7, 3}, {7, 2}, {7, 1}, {7, 0},
                {5, 0}, {4, 0}, {3, 0}, {2, 0}, {1, 0},
                {1, 1}, {1, 3}, {1, 4}, {1, 5}, {1, 6},
                {2, 6}, {3, 6}, {5, 6}, {6, 6}, {6, 5}, {6, 4}, {6, 3}, {6, 2}, {6, 1},
                {5, 1}, {4, 1}, {3, 1}, {2, 1}, {2, 2}, {2, 4}, {2, 5},
                {3, 5}, {4, 5}, {5, 5}, {5, 4}, {5, 2}, {4, 2}, {3, 2}, {3, 3}}[position];
        tt.setCenterY(p[0]*33+310);
        tt.setCenterX(p[1]*33+350);
    }


}
