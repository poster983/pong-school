package src;

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
    
    public AIPaddle(int _x, int _y, int _windowLimits, double _movementSpeed) {
        super(_x, _y, _windowLimits, _movementSpeed);
        
    }
    
    public void update(double[] ballPos) {
        //System.out.println(currY);
        //rec.setTranslateY(currY += 0.05);
        
        if(ballPos[1] > currY) {
            rec.setTranslateY(currY += 0.5);
        } else if(ballPos[1] <= currY) {
            rec.setTranslateY(currY -= 0.5);
        }
    }
}
