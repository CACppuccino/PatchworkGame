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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BackgroundImage;
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
    private static final int BOARD2X = 640;
    private static final int BOARDY = 290;
    private static final int BOARD_SIZE = 270;

    private static String c;
    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group sqiltBoard = new Group();
    private final Group tilesArea = new Group();
    private final Group startScreen = new Group();
    //    private final Group playScreen = new Group();
//    ArrayList<Character> candi;
    private TextField textField;

    private boolean AI = false;

    private static Text turn;
    private static Button confirm;
    private static Button advance;
    private static Button menu;
    private static Circle tt1;
    private static Circle tt2;
    private static Text btn1;
    private static Text btn2;
    private static Text player1;
    private static Text player2;
    private static Alert err;
    private static Alert warn;
    private static Alert win;
    private static String p;
    private static final File BG1 = new File("src/comp1140/ass2/gui/assets/bg.jpg");
    private static final File BG2 = new File("src/comp1140/ass2/gui/assets/bg2.jpg"); //https://i.ytimg.com/vi/bOlIncfaVOU/maxresdefault.jpg
    private static ImageView bg;


    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    private void makePlacement(String placement) {
        if (!AI) controls.getChildren().clear();
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
            mPlacement.setLayoutX((AI ? BOARD2X : BOARD1X) + x);
            mPlacement.setLayoutY(BOARDY + y);
            mPlacement.getChildren().add(tileView);

            controls.getChildren().add(mPlacement);
            i += 4;
        }
    }


    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        //Label label1 = new Label("Placement:");
//        textField = new TextField();
//        textField.setPrefWidth(300);
//        Button button = new Button("Display");
//        button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent e) {
//                makePlacement(textField.getText());
//                System.out.println(new String(three));
//                if (PatchworkGame.isPlacementValid(c, textField.getText())) System.out.println("b");
//                clickArea();
//            }
//        });
        HBox hb = new HBox();
        //hb.getChildren().addAll(label1, textField, button);
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
        //http://www.planwallpaper.com/static/images/cool-background.jpg
        bg = new ImageView(new Image("file:" + BG1.getAbsolutePath()));
        bg.toBack();
        root.getChildren().add(bg);
        Text title = new Text("PATCHWORK");
        title.setLayoutX(400);
        title.setLayoutY(200);
        title.setScaleX(5);
        title.setScaleY(5);
        title.setFill(Color.WHITE);
        ToggleGroup option = new ToggleGroup();
        RadioButton option1 = new RadioButton("Play with AI (1P)");
        option1.setLayoutX(400);
        option1.setLayoutY(250);
        option1.setTextFill(Color.WHITE);
        option1.setToggleGroup(option);
        RadioButton option2 = new RadioButton("Human to human (2P)");
        option2.setLayoutX(400);
        option2.setLayoutY(280);
        option2.setTextFill(Color.WHITE);
        option2.setToggleGroup(option);
        option2.setSelected(true);
        Button begin = new Button("Start Game");
        begin.setLayoutX(400);
        begin.setLayoutY(320);
        begin.setOnMouseClicked(event -> {
            AI = option1.isSelected();
            game();
        });
        startScreen.getChildren().addAll(title, option1, option2, begin);
        root.getChildren().add(startScreen);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void game() {
        bg.setImage(new Image("file:" + BG2.getAbsolutePath()));
        root.getChildren().remove(startScreen);
        timeBoard();
        p = "";
        c = PatchworkGame.initPathCircle();
        if (c.indexOf('A') != 0) c = c.substring(c.indexOf('A') + 1) + c.substring(0, c.indexOf('A') + 1);
        candidateArea(c);
        timeToken();
        squiltBoard1();
        squiltBoard2();
        makeControls();
        PatchworkGame.three = c.substring(0, 3).toCharArray();
        clickArea();
        err = new Alert(Alert.AlertType.ERROR);
        turn = new Text("Player 1's Turn");
        turn.setLayoutX(400);
        turn.setLayoutY(200);
        confirm = new Button("End Turn");
        confirm.setDisable(true);
        confirm.setLayoutX(380);
        confirm.setLayoutY(250);
        advance = new Button("Advance");
        advance.setLayoutX(460);
        advance.setLayoutY(250);
        advance.setOnMouseClicked(event -> {
            p += ".";
            int t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
            PatchworkGame.isPlacementValid(c, p);
            if (p1.timecount >= 53 && p2.timecount >= 53) {
                win = new Alert(Alert.AlertType.INFORMATION);
                boolean w = PatchworkGame.getScoreForPlacement(p, c, true) > PatchworkGame.getScoreForPlacement(p, c, false);
                win.setContentText("Player " + (w ? 1 : 2) + " wins");
                win.showAndWait();
                reset();
            }
            moveToken(t, (t == 1 ? p1 : p2).timecount);
            t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
            turn.setText("Player " + t + "'s turn");
            btn1.setText(p1.buttonCount + " buttons");
            btn2.setText(p2.buttonCount + " buttons");
            clickArea();
            confirm.setDisable(true);
            if (AI) {
                while (t == 2 && p2.timecount < 53) {
                    aiTurn();
                    t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                }
            }
        });
        btn1 = new Text("5 buttons");
        btn1.setLayoutX(30);
        btn1.setLayoutY(580);
        btn2 = new Text("5 buttons");
        btn2.setLayoutX(840);
        btn2.setLayoutY(580);
        warn = new Alert(Alert.AlertType.CONFIRMATION);
        menu = new Button("Back to main");
        menu.setLayoutX(10);
        menu.setLayoutY(10);
        menu.setOnMouseClicked(event -> {
            warn.setContentText("This will end the current game.\nAre you sure?");
            if (warn.showAndWait().get() == ButtonType.OK) {
                reset();
            }
        });
        player1 = new Text("Player 1: Yellow");
        player1.setLayoutX(30);
        player1.setLayoutY(280);
        player1.setFill(Color.YELLOW);
        player2 = new Text("Player 2" + (AI ? " (AI)" : "") + ": Blue");
        player2.setLayoutX(800);
        player2.setLayoutY(280);
        player2.setFill(Color.BLUE);
        root.getChildren().addAll(sqiltBoard, tilesArea, turn, confirm, advance, btn1, btn2, menu, player1, player2);
    }

    public void reset() {
        root.getChildren().clear();
        controls.getChildren().clear();
        bg.setImage(new Image("file:" + BG1.getAbsolutePath()));
        bg.toBack();
        root.getChildren().addAll(bg, startScreen);
        PatchworkGame.p1 = new State(1);
        PatchworkGame.p2 = new State(2);
    }

    // show the final window when the game ends.Including two players'
    // socres and who wins the game. Show a restart button as well.
    public void finalWindow() {

    }

    //displays the squilt board for player 1.
    private void squiltBoard1() {
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
    private void squiltBoard2() {
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
    private void timeBoard() {
        File imgTimeboard = new File("src/comp1140/ass2/gui/assets/timeBoard.png");
        String tbPath = "file:" + imgTimeboard.getAbsolutePath();
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
            System.out.println(event.getSceneX() + ", " + event.getSceneY());
        });
        root.getChildren().add(timeboard);

    }

    //displays the time token of player1,
    // call the drag() when token is dragged.
    private void timeToken() {
        tt1 = new Circle();
        tt1.setRadius(10);
        tt1.setCenterX(360);
        tt1.setCenterY(310);
        tt1.setFill(Color.YELLOW);
        tt2 = new Circle();
        tt2.setRadius(10);
        tt2.setCenterX(390);
        tt2.setCenterY(310);
        tt2.setFill(Color.BLUE);
        root.getChildren().add(tt1);
        root.getChildren().add(tt2);
    }

    //    displays the candidates area which shows the three available tiles.
    public void candidateArea(String init) {
        Rectangle box = new Rectangle(180, 560, 540, 70);

        box.setFill(Color.WHITE);
        box.setOpacity(0.5);
        root.getChildren().add(box);
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
    private void clickArea() {
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
            setUserData(tName);
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
                snap();
                confirm.setDisable(getLayoutX() == homeX);
                confirm.setOnMouseClicked(event1 -> {
                    if (getLayoutX() != homeX) {
                        String m = makeString();
                        System.out.println(new String(PatchworkGame.three));
                        System.out.println(c);
                        int t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                        if (PatchworkGame.isPlacementValid(c, p + m)) {
                            if (p1.timecount >= 53 && p2.timecount >= 53) {
                                win = new Alert(Alert.AlertType.INFORMATION);
                                boolean w = PatchworkGame.getScoreForPlacement(p, c, true) > PatchworkGame.getScoreForPlacement(p, c, false);
                                win.setContentText("Player " + (w ? 1 : 2) + " wins");
                                win.showAndWait();
                                reset();
                            }
                            p += m;
                            root.getChildren().add(this);
                            tilesArea.getChildren().remove(this);
                            setOnScroll(null);
                            moveToken(t, (t == 1 ? p1 : p2).timecount);
                            t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                            turn.setText("Player " + t + "'s turn");
                            btn1.setText(p1.buttonCount + " buttons");
                            btn2.setText(p2.buttonCount + " buttons");
                            clickArea();
                            confirm.setDisable(true);
                            if (AI) {
                                while (t == 2 && p2.timecount < 53) {
                                    aiTurn();
                                    t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                                }
                            }
                        } else {
                            err.setContentText("Invalid Placement\nMake sure you have enough buttons");
                            err.showAndWait();
                            confirm.setDisable(true);
                            setLayoutX(homeX);
                            setLayoutY(homeY);
                        }
                        event.consume();
                    }
                });

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
                if (getLayoutY() > BOARDY && getLayoutY() < BOARDY + BOARD_SIZE) {
                    err.setContentText("It's Player " + State.check_turn(PatchworkGame.p1, PatchworkGame.p2) + "'s Turn");
                    err.showAndWait();
                }
                setLayoutX(homeX);
                setLayoutY(homeY);
            }
        }
    }

    private void aiTurn() {
        String s = PatchworkAI.generatePatchPlacement(c, p);
        System.out.println(s);
        p += s;
        makePlacement(s);
        PatchworkGame.isPlacementValid(c, p);
        moveToken(2, p2.timecount);
        int t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
        turn.setText("Player " + t + "'s turn");
        btn1.setText(p1.buttonCount + " buttons");
        btn2.setText(p2.buttonCount + " buttons");
        clickArea();

    }

    private void moveToken(int player, int position) {
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
        tt.setCenterY(p[0] * 33 + 310);
        tt.setCenterX(p[1] * 33 + 350);
    }
}