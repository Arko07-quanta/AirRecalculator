package org.buet.sky.airrecalculator;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
                List<City> dij = objChecker.getDijkstraObj();
                System.out.println(objChecker.getDijkstraObj());

//
//                Stage stage = new Stage();
//                Parent root = null;
//                try {
//                    root = FXMLLoader.load(getClass().getResource("MyCity.fxml"));
//                } catch (IOException e) {
//                    System.out.println("FXML loader error");
//                    throw new RuntimeException(e);
//                }
//                Scene scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();


                ((CityMap) Main.controller.get(30)).drawWithEdges(dij);
            }



            if(objChecker.getAllCity()) {
                Main.cityList = objChecker.getAllCityObj();
                Platform.runLater(() -> {
                    ((RequestPageController) Main.controller.get(6)).populateComboBoxes();
                });
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
                for(AirPlane airPlane:Main.airPlaneList){
                    System.out.println(airPlane);
                }

                try{
                    Platform.runLater(() -> {
                        ((PlaneInfoController) Main.controller.get(18)).refreshPlanes();
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

                try{
                    Platform.runLater(() -> {
                        ((RequestPageController) Main.controller.get(6)).populateComboBoxes();
                    });
                }catch(Exception e){
                    e.printStackTrace();
                }
            }


        }
    }

}
