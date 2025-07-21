package org.buet.sky.airrecalculator;

import java.net.ServerSocket;




public class Server {
    private ServerSocket serverSocket;

    Server(){
        try{
            serverSocket = new ServerSocket(44444);
            while(true){
                Network nt = new Network(serverSocket.accept());
                SharedObject obj = new SharedObject();
                new ServerWriteThread(nt, obj);
                new ServerReadThread(nt, obj);
            }
        } catch (Exception e) {
            System.out.println("Server could not be started");
        }
    }
    public static void main(String[] args){
        new Server();
    }
}
