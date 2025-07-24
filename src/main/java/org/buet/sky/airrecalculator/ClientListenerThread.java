package org.buet.sky.airrecalculator;

import java.util.List;

public class ClientListenerThread implements Runnable {
    SharedObject obj;
    ClientListenerThread(SharedObject obj) {
        this.obj = obj;
    }

    public void run() {
        Command cmd = obj.readerPop();
        ObjectChecker objChecker = new ObjectChecker(cmd);

        if(objChecker.isMap()){
            ((CityMap) Main.controller.get(6)).redrawMap(objChecker.getMap());
        }
    }

}
