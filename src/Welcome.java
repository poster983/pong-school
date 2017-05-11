package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
/**
 * Created by joseph on 5/8/17.
 */
public class Welcome extends Application {
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Single Player");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                String[] args = {};
                GameWindow.main(args);
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Pong!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        
    }


}
