package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Path;
import javafx.scene.control.Button;
import java.util.*;
import javafx.application.Platform;

public class GameWindow extends Application {

    private boolean goDown, goUp;
    private static final int MAX_WINDOW_SIZE_X = 940; //940
    private static final int MAX_WINDOW_SIZE_Y = 640; // 640
    private static final double PADDLE_MOVEMENT_SPEED = 0.5;//0.4
    private static final double INITIAL_BALL_MOVEMENT_SPEED = 0.9; //0.9
    private static Paddle player = new Paddle(MAX_WINDOW_SIZE_X/2-50, 0, MAX_WINDOW_SIZE_Y, PADDLE_MOVEMENT_SPEED);
    private static AIPaddle AI = new AIPaddle(-MAX_WINDOW_SIZE_X/2+50, 0, MAX_WINDOW_SIZE_Y, PADDLE_MOVEMENT_SPEED);
                                                                                                                               //
    private static Ball ball = new Ball(0,0, MAX_WINDOW_SIZE_X, MAX_WINDOW_SIZE_Y, INITIAL_BALL_MOVEMENT_SPEED, new Paddle[]{player, AI});
    
    private static Timer timer = new Timer();
    
    
    private StackPane root = new StackPane();
    private Scene scene = new Scene(root, 300, 250);

    @Override
    public void start(Stage primaryStage) {
        
        Button singlePlayerButton = new Button();
        singlePlayerButton.setText("Single Player");
        singlePlayerButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                playSinglePlayer();
            }
        });
        
       System.out.println(-MAX_WINDOW_SIZE_X/2);

        
        root.getChildren().add(singlePlayerButton);
        //root.getChildren().add(player.getRectangle());
        //root.getChildren().add(AI.getRectangle());
        //root.getChildren().add(ball.getBall());

        

        primaryStage.setTitle("Pong!");
        primaryStage.setScene(scene);
        primaryStage.setHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setWidth(MAX_WINDOW_SIZE_X);
        primaryStage.setMinHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setMinWidth(MAX_WINDOW_SIZE_X);
        primaryStage.setMaxHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setMaxWidth(MAX_WINDOW_SIZE_X);
        
        
        primaryStage.show();
        
    }



    public static void main(String[] args) {
        launch(args);
    }


    //Check for Ball paddle Collision
    public static Path checkCollision(Paddle thisPaddle){
        
        System.out.println((Path)Shape.intersect(thisPaddle.getRectangle(), ball.getBall()));
        return (Path)Shape.intersect(thisPaddle.getRectangle(), ball.getBall());

    }
    /*
     public static Path checkCollisionLeft(){
       
        System.out.println((Path)Shape.intersect(AI.getRectangle(), ball.getBall()));
        return (Path)Shape.intersect(AI.getRectangle(), ball.getBall());

    }*/
    
    private void playSinglePlayer() {
        System.out.println(-MAX_WINDOW_SIZE_X/2);

        StackPane root = new StackPane();
        //root.getChildren().add(btn);
        root.getChildren().add(player.getRectangle());
        root.getChildren().add(AI.getRectangle());
        root.getChildren().add(ball.getBall());
        /*
        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Pong!");
        primaryStage.setScene(scene);
        primaryStage.setHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setWidth(MAX_WINDOW_SIZE_X);
        primaryStage.setMinHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setMinWidth(MAX_WINDOW_SIZE_X);
        primaryStage.setMaxHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setMaxWidth(MAX_WINDOW_SIZE_X);
        */
        //window size listener
        /*
        primaryStage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        */

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goUp = true;
                        break;
                    case DOWN:
                        goDown = true;
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        goUp = false;
                        break;
                    case DOWN:
                        goDown = false;
                        break;
                }
            }
        });









        //SHOW the window
        //primaryStage.show();

        //Movement timer
        /*
        AnimationTimer atimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                double py = 0;

                if (goDown) py += PADDLE_MOVEMENT_SPEED;
                if (goUp) py += -PADDLE_MOVEMENT_SPEED;


                player.moveY(py);
                //Thread.sleep(1);
            }
            
        };
        atimer.start();*/
        
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        
                        double py = 0;

                if (goDown) py += PADDLE_MOVEMENT_SPEED;
                if (goUp) py += -PADDLE_MOVEMENT_SPEED;


                player.moveY(py);
                AI.update(ball.getY());
                ball.updateBall();
                    }
                });
            }
        }, 1,1);
    }
}