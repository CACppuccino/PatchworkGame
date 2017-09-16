package comp1140.ass2;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class test extends Application{
    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group sqiltBoard = new Group();
    private final Group tilesArea = new Group();
    ArrayList<Character> candi;
    TextField textField;
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patchwork Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        String c = PatchworkGame.initPathCircle();
        candidateArea(c);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void candidateArea(String init){
        HBox[] hb = new HBox[init.length()];
        for (int i =0; i<init.length();i++) {
            hb[i] = new HBox();
            String cc;
            if (init.charAt(i)<='Z') cc= ""+init.charAt(i);
            else cc=init.charAt(i)+"_";
            File imgTimeboard = new File("src/comp1140/ass2/gui/assets/"+cc+".png");
            String tPath = new String("file:" + imgTimeboard.getAbsolutePath());
            System.out.println(tPath);
            Image tile = new Image(tPath);
            ImageView tbView = new ImageView();
            tbView.setImage(tile);
            tbView.setFitHeight(25);
            tbView.setFitWidth(25);
            hb[i].getChildren().add(tbView);
            hb[i].setLayoutX(120+30*(i%17));
            hb[i].setLayoutY(i<17?120:160);

            root.getChildren().add(hb[i]);

        }


        //        hb.setLayoutX(270);
//        hb.setLayoutY(120);
//        tilesArea.getChildren().add(hb);
//        root.getChildren().add(tilesArea);
    }
}
