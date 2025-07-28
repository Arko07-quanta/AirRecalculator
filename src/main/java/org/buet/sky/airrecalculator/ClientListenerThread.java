package org.buet.sky.airrecalculator;


public class ClientListenerThread implements Runnable {
    SharedObject obj;
    ClientListenerThread(SharedObject obj) {
        this.obj = obj;
        new Thread(this).start();
    }

    public void run() {
        while (true) {
            Command cmd = obj.readerPop();
            ObjectChecker objChecker = new ObjectChecker(cmd);

            if(objChecker.closeThread()){
                System.out.println("Thread closed");
                break;
            }

            if (objChecker.getAllCity()) {
                ((CityMap) Main.controller.get(6)).redrawMap(objChecker.getAllCityObj());
            }

            if(objChecker.isLogin()){
                Company company = objChecker.getAccountObj();
                Main.serverStatus = true;
                Main.company = company;


            }

            if(objChecker.getAllPlane()){
                Main.airPlaneList = objChecker.getAllPlaneObj();
                ((PlaneInfoController) Main.controller.get(9)).initialize();
            }

        }
    }

}
