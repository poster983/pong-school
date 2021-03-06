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

public class GameWindow extends Application         {

    private boolean goDown, goUp;
    private boolean goDown2, goUp2;
    private static final int MAX_WINDOW_SIZE_X = 940; //940
    private static final int MAX_WINDOW_SIZE_Y = 640; // 640
    private static final double PADDLE_MOVEMENT_SPEED = 0.7;//0.5
    private static final double INITIAL_BALL_MOVEMENT_SPEED = 0.9; //0.9
    private static final double WIN_SCORE = 10.0;
    private static Paddle player = new Paddle(MAX_WINDOW_SIZE_X/2-50, 0, MAX_WINDOW_SIZE_Y, PADDLE_MOVEMENT_SPEED, "Player 1");
    private static Paddle playerTwo = new Paddle(-MAX_WINDOW_SIZE_X/2+50, 0, MAX_WINDOW_SIZE_Y, PADDLE_MOVEMENT_SPEED, "Player 2");
    private static AIPaddle AI = new AIPaddle(-MAX_WINDOW_SIZE_X/2+50, 0, MAX_WINDOW_SIZE_Y, PADDLE_MOVEMENT_SPEED, "AI");
    //
    private static Ball ball; //declared in the start methods to work with the right paddles
    
    private static Timer timer = new Timer();

    private static StackPane root = new StackPane();
    private Scene scene = new Scene(root, 300, 250);

    //Networking
    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;

    @Override
    public void start(Stage primaryStage) {

        Button singlePlayerButton = new Button();
        singlePlayerButton.setText("Singleplayer");
        
        Button multiPlayerButton1 = new Button();
        multiPlayerButton1.setText("Local Multiplayer");
        multiPlayerButton1.setTranslateY(50);
        
        Button multiPlayerButton2 = new Button();
        multiPlayerButton2.setText("Online Multiplayer");
        multiPlayerButton2.setTranslateY(100);

        Text bigTitle = new Text(10, 50, "Pong!");
        bigTitle.setFont(new Font(20));
        bigTitle.setTranslateY(-100);
        singlePlayerButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(singlePlayerButton);
                    root.getChildren().remove(bigTitle);
                    root.getChildren().remove(multiPlayerButton1);
                    root.getChildren().remove(multiPlayerButton2);
                    singleplayerSetup();
                }
            });
            
        multiPlayerButton1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(singlePlayerButton);
                    root.getChildren().remove(bigTitle);
                    root.getChildren().remove(multiPlayerButton1);
                    root.getChildren().remove(multiPlayerButton2);
                    playMultiplayer();
                }
            });
            
        multiPlayerButton2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(singlePlayerButton);
                    root.getChildren().remove(bigTitle);
                    root.getChildren().remove(multiPlayerButton1);
                    root.getChildren().remove(multiPlayerButton2);
                    multiPlayerSetup();
                }
            });

        root.getChildren().add(bigTitle);

        root.getChildren().add(singlePlayerButton);

        root.getChildren().add(multiPlayerButton1);
        root.getChildren().add(multiPlayerButton2);
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
    
    private void singleplayerSetup() {
        Button levelOne = new Button();
        levelOne.setText("Easy AI");
        levelOne.setTranslateY(-100);
        
        Button levelTwo = new Button();
        levelTwo.setText("Medium AI");
        levelTwo.setTranslateY(-50);
        
        Button levelThree = new Button();
        levelThree.setText("Hard AI");
        
        Button levelFour = new Button();
        levelFour.setText("Literally Impossible");
        levelFour.setTranslateY(50);
        
        levelOne.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(levelOne);
                    root.getChildren().remove(levelTwo);
                    root.getChildren().remove(levelThree);
                    root.getChildren().remove(levelFour);
                    AI.setSpeed(0.5);
                    playSinglePlayer();
                }
            });
        
        levelTwo.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(levelOne);
                    root.getChildren().remove(levelTwo);
                    root.getChildren().remove(levelThree);
                    root.getChildren().remove(levelFour);
                    AI.setSpeed(0.7);
                    playSinglePlayer();
                }
            });
                       
        levelThree.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(levelOne);
                    root.getChildren().remove(levelTwo);
                    root.getChildren().remove(levelThree);
                    root.getChildren().remove(levelFour);
                    AI.setSpeed(0.9);
                    playSinglePlayer();
                }
            });
                       
        levelFour.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(levelOne);
                    root.getChildren().remove(levelTwo);
                    root.getChildren().remove(levelThree);
                    root.getChildren().remove(levelFour);
                    playSinglePlayer();
                }
            });
        
        root.getChildren().add(levelOne);
        root.getChildren().add(levelTwo);
        root.getChildren().add(levelThree);
        root.getChildren().add(levelFour);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    //Check for Ball paddle Collision
    public static Path checkCollision(Paddle thisPaddle){
        
        System.out.println((Path)Shape.intersect(thisPaddle.getRectangle(), ball.getBall()));
        return (Path)Shape.intersect(thisPaddle.getRectangle(), ball.getBall());

    }

    public static boolean checkEndCondition(Paddle thisPaddle) {
        if(WIN_SCORE <= thisPaddle.getScore()) {
            System.out.println(WIN_SCORE);
            System.out.println(thisPaddle.getScore());
            root.getChildren().clear();
            timer.cancel();
            Text winner = new Text(10, 50, thisPaddle.getName() + " Wins!");
            winner.setFont(new Font(20));
            winner.setTranslateY(-100);
            root.getChildren().add(winner);
            return true;
        } else {
            return false;
        }
    }
    //Single player logic goes here
    private void playSinglePlayer() {
        System.out.println(-MAX_WINDOW_SIZE_X/2);
        
        ball = new Ball(-400, 0, MAX_WINDOW_SIZE_X, MAX_WINDOW_SIZE_Y, INITIAL_BALL_MOVEMENT_SPEED, new Paddle[]{player, AI});
        
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
    
    private void playMultiplayer() {
        System.out.println(-MAX_WINDOW_SIZE_X/2);
        
        ball = new Ball(-400,0, MAX_WINDOW_SIZE_X, MAX_WINDOW_SIZE_Y, INITIAL_BALL_MOVEMENT_SPEED, new Paddle[]{player, playerTwo});

        root.getChildren().add(player.getRectangle());
        root.getChildren().add(playerTwo.getRectangle());
        root.getChildren().add(ball.getBall());
        Text player1Score = player.getScoreBd();
        player1Score.setTranslateY(-MAX_WINDOW_SIZE_Y/2+100);
        player1Score.setTranslateX(100);
        root.getChildren().add(player.getScoreBd());

        Text player2Score = playerTwo.getScoreBd();
        player2Score.setTranslateY(-MAX_WINDOW_SIZE_Y/2+100);
        player2Score.setTranslateX(-100);
        root.getChildren().add(playerTwo.getScoreBd());

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
                        case W:
                        goUp2 = true;
                        break;
                        case S:
                        goDown2 = true;
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
                        case W:
                        goUp2 = false;
                        break;
                        case S:
                        goDown2 = false;
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
                                double py2 = 0;

                                if (goDown2) py2 += PADDLE_MOVEMENT_SPEED;
                                if (goUp2) py2 += -PADDLE_MOVEMENT_SPEED;

                                playerTwo.moveY(py2);
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

        //Listens for Host Button press
        hostIt.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(hostIt);
                    root.getChildren().remove(bigTitle);
                    root.getChildren().remove(connectIt);
                    multiPlayerSetupHost();
                }
            });

        //Listens for Connect Button press
        connectIt.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    root.getChildren().remove(hostIt);
                    root.getChildren().remove(bigTitle);
                    root.getChildren().remove(connectIt);
                    multiPlayerSetupConnect();
                }
            });

    }

    private void multiPlayerSetupHost() {
        System.out.println("Trying to make a server...");
        //Set Sockets 
        MultiplayerServer.call();

    }

    private void multiPlayerSetupConnect() {
        System.out.println("Trying to Connect to a server...");
        //Set Sockets 
        try {
            clientSocket = new Socket("localhost", 1234);  //LocalHost For Now
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //GOT FROM TIC TAC TOE
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true); //GOT FROM TIC TAC TOE
            System.out.println("Connected");
            TESTINGSOCKETS("Connected");
        } catch( Exception e )
        {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    private void TESTINGSOCKETS(String Message ) {
        try {

            socketOut.println(Message);

        }catch( Exception e )
        {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    //Multi player LAN logic goes here
    private void playMultiPlayerLan(Socket socket) {
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