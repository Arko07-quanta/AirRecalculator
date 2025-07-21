package org.buet.sky.airrecalculator;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;


public class Network {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;



    public Network(Socket socket){
        this.socket = socket;
        init();
    }

    public Network(String ip, int port){
        try {
            this.socket = new Socket(ip, port);
        }catch (Exception e){
            System.out.println("Could not connect to the server");
            System.out.println(e);
        }
        init();
    }



    private void init(){
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    public void write(Object object){
        try {
            out.writeUnshared(object);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public Object read(){
        try {
            return in.readUnshared();
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void closeConnection(){
        try {
            socket.close();
            in.close();
            out.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
