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
            Object ob = network.read();
            Integer status = (Integer) ob;
            if(status == 1) {
                City city = (City) network.read();
                DataBase.addCity(city);
            }



        }

    }
}