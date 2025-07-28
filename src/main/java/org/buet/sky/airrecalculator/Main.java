package org.buet.sky.airrecalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class Main extends Application {
    public static Network network;
    public static SharedObject obj;
    public static Company company;  // contains login info
    public static Boolean loginStatus = false;
    public static Boolean serverStatus = false;

    // contains all the controller's object
    public static HashMap<Integer, Object> controller;
    public static List<AirPlane> airPlaneList;
    public static List<City>  cityList;
    public static List<AirPlane> allPlaneList;

    public static void showPopup(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new HashMap<>();
        network = new Network("127.0.0.1", 44444);
        obj = new SharedObject();

        new ClientReadThread(network, obj);
        new ClientWriteThread(network, obj);
        new ClientListenerThread(obj);



        Parent root = (new FXMLLoader(Main.class.getResource("MainPage.fxml"))).load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("City Mapper");
        primaryStage.show();
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


