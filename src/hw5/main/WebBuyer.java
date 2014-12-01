package hw5.main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WebBuyer extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(WebBuyer.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane page = FXMLLoader.load(WebBuyer.class.getResource("WebBuyer.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("WebBuyer Application");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(WebBuyer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}