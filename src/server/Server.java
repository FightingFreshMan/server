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
    static DataInputStream dataFromClient = null;
    static DataOutputStream dataToClient = null;
    static DataInputStream dataFromClient_1 = null;
    static DataOutputStream dataToClient_1 = null;
    static DataInputStream dataFromClient_2 = null;
    static DataOutputStream dataToClient_2 = null;
    static ExecutorService exe = null;

    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ServerJFrame sjf = new ServerJFrame();
        sjf.show();
        //server start 
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket client_1 = null;
        Socket client_2 = null;
        ServerJFrame.serverMessageArea.append(new Date() + "Server started at socket 8888" + '\n');

        /* Socket client_1 = serverSocket.accept();//client_1 connect to server
         dataFromClient_1 = new DataInputStream(client_1.getInputStream());
         dataToClient_1 = new DataOutputStream(client_1.getOutputStream());
         ServerJFrame.serverMessageArea.append(new Date() 
         + "client_1 had connected to server,and the ip of client_1 is " 
         + client_1.getInetAddress().getHostAddress() + '\n');

         /*Socket client_2 =serverSocket.accept();//client_2 connect to server 
         dataFromClient_2 = new DataInputStream(client_1.getInputStream());
         dataToClient_2 = new DataOutputStream(client_1.getOutputStream());
         ServerJFrame.serverMessageArea.append(new Date()
         +"client_2 had connected to server,and the ip of client_1 is "client_1.getInetAddress().getHostAddress()
         +'\n');
         */
        exe = Executors.newCachedThreadPool();
        try {
            int count = 0;
            while (true) {
                client_1 = serverSocket.accept();//client_1 connect to server               
                ServerJFrame.serverMessageArea.append(new Date()
                        + "client " + ++count + " had connected to server,and the ip of client " + count + " is "
                        + client_1.getInetAddress().getHostAddress() + '\n');
                

                /*Runnable task=new SendToServer(socket);
                 Thread thread=new Thread(task);
                 thread.start();*/
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            exe.shutdown();
            client_1.close();
            client_2.close();
            serverSocket.close();
        }

    }

    private static class ExchangeMessage implements Runnable {
        
        public void run() {

        }

    }

    //实现多客户端访问服务器
    private static class SendToServer implements Runnable {

        Socket socket = new Socket();
        //  public static DataInputStream dataFromClient_1 = null;
        // public static DataOutputStream dataToClient_1 = null;

        public SendToServer(Socket socket) {
            try {
                this.socket = socket;
                dataFromClient = new DataInputStream(socket.getInputStream());
                dataToClient = new DataOutputStream(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void run() {
            try {
                ServerJFrame.serverMessageArea.append("Client:\n\t" + dataFromClient_1.readUTF() + '\n');

            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
