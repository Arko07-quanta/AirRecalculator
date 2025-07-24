package org.buet.sky.airrecalculator;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Set;


public class Server {
    private ServerSocket serverSocket;
    public static HashMap<Integer, Set<Integer>> requireGraph;
    public static HashMap<Integer, SharedObject> clientObject;



    int clientId = 0;
    Server(){
        try{
            serverSocket = new ServerSocket(44444);
            while(true){
                clientId++;
                Network nt = new Network(serverSocket.accept());
                SharedObject obj = new SharedObject();

                clientObject.put(clientId, obj);


                new ServerWriteThread(nt, obj);
                new ServerReadThread(nt, obj);
                new ServerListenerThread(obj, clientId);
            }
        } catch (Exception e) {
            System.out.println("Server could not be started");
        }
    }
    public static void main(String[] args){
        new Server();
    }
}
