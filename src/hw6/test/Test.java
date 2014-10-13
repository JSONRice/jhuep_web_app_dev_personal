package hw6.test;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class Test extends Application implements Initializable {

	@FXML //  fx:id="myButton"
    private Button myButton; // Value injected by FXMLLoader

	private Circle myCircle;
	
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Test.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            StackPane page = (StackPane) FXMLLoader.load(Test.class.getResource("Test.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("FXML is Simple");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert myButton != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        myButton.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent event) {
                System.out.println("That was easy, wasn't it?");
            }
        });
    }
}