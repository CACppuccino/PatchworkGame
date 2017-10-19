package comp1140.ass2;

import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import java.io.File;

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

    public static String c;

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
    public static String p;

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
            String tileN = tileName + (tileName > 'a' && tileName < 'i' ? "_.png" : ".png");
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
            if (((int)r/90)%2==1) {
                x+= (h / 2 - w / 2);
                y+= (w / 2 - h / 2);
            }

            //indicates which board is going to be placed on the tile
            setHboxPos(mPlacement,(AI ? BOARD2X : BOARD1X) + x,BOARDY + y);
            mPlacement.getChildren().add(tileView);

            controls.getChildren().add(mPlacement);
            i += 4;
        }
    }

    private static void setTextPos(Text obj, double LayoutX, double LayoutY){
        obj.setLayoutX(LayoutX);
        obj.setLayoutY(LayoutY);
    }
    private static void setBtnPos(Button obj, double LayoutX, double LayoutY){
        obj.setLayoutX(LayoutX);
        obj.setLayoutY(LayoutY);
    }
    private static void setHboxPos(HBox obj, double LayoutX, double LayoutY){
        obj.setLayoutX(LayoutX);
        obj.setLayoutY(LayoutY);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        HBox hb = new HBox();
        hb.setSpacing(10);
        setHboxPos(hb,250,VIEWER_HEIGHT - 50);
        root.getChildren().add(hb);
        root.getChildren().add(controls);
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
        setTextPos(title,400,200);
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
        setBtnPos(begin,400,320);
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
        c = PatchworkGame.initPatchCircle();

        if (c.indexOf('A') != c.length()-1) c = c.substring(c.indexOf('A') + 1) + c.substring(0, c.indexOf('A') + 1);
        candidateArea(c);
        timeToken();
        squiltBoard1();
        squiltBoard2();
        makeControls();
        PatchworkGame.three = c.substring(0, 3).toCharArray();
        clickArea();
        err = new Alert(Alert.AlertType.ERROR);
        turn = new Text("Player 1's Turn");
        setTextPos(turn,400,200);
        confirm = new Button("End Turn");
        confirm.setDisable(true);
        setBtnPos(confirm,380,250);
        advance = new Button("Advance");
        setBtnPos(advance,460,250);
        advance.setDisable(false);
        advance.setOnMouseClicked(event -> {
            tilesArea.getChildren().clear();
            p += ".";
            int t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
            PatchworkGame.isPlacementValid(c, p);
            System.out.println("P1:"+p);
            moveToken(t, (t == 1 ? p1 : p2).timecount);
            if (p1.timecount >= 53 && p2.timecount >= 53) {
                win = new Alert(Alert.AlertType.INFORMATION);
                int s1= PatchworkGame.getScoreForPlacement(c,p, true);
                int s2=PatchworkGame.getScoreForPlacement(c,p, false);
                System.out.println(p);
                if (s1!=s2)
                    win.setContentText("Player " + (s1>s2 ? 1 : 2) + " wins\n"+ "Player 1:" +s1+" scores\n"+ "Player 2:" +s2 +" scores");
                else    win.setContentText("Tie game!");
                win.showAndWait();
                reset();
            }
            t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
            turn.setText("Player " + t + "'s turn");
            btn1.setText(p1.buttonCount + " buttons");
            btn2.setText(p2.buttonCount + " buttons");
            clickArea();
            if (AI) {
                while (t == 2 && p2.timecount < 53) {
                    aiTurn();
                    t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                }
            }
        });
        btn1 = new Text("5 buttons");
        setTextPos(btn1,30,580);
        btn1.setFill(Color.WHITE);
        btn2 = new Text("5 buttons");
        setTextPos(btn2,840,580);
        warn = new Alert(Alert.AlertType.CONFIRMATION);
        menu = new Button("Back to main");
        setBtnPos(menu,10,10);
        menu.setOnMouseClicked(event -> {
            warn.setContentText("This will end the current game.\nAre you sure?");
            if (warn.showAndWait().get() == ButtonType.OK) {
                reset();
            }
        });
        player1 = new Text("Player 1: Yellow");
        setTextPos(player1,30,280);
        player1.setFill(Color.YELLOW);
        player2 = new Text("Player 2" + (AI ? " (AI)" : "") + ": Blue");
        setTextPos(player2,800,280);
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

    private void setSquiltBoard(int x, Color color){
        Rectangle[][] sq = new Rectangle[9][9];
        HBox sq1 = new HBox();
        sq1.setPrefSize(280, 280);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sq[i][j] = new Rectangle(x + i * 30, BOARDY + j * 30, 30, 30);
                sq[i][j].setStroke(Color.color((double) i / 9, (double) j / 9, (double) (i + j) / 18));
                sq[i][j].setFill(color);
                root.getChildren().add(sq[i][j]);
            }
        }
        setHboxPos(sq1,BOARD1X,BOARDY);

    }

    //displays the squilt board for player 1.
    private void squiltBoard1() {setSquiltBoard(BOARD1X,Color.WHEAT);}

    //displays the squilt board for player 2.
    private void squiltBoard2() {setSquiltBoard(BOARD2X,Color.BLACK);}

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
        setHboxPos(timeboard,331.5,290);
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
            Image tile = new Image(tPath);
            ImageView tbView = new ImageView();
            tbView.setImage(tile);
            tbView.setFitHeight(25);
            tbView.setFitWidth(25);

            hb[i].getChildren().add(tbView);
            setHboxPos(hb[i],200 + 30 * (i % 17),VIEWER_HEIGHT - (i < 17 ? 130 : 100));
            root.getChildren().add(hb[i]);
        }
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
        boolean rotated = false;

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
                if ((getRotate()+90)==360){
                    setScaleX(-getScaleX());
                    setRotate(0);
                }else                setRotate((getRotate()+90));

                System.out.println(getLayoutX() + " " + getLayoutY());
                System.out.println(getFitWidth() + " " + getFitHeight());
                rotated = ((int) getRotate() / 90) % 2 == 1;
                if (rotated) {
                    setLayoutX(getLayoutX() + (getFitHeight() / 2 - getFitWidth() / 2));
                    setLayoutY(getLayoutY() + (getFitWidth() / 2 - getFitHeight() / 2));
                } else {
                    setLayoutX(getLayoutX() - (getFitHeight() / 2 - getFitWidth() / 2));
                    setLayoutY(getLayoutY() - (getFitWidth() / 2 - getFitHeight() / 2));
                }


                event.consume();
            });
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });
            setOnMouseDragged(event -> {      // mouse is being dragged

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
                root.getChildren().remove(this);
                root.getChildren().add(this);
                tilesArea.getChildren().remove(this);
                tilesArea.setDisable(true);
                snap();
                if (getLayoutX()==homeX){
                    tilesArea.setDisable(false);
                    tilesArea.getChildren().add(this);
                    root.getChildren().remove(this);
                }
                confirm.setDisable(getLayoutX() == homeX);
                advance.setDisable(!confirm.isDisabled());
                confirm.setOnMouseClicked(event1 -> {
                    if (getLayoutX() != homeX) {
                        String m = makeString();
                        System.out.println(m);
                        System.out.println(new String(PatchworkGame.three));
                        System.out.println(c);
                        int t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                        if (PatchworkGame.isPlacementValid(c, p + m)) {
                            p += m;
                            System.out.println("P1:" + p);
                            if (p1.timecount >= 53 && p2.timecount >= 53) {
                                win = new Alert(Alert.AlertType.INFORMATION);
                                int s1 = PatchworkGame.getScoreForPlacement(c, p, true);
                                int s2 = PatchworkGame.getScoreForPlacement(c, p, false);
                                System.out.println(s1 + s2);
                                if (s1!=s2)
                                    win.setContentText("Player " + (s1>s2 ? 1 : 2) + " wins\n"+ "Player 1:" +s1+" scores\n"+ "Player 2:" +s2 +" scores");
                                else win.setContentText("Tie game !");
                                win.showAndWait();
                                reset();
                            }
                            setOnScroll(null);
                            setOnMouseReleased(null);
                            setOnMouseDragged(null);
                            tilesArea.setDisable(false);
                            moveToken(t, (t == 1 ? p1 : p2).timecount);
                            t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                            turn.setText("Player " + t + "'s turn");
                            btn1.setText(p1.buttonCount + " buttons");
                            btn2.setText(p2.buttonCount + " buttons");
                            clickArea();
                            confirm.setDisable(true);
                            advance.setDisable(false);
                            if (AI) {
                                while (t == 2 && p2.timecount < 53) {
                                    aiTurn();
                                    t = State.check_turn(PatchworkGame.p1, PatchworkGame.p2);
                                }
                            }
                        } else {
                            if ((t == 1 ? p1 : p2).buttonCount < tileCost)
                                err.setContentText("You can't afford this patch");
                            else err.setContentText("Invalid placement");
                            err.showAndWait();
                            confirm.setDisable(true);
                            advance.setDisable(false);
                            tilesArea.setDisable(false);
                            setRotate(0);
                            rotated=false;
                            setLayoutX(homeX);
                            setLayoutY(homeY);
                            tilesArea.setDisable(false);
                            tilesArea.getChildren().add(this);
                            root.getChildren().remove(this);
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
            if (rotated) {
                x -= (getFitHeight() / 2 - getFitWidth() / 2);
                y -= (getFitWidth() / 2 - getFitHeight() / 2);
            }
            String s = "" + tName;
            s += (char) ('A' + x / 30);
            s += (char) ('A' + y / 30);
            s += (char) ('A' + (int) getRotate() / 90);
            return s;
        }


        void snap() {
            double x = getLayoutX();
            double y = getLayoutY();
            double offX = 0;
            double offY = 0;
            double w = getFitWidth();
            double h = getFitHeight();
            if (rotated) {
                offX = (getFitHeight() / 2 - getFitWidth() / 2);
                offY = (getFitWidth() / 2 - getFitHeight() / 2);
                x -= offX;
                y -= offY;
                h=getFitWidth();
                w=getFitHeight();
            }
            if (State.check_turn(PatchworkGame.p1, PatchworkGame.p2) == 1 && y > BOARDY && y + h < BOARDY + BOARD_SIZE + 30) {
                if (x > BOARD1X && x + w < BOARD1X + BOARD_SIZE + 30) {
                    setLayoutX((((int) x - BOARD1X) / 30) * 30 + BOARD1X + offX);
                    setLayoutY((((int) y - BOARDY) / 30) * 30 + BOARDY + offY);
                } else {
                    if (x > BOARD2X && x + w < BOARD2X + BOARD_SIZE + 30) {
                        err.setContentText("It's Player 1's Turn");
                        err.showAndWait();
                    }
                    setRotate(0);
                    rotated=false;
                    setLayoutX(homeX);
                    setLayoutY(homeY);
                }
            } else if (State.check_turn(PatchworkGame.p1, PatchworkGame.p2) == 2 && y > BOARDY && y + h < BOARDY + BOARD_SIZE + 30) {
                if (x > BOARD2X && x + w < BOARD2X + BOARD_SIZE + 30) {
                    setLayoutX((((int) x - BOARD2X) / 30) * 30 + BOARD2X + offX);
                    setLayoutY((((int) y - BOARDY) / 30) * 30 + BOARDY + offY);
                } else {
                    if (x > BOARD1X && x + w < BOARD1X + BOARD_SIZE + 30) {
                        err.setContentText("It's Player 2's Turn");
                        err.showAndWait();
                    }
                    setRotate(0);
                    rotated=false;
                    setLayoutX(homeX);
                    setLayoutY(homeY);
                }
            } else {
                setRotate(0);
                rotated=false;
                setLayoutX(homeX);
                setLayoutY(homeY);
            }
        }
    }

    private void aiTurn() {
        String s = PatchworkAI.generatePatchPlacement(c, p);
        System.out.println(s);
        p += s;
        System.out.println("AI:"+p);
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