
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
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
public class ProxyServer {
    static int port = 5000;
    static ServerSocket ss;
    static DataInputStream in;
    static DataOutputStream out;
    static PrintStream dos;
    
    static LRU cache = new LRU();
    
    static class Multi extends Thread{  
        String name;

        public Multi(String name) {
            this.name = name;
        }        
        
        @Override
        public void run(){
            if (name.equals("1"))
                try {
                    sendWebFile();
            } catch (IOException ex) {
                Logger.getLogger(ProxyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            else
                try {
                    recieveURL();
            } catch (IOException ex) {
                Logger.getLogger(ProxyServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  
    }  

    static private void sendWebFile() throws IOException{
        System.out.println("enter message to send: ");
        String str = in.readLine();            
        dos.println(str);
        if (str.equals("exit"))
            ss.close();
    }
    
    static private void recieveURL() throws IOException{
        String str1 = in.readLine();
        System.out.println("Message Received: " + str1);
//        out.writeBytes(cache.add(str1).toString());
        dos.println(cache.add(str1));
        cache.display();
        if (str1.equals("end"))
            ss.close();
    }
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        ss = new ServerSocket(port);
        System.out.println("Server is listening to port : " + port);
        Socket s = ss.accept();
        in = new DataInputStream(s.getInputStream());
        out = new DataOutputStream(s.getOutputStream());
        dos = new PrintStream(s.getOutputStream());
        
//        Multi sendingThread = new Multi("1");
//        Multi receivingThread = new Multi("2"); 
        
        while (true) {
//            receivingThread.run();
            recieveURL();
        }
        
        
        
//        LRU lru = new LRU();
//        lru.add("A");
//        lru.display();
//        lru.add("B");
//        lru.display();
//        lru.add("C");
//        lru.display();
//        lru.add("A");
//        lru.display();
//        lru.add("D");
//        lru.display();
//        lru.add("C");
//        lru.display();
//        lru.add("A");
//        lru.display();
//        lru.add("D");
//        lru.display();
//        lru.add("D");
//        lru.display();
//        lru.add("A");
//        lru.display();
//        lru.add("C");




    }
    
}
