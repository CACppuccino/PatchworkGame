package comp1140.ass2;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;

public class test extends Application{
    private Group root = new Group();
    private void setImg(){
        File file = new File("./A.png");
        System.out.println(file.exists());
        Image tb = new Image("file:/home/cup/IdeaProjects/comp1140-ass2-wed15c/src/comp1140/ass2/gui/assets/timeBoard.png");//"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ84hMKGYnLjbYASltpjWRIIumEGrwWPmkDFVkkr4hppCPekUIY");//("../gui/assets/timeBoard.png");
        ImageView tbView = new ImageView();
        tbView.setImage(tb);
//        tbView.setFitHeight(300);
//        tbView.setFitWidth(300);
        HBox timeboard = new HBox();
        timeboard.getChildren().add(tbView);
//        timeboard.setLayoutX(200);
//        timeboard.setLayoutY(500);
        root.getChildren().add(timeboard);
    }
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("for testing");
        setImg();
        Scene scene = new Scene(root, 900,900);
        scene.setFill(Color.valueOf("#3367D6"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
