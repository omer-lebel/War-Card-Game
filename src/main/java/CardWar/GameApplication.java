package CardWar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class GameApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(getClass().getResource("CardWar.fxml"));
        System.out.println(getClass().getResource("back.png"));
        System.out.println();
        Parent root = FXMLLoader.load(getClass().getResource("CardWar.fxml"));
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


