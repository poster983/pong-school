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
    private Socket[] SocketList = new Socket[2];
    BufferedReader input;
    PrintWriter output;
    //Thread
    public void run() {
        try {
            serverSocket = new ServerSocket(1234);
        }catch( Exception e )
        {
            System.out.println("Error");
            e.printStackTrace();
        }

        try {
            int x = 0;
            while(true) {//TESTING THIS IS PLAGERISM

                SocketList[x] = serverSocket.accept();
                if(SocketList[0] != null) {
                    x = 1;
                }
                if(SocketList[0] != null && SocketList[1] != null) {
                    System.out.println("All Players Connected");
                    System.out.println(SocketList[0].toString() + " " + SocketList[1].toString());
                    input = new BufferedReader(
                        new InputStreamReader(SocketList[0].getInputStream()));
                    output = new PrintWriter(SocketList[0].getOutputStream(), true);
                }

            }

        }catch( Exception e )
        {
            System.out.println("Error");
            e.printStackTrace();
        } finally {
            try{
                serverSocket.close();
            } catch( Exception e )
            {
                System.out.println("Error");
                e.printStackTrace();
            }
        }

        //
    }

    public void listenForIn() {
        try {
            while (true) {
                System.out.println(input.readLine());
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
