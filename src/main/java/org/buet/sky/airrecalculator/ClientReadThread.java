package org.buet.sky.airrecalculator;

public class ClientReadThread implements Runnable{
    Network network;
    SharedObject obj;
    public ClientReadThread(Network network, SharedObject obj){
        this.network = network;
        this.obj = obj;
        new Thread(this).start();
    }


    @Override
    public void run() {
        while(true) {
            try {
                obj.readerPush((Command) network.read());
            }catch(Exception e){
                obj.readerPush(new Command(-100, null));
                break;
            }
        }
    }
}