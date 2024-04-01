package WarCard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("WarCard.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("The card war game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        System.out.println();
    }
}


