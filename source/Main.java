import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.*;


/**
 * Todo
 * Create board
 * draw dots
 * create button
 */
public class Main {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 5463);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String indata;
            EntityHandler entityHandler = new EntityHandler();

            while((indata = in.readLine()) != null) {
                entityHandler.server_command_update(indata);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
