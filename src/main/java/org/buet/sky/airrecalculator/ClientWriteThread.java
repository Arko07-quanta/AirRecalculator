package org.buet.sky.airrecalculator;


import java.util.List;

public class ClientWriteThread implements Runnable{
    Network network;
    SharedObject obj;
    public ClientWriteThread(Network network, SharedObject obj){
        this.network = network;
        this.obj = obj;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true){
            try {
                network.write(obj.writerPop());
            }catch(Exception e){
                obj.readerPush(new Command(-100, null));
            }
        }
    }


}
