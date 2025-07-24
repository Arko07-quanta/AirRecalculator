package org.buet.sky.airrecalculator;

import java.net.ServerSocket;
import java.util.HashMap;


public class Server {
    private ServerSocket serverSocket;

    public static HashGraph requireGraph, companyClient;

    public static HashMap<Integer, SharedObject> clientObject;

    public static SharedObject dataBaseListener;





    int clientId = 1000;
    Server(){
        clientObject = new HashMap<>();
        requireGraph = new HashGraph();
        companyClient = new HashGraph();
        dataBaseListener = new SharedObject();
        new DataBaseListener();

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
            e.printStackTrace();
            System.out.println("Server could not be started");
        }
    }
    public static void main(String[] args){
        new Server();
    }
}
