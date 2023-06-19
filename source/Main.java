import java.io.*;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.*;



public class Main {

    public static void main(String[] args) {
        try {
            //frame set up
            final int WIDTH = 800;
            final int HEIGHT = 500;
            JFrame frame = new JFrame("Saab");
            Display display = new Display();

            frame.setSize(WIDTH,HEIGHT);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(display);
            frame.setVisible(true);
            
            ArrayBlockingQueue<String> inputQueue = new ArrayBlockingQueue<>(100);
            
            //connect to server
            Client client = new Client(inputQueue);
            Thread inputThread = new Thread(client);
            inputThread.start();

            String indata;
            while ((indata = inputQueue.take()) != null) {
                display.update(indata);
                System.out.println(inputQueue.size());

            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }

}
