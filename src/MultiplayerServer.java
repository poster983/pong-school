package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Write a description of class Multiplayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MultiplayerServer implements Runnable
{
    private ServerSocket serverSocket;
    
   public void run() {
        try {
              serverSocket = new ServerSocket(1234);
          }catch( Exception e )
        {
            System.out.println("Error");
            e.printStackTrace();
        }
        
         try {
              while(true) { //TESTING THIS IS PLAGERISM
                  Socket test = serverSocket.accept();
                }
          }catch( Exception e )
        {
            System.out.println("Error");
            e.printStackTrace();
        }
        
    }
    
    public static void call() {
        System.out.println("Trying to make server...");
        (new Thread(new MultiplayerServer())).start();
    }
}
