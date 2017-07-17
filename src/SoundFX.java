package src;

import java.io.*;
import sun.audio.*;
/**
 * Write a description of class SoundFX here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SoundFX
{

    
    public static void CrappyBeep()
    {
        java.awt.Toolkit.getDefaultToolkit().beep();
        checkOs();
    }
    
    public static void BettaBeep() {
    	
    }
    
    public static void checkOs() {
    	String oz = System.getProperty("os.name");
    	
    	if(oz.substring(0, 7).equals("Windows")) {
    		System.out.println(true);
    	}
    	System.out.println(oz.substring(0, 7));
    }
   /*
   public static void main(String args[]) {
       java.awt.Toolkit.getDefaultToolkit().beep();

    }
    */
}
