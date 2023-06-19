import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

public class Client implements Runnable {

    Socket s;
    BufferedReader in;
    ArrayBlockingQueue<String> input;
    public Client(ArrayBlockingQueue<String> inputQueue) {
        input = inputQueue;
        try {
            s = new Socket("localhost", 5463);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }
   
    @Override
    public void run() {
        String indata;
        try {
            while((indata = in.readLine()) != null) 
            {
                input.put(indata);
            }
            s.close();
        } catch (Exception e) {
           System.out.println(e.getStackTrace());
        }
        
    }
}
