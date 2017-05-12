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
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.scene.control.Button;
import javafx.scene.text.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import javafx.application.Platform;

public class GameWindow extends Application {

    private boolean goDown, goUp;
    private static final int MAX_WINDOW_SIZE_X = 940; //940
    private static final int MAX_WINDOW_SIZE_Y = 640; // 640
    private static final double PADDLE_MOVEMENT_SPEED = 0.5;//0.4
    private static final double INITIAL_BALL_MOVEMENT_SPEED = 0.9; //0.9
    private static final int WIN_SCORE = 10;
    private static Paddle player = new Paddle(MAX_WINDOW_SIZE_X/2-50, 0, MAX_WINDOW_SIZE_Y, PADDLE_MOVEMENT_SPEED);
    private static AIPaddle AI = new AIPaddle(-MAX_WINDOW_SIZE_X/2+50, 0, MAX_WINDOW_SIZE_Y, PADDLE_MOVEMENT_SPEED);
    //
    private static Ball ball = new Ball(0,0, MAX_WINDOW_SIZE_X, MAX_WINDOW_SIZE_Y, INITIAL_BALL_MOVEMENT_SPEED, new Paddle[]{player, AI});

    private static Timer timer = new Timer();

    private static StackPane root = new StackPane();
    private Scene scene = new Scene(root, 300, 250);

    @Override
    public void start(Stage primaryStage) {

        Button singlePlayerButton = new Button();
        singlePlayerButton.setText("Single Player");

        Button multiPlayerButton = new Button();
        multiPlayerButton.setText("MultiPlayer");
        multiPlayerButton.setTranslateY(50);

        Text bigTitle = new Text(10, 50, "Pong!");
        bigTitle.setFont(new Font(20));
        bigTitle.setTranslateY(-100);
        singlePlayerButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(singlePlayerButton);
                    root.getChildren().remove(bigTitle);
                    root.getChildren().remove(multiPlayerButton);
                    playSinglePlayer();
                }
            });

        multiPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(singlePlayerButton);
                    root.getChildren().remove(bigTitle);
                    root.getChildren().remove(multiPlayerButton);
                    multiPlayerSetup();
                }
            });

        root.getChildren().add(bigTitle);
        
        root.getChildren().add(singlePlayerButton);

        root.getChildren().add(multiPlayerButton);
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
    
    public static void checkEndCondition(Paddle thisPaddle) {
        if(WIN_SCORE >= thisPaddle.getScore()) {
            System.out.println(thisPaddle.getScore());
            //root.getChildren().clear();
        }
    }
    //Single player logic goes here
    private void playSinglePlayer() {
        System.out.println(-MAX_WINDOW_SIZE_X/2);

        
        root.getChildren().add(player.getRectangle());
        root.getChildren().add(AI.getRectangle());
        root.getChildren().add(ball.getBall());
        Text player1Score = player.getScoreBd();
        player1Score.setTranslateY(-MAX_WINDOW_SIZE_Y/2+100);
        player1Score.setTranslateX(100);
        root.getChildren().add(player.getScoreBd());
        
        Text player2Score = AI.getScoreBd();
        player2Score.setTranslateY(-MAX_WINDOW_SIZE_Y/2+100);
        player2Score.setTranslateX(-100);
        root.getChildren().add(AI.getScoreBd());
        
        Line centerLine = new Line(0, MAX_WINDOW_SIZE_Y/2, 0, -1*MAX_WINDOW_SIZE_Y/2);
        centerLine.getStrokeDashArray().addAll(20d, 10d, 10d, 10d);
        centerLine.setStrokeWidth(3.0);
        root.getChildren().add(centerLine);

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

    private void multiPlayerSetup() {

        Text bigTitle = new Text(10, 50, "Multiplayer Setup!");
        bigTitle.setFont(new Font(20));
        bigTitle.setTranslateY(-100);
        

        Button hostIt = new Button();
        hostIt.setText("Host");

        Button connectIt = new Button();
        connectIt.setText("Connect");
        connectIt.setTranslateY(50);
        
        
        root.getChildren().add(bigTitle);
        root.getChildren().add(hostIt);
        root.getChildren().add(connectIt);
        
        
        
        hostIt.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(hostIt);
                    root.getChildren().remove(bigTitle);
                    root.getChildren().remove(connectIt);
                    multiPlayerSetupHost();
                }
            });

        
    }
        
    private void multiPlayerSetupHost() {
        System.out.println("Trying to make a server...");
        //Set Sockets 
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            Socket socket = serverSocket.accept();
            
            //System.out.println(socket.toString());
            playMultiPlayer(socket);
        
        }  catch( Exception e )
        {
            System.out.println("Error");
            e.printStackTrace();
        }

    }
    //Multi player logic goes here
    private void playMultiPlayer(Socket socket) {
        System.out.println(-MAX_WINDOW_SIZE_X/2);

        
        root.getChildren().add(player.getRectangle());
        root.getChildren().add(AI.getRectangle());
        root.getChildren().add(ball.getBall());

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