import java.awt.Graphics;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.*;
import javax.swing.*;
import java.awt.*;


/**
 * Todo
 * Create board
 * draw dots
 * create button
 */
public class Main {

    public static void main(String[] args) {
        try {
            final int WIDTH = 800;
            final int HEIGHT = 500;
            JFrame frame = new JFrame("Saab");
 
            Display display = new Display();
            frame.setSize(WIDTH,HEIGHT);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.add(display);
            frame.setVisible(true);
            
            Socket s = new Socket("localhost", 5463);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String indata;
   

            while((indata = in.readLine()) != null) {
                System.out.println(indata);
                display.update(indata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
