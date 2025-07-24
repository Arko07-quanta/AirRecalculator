package org.buet.sky.airrecalculator;

public class ServerReadThread implements Runnable{
    Network network;
    SharedObject obj;
    public ServerReadThread(Network network, SharedObject obj){
        this.network = network;
        this.obj = obj;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true) {
            obj.readerPush((Command) network.read());
        }
    }
}