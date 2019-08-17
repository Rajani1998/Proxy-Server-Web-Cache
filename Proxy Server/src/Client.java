
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mehdi Raza Rajani
 */
public class Client {
    
    static int port = 5000;
    static Socket s;
    static DataInputStream in;
    static DataInputStream out;
    static PrintStream dos;


    
    static class Multi extends Thread{  
        String name;

        public Multi(String name) {
            this.name = name;
        }        
        
        @Override
        public void run(){
            if (name.equals("1"))
                try {
                    sendURL();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            else
                try {
                    recieveWebFile();
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }  

    static private void sendURL() throws IOException{
        System.out.println("enter url to send: ");
        String str1 = out.readLine();
        dos.println(str1);
        if (str1.equals("end"))
            s.close();
    }
    
    static private void recieveWebFile() throws IOException{
        String str = in.readLine();
        System.out.println("Message Received: " + str);
        if (str.equals("end"))
            s.close();
    }    
    
    
    public static void main(String args[])throws IOException, MalformedURLException, UnknownHostException {
        s = new Socket("localhost", port);
        in = new DataInputStream(s.getInputStream());
        out = new DataInputStream(System.in);
        dos = new PrintStream(s.getOutputStream());        
        
        Multi sendingThread = new Multi("1");
        Multi receivingThread = new Multi("2"); 
        
        while (true) {
            sendingThread.run();
            receivingThread.run();
        }
        
    }
}
