package org.buet.sky.airrecalculator;


import java.util.List;

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

            if(objChecker.isDijkstra()){
                System.out.println("Dijkstra closed");
                List<City> dij = objChecker.getDijkstraObj();
                ((CityMap) Main.controller.get(6)).drawWithEdges(dij);
                try {
                    Thread.sleep(7000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }



            if(objChecker.getAllCity()) {
                Main.cityList = objChecker.getAllCityObj();
                ((CityMap) Main.controller.get(6)).redrawMap(objChecker.getAllCityObj());
                AirPlane airPlane = new AirPlane("name", 100, 3.0, 0, 0);
                airPlane.setMileage(2);
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                Dijkstra dijkstra = new Dijkstra(airPlane, Main.cityList.get(0), Main.cityList.get(Main.cityList.size() -1), Main.cityList, true);
                Dijkstra dijkstra2 = new Dijkstra(airPlane, Main.cityList.get(0), Main.cityList.get(Main.cityList.size() -1), Main.cityList, false);
            }



            if(objChecker.isLogin()){
                Company company = objChecker.getAccountObj();
                if(company != null){
                    Main.serverStatus = true;
                    Main.company = company;
                }
                else{
                    Main.serverStatus = true;
                }


            }

            if(objChecker.getMyPlane()){
                Main.airPlaneList = objChecker.getAllPlaneObj();
                ((PlaneInfoController) Main.controller.get(9)).refreshPlanes();
            }

        }
    }

}
