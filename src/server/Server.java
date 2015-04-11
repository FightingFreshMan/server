/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerJFrame;

/**
 *
 * @author my
 */
public class Server {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    private static DataInputStream dataFromClient = null;
    private static DataOutputStream dataToClient = null;
    private static DataInputStream dataFromClient_1 = null;
    private static DataOutputStream dataToClient_1 = null;
    private static DataInputStream dataFromClient_2 = null;
    private static DataOutputStream dataToClient_2 = null;
    private static ExecutorService exe = null;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ServerJFrame sjf = new ServerJFrame();
        sjf.show();
        //server start 
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket client_1 = null;
        Socket client_2 = null;
        ServerJFrame.serverMessageArea.append(new Date() + "Server started at socket 8888" + '\n');

        exe = Executors.newCachedThreadPool();
        try {
            int count = 0;
            // while (true) {
            client_1 = serverSocket.accept();//client_1 connect to server               
            ServerJFrame.serverMessageArea.append(new Date()
                    + "client " + ++count + " had connected to server,and the ip of client " + count + " is "
                    + client_1.getInetAddress().getHostAddress() + '\n');

            client_2 = serverSocket.accept();//client_2 connect to server               
            ServerJFrame.serverMessageArea.append(new Date()
                    + "client " + ++count + " had connected to server,and the ip of client " + count + " is "
                    + client_2.getInetAddress().getHostAddress() + '\n');
            dataFromClient_1 = new DataInputStream(client_1.getInputStream());
            dataToClient_1 = new DataOutputStream(client_1.getOutputStream());
            dataFromClient_2 = new DataInputStream(client_2.getInputStream());
            dataToClient_2 = new DataOutputStream(client_2.getOutputStream());

            exe.execute(new Client_1To2());
            exe.execute(new Client_2To1());
            
            /*Runnable task=new SendToServer(socket);
             Thread thread=new Thread(task);
             thread.start();*/
            // }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           /* exe.shutdown();
             dataFromClient_1.close();
             dataToClient_1.close();
             dataFromClient_2.close();
              dataToClient_2.close();
             client_1.close();
             client_2.close();
             serverSocket.close();*/
        }

    }

    private static class Client_2To1 implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {           
                    dataToClient_1.writeUTF(dataFromClient_2.readUTF());
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static class Client_1To2 implements Runnable {
       @Override
        public void run() {
            try {
                while (true) {  
                    dataToClient_2.writeUTF(dataFromClient_1.readUTF());
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
