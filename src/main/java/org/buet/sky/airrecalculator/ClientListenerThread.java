package org.buet.sky.airrecalculator;

import java.util.List;

public class ClientListenerThread implements Runnable {
    SharedObject obj;
    ClientListenerThread(SharedObject obj) {
        this.obj = obj;
    }

    public void run() {
        Integer opt = (Integer) obj.readerPop();
        if(opt == 6){
            ((CityMap) Main.controller.get(opt)).redrawMap((List<City>) obj.readerPop());
        }
    }

}
