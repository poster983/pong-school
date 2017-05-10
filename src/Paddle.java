package src;

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
public class Paddle {
    protected Rectangle rec;
    private double currX;
    protected double currY;
    private int windowLimits;
    private double movementSpeed;

    //in vars for collision
    private int width = 10;
    private int height = 90;


    public Paddle(int _x, int _y, int _windowLimits, double _movementSpeed) {
        rec = new Rectangle(0, 0, width, height);
        rec.setFill(Color.BLACK);
        rec.setTranslateX(_x);
        currX = _x;
        rec.setTranslateY(_y);
        currY = _y;

        windowLimits = _windowLimits;
        movementSpeed = _movementSpeed;
    }

    //Accessor Methods

    public double getCurrX() {
        return currX;
    }
    public double getCurrY() {
        return currY;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }


    //gets the rectangle object
    public Rectangle getRectangle() {
        return rec;
    }
    //called from arrow key event in GameWindow.java
    public void moveY(double _y) {
        //System.out.println(-windowLimits/2);
        //System.out.println(_y);
        //Collision Checker with window
        if((((windowLimits/2)-height/2 > currY && _y == movementSpeed) || _y == -movementSpeed) && ((-windowLimits/2+height/2 < currY && _y == -movementSpeed) || _y == movementSpeed)) { //  ((windowLimits/2 < currY && _y == -movementSpeed) || _y == movementSpeed))
            currY += _y;
            rec.setTranslateY(currY);
        }


        //System.out.println(currY);

    }
}
