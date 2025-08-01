package org.buet.sky.airrecalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main extends Application {
    public static Network network;
    public static SharedObject obj;
    public static Company company;  // contains login info
    public static Boolean loginStatus = false;
    public static Boolean serverStatus = false;
    public static Stage primaryStage;

    // contains all the controller's object
    public static HashMap<Integer, Object> controller;
    public static List<AirPlane> airPlaneList;
    public static List<City>  cityList;
    public static List<AirPlane> allPlaneList;
    public static List<Integer> req;
    public static HashMap<Integer, City> hashCityMap = new HashMap<>();


    public static void showPopup(String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setHeaderText(null);
        alert.setContentText(msg);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                TicketInfoController.class.getResource("/style/MainPage.css").toExternalForm()
        );

        alert.showAndWait();
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        controller = new HashMap<>();
        network = new Network("127.0.0.1", 44444);
        obj = new SharedObject();

        new ClientReadThread(network, obj);
        new ClientWriteThread(network, obj);
        new ClientListenerThread(obj);



        // want updated information about all plane and all city
        req = new ArrayList<>();
        req.add(6);
        req.add(9);
        obj.writerPush(new Command(-1, req));


        Parent root = (new FXMLLoader(HelloApplication.class.getResource("MainPage.fxml"))).load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Air Recalulator");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        primaryStage.show();
        //((CityMap)controller.get(30)).redrawMap(cityList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}






//
//
//
//public class Main extends Application {
//
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
//        Scene scene = new Scene(root);
//        //primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Air Recalculator");
//        primaryStage.show();
//        primaryStage.centerOnScreen();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//


