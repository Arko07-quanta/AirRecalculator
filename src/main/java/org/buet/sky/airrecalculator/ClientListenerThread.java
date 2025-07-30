package org.buet.sky.airrecalculator;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientListenerThread implements Runnable {
    SharedObject obj;

    ClientListenerThread(SharedObject obj) {
        this.obj = obj;
        new Thread(this).start();
    }



    public void refreshWindow() {
        try {
            Platform.runLater(() -> {
                ((RequestPageController) Main.controller.get(6)).loadData();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Platform.runLater(() -> {
                ((PlaneInfoController) Main.controller.get(18)).loadData();
            });
        } catch (Exception e) {
            System.out.println("PlaneInfo refresh failed");
        }

        try {
            Platform.runLater(() -> {
                ((SchedulePageController) Main.controller.get(9)).loadData();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Platform.runLater(() -> {
                ((ProfilePageController) Main.controller.get(15)).loadData();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            Platform.runLater(() -> {
                ((TicketInfoController)Main.controller.get(60)).loadSeats();
            });
        }catch(Exception e){
            System.out.println("Ticket refresh failed");
        }

    }


    public void run() {
        while (true) {
            Command cmd = obj.readerPop();
            ObjectChecker objChecker = new ObjectChecker(cmd);

            if (objChecker.closeThread()) {
                System.out.println("Thread closed");
                break;
            }

            if (objChecker.isDijkstra()) {
                List<City> dij = objChecker.getDijkstraObj();

                Platform.runLater(() -> {
                    Stage stage = new Stage();
                    stage.setResizable(false);
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("CityMap.fxml"));
                        stage.setScene(new Scene(root));
                        stage.show();
                        ((CityMap) Main.controller.get(30)).redrawMap(Main.cityList);
                        ((CityMap) Main.controller.get(30)).drawWithEdges(dij);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }

            if (objChecker.isLogOut()) {
                Main.loginStatus = false;

                try {
                    Platform.runLater(() -> {
                        LoginPage loginPage = new LoginPage(Main.primaryStage);
                        try {
                            loginPage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (Exception e) {
                    System.out.println("Failed to Log out");
                }
            }


            if (objChecker.getAllCity()) {
                Main.cityList = objChecker.getAllCityObj();
                for (City city : Main.cityList) {
                    Main.hashCityMap.put(city.getId(), city);
                }
            }


            if (objChecker.isLogin()) {
                Company company = objChecker.getAccountObj();
                if (company != null) {
                    List<Integer> list = new ArrayList<>();
                    list.addAll(Main.req);
                    list.add(18);
                    obj.writerPush(new Command(-1, list));
                    Main.serverStatus = true;
                    Main.company = company;
                } else {
                    Main.serverStatus = true;
                }

            }

            if (objChecker.getMyPlane()) {
                Main.airPlaneList = objChecker.getAllPlaneObj();
            }

            if (objChecker.getAllPlane()) {
                Main.allPlaneList = objChecker.getAllPlaneObj();
            }

            refreshWindow();
        }

    }
}
