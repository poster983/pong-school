//package src;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Write a description of class AIPaddle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AIPaddle extends Paddle
{
    
    public AIPaddle(int _x, int _y, int _windowLimits, double _movementSpeed, String _name) {
        super(_x, _y, _windowLimits, _movementSpeed, _name); //the .9 limits the speed of the AI Paddle, because it was too fast, and impossible to beat
        
    }
    
    public void setSpeed(double m) { // multiplies the speed by a multiplier, used to set difficulty of AI.
        movementSpeed *= m;
    }
    
    public void update(double[] ballPos) {
        //System.out.println(currY);
        //rec.setTranslateY(currY += 0.05);
        
        if(ballPos[1] > currY) {
            moveY(movementSpeed);
            //rec.setTranslateY(currY += movementSpeed);
        } else if(ballPos[1] <= currY) {
            moveY(-movementSpeed);
            //rec.setTranslateY(currY -= movementSpeed);
        }
    }
}
