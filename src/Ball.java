import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
/**
 * Created by joseph on 5/8/17.
 */
public class Ball {
    private Rectangle ball;
    private double movementSpeed;
    private double[] location;
    private int[] windowLimits;
    private int[] size = {10,10};
    private int[] velocity = {1,1};

    public Ball(double _startX, double _startY, int _windowLimitsX, int _windowLimitsY, double _movementSpeed) {


        ball = new Rectangle(0, 0, size[0], size[1]);

        location = new double[2];
        location[0] = _startX;
        location[1] = _startY;
        ball.setTranslateX(location[0]);
        ball.setTranslateY(location[1]);

        windowLimits = new int[2];
        windowLimits[0] = _windowLimitsX;
        windowLimits[1] = _windowLimitsY;

        movementSpeed = _movementSpeed;

        //start movement
        timer.start();
    }
    //gets the ball object
    public Rectangle getBall() {
        return ball;
    }

    // Ball clock
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {

            location[0] = location[0] + (velocity[0] * movementSpeed);
            location[1] = location[1] + (velocity[1] * movementSpeed);
            ball.setTranslateX(location[0]);
            ball.setTranslateY(location[1]);
        }
    };

}
