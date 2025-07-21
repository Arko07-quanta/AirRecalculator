package org.buet.sky.airrecalculator;

public class ServerWriteThread implements Runnable{
    Network network;
    SharedObject obj;
    public ServerWriteThread(Network network, SharedObject obj){
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


            obj.push(status);
        }

        }
    }