package comp1140.ass2;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

public class test extends Application{

    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        int columns = 20, rows = 10, horizontal = 50, vertical = 20;
        Rectangle rect = null;
        for (int i = 0; i < columns; ++i) {
            for (int j = 0; j < rows; ++j) {
                rect = new Rectangle(horizontal * j, vertical * i, horizontal, vertical);
                rect.setStroke(Color.RED);
                root.getChildren().add(rect);
            }
        }
        scene.setRoot(root);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
