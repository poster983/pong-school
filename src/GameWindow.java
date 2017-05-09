import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;


public class GameWindow extends Application {

    private boolean goDown, goUp;
    private final int MAX_WINDOW_SIZE_X = 940;
    private final int MAX_WINDOW_SIZE_Y = 640;
    private final double PADDLE_MOVEMENT_SPEED = 0.4;
    private final double INITIAL_BALL_MOVEMENT_SPEED = 0.02;
    private Paddle player = new Paddle(MAX_WINDOW_SIZE_X-550, 0, MAX_WINDOW_SIZE_Y, PADDLE_MOVEMENT_SPEED);
    private Ball ball = new Ball(0,0, MAX_WINDOW_SIZE_X, MAX_WINDOW_SIZE_Y, INITIAL_BALL_MOVEMENT_SPEED);


    @Override
    public void start(Stage primaryStage) {
        /*
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        */





        StackPane root = new StackPane();
        //root.getChildren().add(btn);
        root.getChildren().add(player.getRectangle());
        root.getChildren().add(ball.getBall());

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Pong!");
        primaryStage.setScene(scene);
        primaryStage.setHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setWidth(MAX_WINDOW_SIZE_X);
        primaryStage.setMinHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setMinWidth(MAX_WINDOW_SIZE_X);
        primaryStage.setMaxHeight(MAX_WINDOW_SIZE_Y);
        primaryStage.setMaxWidth(MAX_WINDOW_SIZE_X);
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
                    case UP:    goUp = true; break;
                    case DOWN:  goDown = true; break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:    goUp = false; break;
                    case DOWN:  goDown = false; break;
                }
            }
        });





        //SHOW the window
        primaryStage.show();

        //Movement timer
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double py = 0;

                if (goDown) py += PADDLE_MOVEMENT_SPEED;
                if (goUp) py += -PADDLE_MOVEMENT_SPEED;


                player.moveY(py);
            }
        };
        timer.start();
    }



    public static void main(String[] args) {
        launch(args);
    }

}