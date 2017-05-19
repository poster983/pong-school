package src;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Path;
import java.util.*;
import javafx.application.Platform;
/**
 * Created by joseph on 5/8/17.
 */
public class Ball {
    private Rectangle ball;
    private double movementSpeed;
    private double[] location;
    private int[] windowLimits;
    private int[] size = {10,10};
    private double[] velocity = {1.0,0.5}; // 1.0,0.5
    //for collision detection AND performance
    private Paddle[] Paddles;
    
    
    private static Timer timer = new Timer();

    public Ball(double _startX, double _startY, int _windowLimitsX, int _windowLimitsY, double _movementSpeed, Paddle[] _paddleArray) {

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

        //Get paddle info
        Paddles = _paddleArray;

       
        
    }
    //gets the ball object
    public Rectangle getBall() {
        return ball;
    }
    
    public double[] getY() {
        return location;
    }
    // Ball clock

    
    //Move ball (called vrom main clock
    public void updateBall() {

        //System.out.println(location[0]);
        location[0] = location[0] + (velocity[0] * movementSpeed);
        location[1] = location[1] + (velocity[1] * movementSpeed);
        ball.setTranslateX(location[0]);
        ball.setTranslateY(location[1]);
        
        //DEBUG ONLY FOR GLITCH THROUGH
        /*
         if((Paddles[1].getCurrX() > location[0] - Paddles[1].getWidth() +30 ) && ((location[1] < Paddles[1].getHeight()+ Paddles[1].getCurrY()) && (location[1] > Paddles[1].getHeight()))) {
             movementSpeed = 0.2;
            }
        */
        //Check for col with right Paddle
        if(Paddles[0].getCurrX() <= location[0]+Paddles[0].getWidth()) {
            System.out.println("Check Right");
            if (GameWindow.checkCollision(Paddles[0]).getElements().size() > 0) {
                //Calculate vector thingy for paddle collision detection
                System.out.println("Hit Right");
                double relativeIntersectY = (Paddles[0].getCurrY()+(Paddles[0].getHeight()/2)) - location[1];
                double normalizedRelativeIntersectionY = (relativeIntersectY/(Paddles[0].getHeight()/2));
                double bounceAngle = normalizedRelativeIntersectionY * 75;
                double ballVx = (((movementSpeed)*Math.cos(bounceAngle)) );
                double ballVy = (((movementSpeed)*-Math.sin(bounceAngle)));
                /*
                System.out.println("Rel intersect Y: " + relativeIntersectY);
                System.out.println("Norm intersect Y: " + normalizedRelativeIntersectionY);
                System.out.println("Bounce aqng: " + bounceAngle);
                System.out.println(ballVx);
                System.out.println(ballVy);
                System.out.println("________ \n");
                */
                //Play crappy windows beep 
                SoundFX.CrappyBeep();
                
                
                
                velocity = new double[]{ballVx, ballVy};
                 
                //velocity = new double[]{-1*velocity[0], velocity[1]};
            }
        }
        //Check Col with left Paddle
         if(Paddles[1].getCurrX() > location[0] - Paddles[1].getWidth() ) {

            if (GameWindow.checkCollision(Paddles[1]).getElements().size() > 0) {
                //Calculate vector thingy for paddle collision detection
                System.out.println("Hit Left");
                double relativeIntersectY = (Paddles[1].getCurrY()+(Paddles[1].getHeight()/2)) - location[1];
                double normalizedRelativeIntersectionY = (relativeIntersectY/(Paddles[1].getHeight()/2));
                double bounceAngle = normalizedRelativeIntersectionY * -75;
                double ballVx = (((movementSpeed)*Math.cos(bounceAngle)) );
                double ballVy = (((movementSpeed)*-Math.sin(bounceAngle)));
                /*
                System.out.println("Rel intersect Y: " + relativeIntersectY);
                System.out.println("Norm intersect Y: " + normalizedRelativeIntersectionY);
                System.out.println("Bounce aqng: " + bounceAngle);
                System.out.println(ballVx);
                System.out.println(ballVy);
                System.out.println("________ \n");
                */
               
               //Play crappy windows beep 
                SoundFX.CrappyBeep();
                
                
                velocity = new double[]{ballVx, ballVy};
                 
                //velocity = new double[]{-1*velocity[0], velocity[1]};
            }
        }
        
        // check for col with bottom window border
                if(location[1] >= (windowLimits[1] / 2) - size[1]) {
                    //System.out.println(windowLimits[1]);
                    //System.out.println(location[1]);
                    velocity = new double[]{velocity[0], -1 * velocity[1]};
                    
                    SoundFX.CrappyBeep();
                }

                // check for col with left wall
                if(location[0] <= -1 * windowLimits[0] / 2) {
                    //System.out.println(windowLimits[1]);
                    //System.out.println(location[0]);
                    //velocity = new double[]{-1*velocity[0], velocity[1]};
                    Paddles[0].incScore(1);
                    ball.setTranslateX(0);
                    ball.setTranslateY(0);
                    location[0] = 0;
                    location[1] = 0;
                    velocity = new double[]{-1 * velocity[0], -1 * velocity[1]};
                    //System.out.println("hit");
                }
                // check for col with left wall QUICK TEST
                /*if(location[0] <= 0) {
                //System.out.println(windowLimits[1]);
                //System.out.println(location[0]);
                velocity = new double[]{-1*velocity[0], velocity[1]};
                }*/

                // check for col with Top window border
                if(location[1] <= (-1 * windowLimits[1] / 2) + size[1]){
                    //System.out.println(windowLimits[1]);
                    //System.out.println(location[1]);
                    velocity = new double[]{velocity[0], -1 * velocity[1]};
                    
                    SoundFX.CrappyBeep();
                }

                // check for col with right window border
                if(location[0] >= (windowLimits[0] / 2)){
                    //System.out.println(windowLimits[1]);
                    //System.out.println(location[1]);
                    Paddles[1].incScore(1);
                    ball.setTranslateX(0);
                    ball.setTranslateY(0);
                    location[0] = 0;
                    location[1] = 0;
                    velocity = new double[]{-1 * velocity[0], -1 * velocity[1]};
                    //velocity = new double[]{velocity[0], velocity[1]};
                }
    }


}
