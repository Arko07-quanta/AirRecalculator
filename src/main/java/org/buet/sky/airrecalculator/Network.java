package org.buet.sky.airrecalculator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Network {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;


    public Network(Socket socket) throws IOException {
        this.socket = socket;
        init();
    }

    public Network(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
        init();
    }

    private void init() throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }



    public void write(Object object) throws IOException {
        out.writeUnshared(object);
        out.flush();
    }

    public Object read() throws IOException, ClassNotFoundException {
        return in.readUnshared();
    }


    public void closeConnection() throws IOException {
        socket.close();
        in.close();
        out.close();
    }

}
