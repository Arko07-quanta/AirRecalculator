package org.buet.sky.airrecalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;





public class Main extends Application {
    public static Network network;
    public static SharedObject obj;


    @Override
    public void start(Stage primaryStage) throws Exception {
        network = new Network("127.0.0.1", 44444);
        obj = new SharedObject();


        new ClientReadThread(network, obj);
        new ClientWriteThread(network, obj);


        Parent root = (new FXMLLoader(HelloApplication.class.getResource("MainPage.fxml"))).load();
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


