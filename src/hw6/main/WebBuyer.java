package hw6.main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class WebBuyer extends Application implements Initializable {

    @FXML
    private Button btOK;
	
    @FXML
    private Button btCancel;
    
    @FXML
    private Button btSetup;
    
    @FXML
    private Button btHelp;
    
    @FXML
    private RadioButton rbtSelection;

    @FXML
    private RadioButton rbtAll;

    @FXML
    private RadioButton rbtApplet;

    @FXML
    private CheckBox cbImage;

    @FXML
    private CheckBox cbText;

    @FXML
    private CheckBox cbCode;
    
    @FXML
    private CheckBox cbPrint;
    
    public void setEvents(){
    	setButtonEvents();
    	setRadioButtonEvents();
    	setCheckBoxEvents();
    }
    
    public void setButtonEvents(){
        this.btOK.setOnAction((event) -> {        	
        	System.out.println("OK");
        });
        this.btCancel.setOnAction((event) -> {
        	System.out.println("Cancel");
        });
        this.btSetup.setOnAction((event) -> {
        	System.out.println("Setup...");
        });
        this.btHelp.setOnAction((event) -> {
        	System.out.println("Help");
        });        
    }
    
    public void setRadioButtonEvents(){
    	this.rbtSelection.setOnAction((event) -> {
    		System.out.println("Selection");
    	});
    	this.rbtAll.setOnAction((event) -> {
    		System.out.println("All");
    	});
    	this.rbtApplet.setOnAction((event) -> {
    		System.out.println("Applet");
    	});
    }
    
    public void setCheckBoxEvents(){
    	this.cbImage.setOnAction((event) -> {
    		System.out.println("Image");
    	});
    	this.cbText.setOnAction((event) -> {
    		System.out.println("Text");
    	});
    	this.cbCode.setOnAction((event) -> {
    		System.out.println("Code");
    	});
    	this.cbPrint.setOnAction((event) -> {
    		System.out.println("Print to File");
    	});
    }
    
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        assert btOK     != null : "fx:id=\"btOK\" was not injected: check your FXML file 'WebBuyer.fxml'.";
        assert btCancel != null : "fx:id=\"btCancel\" was not injected: check your FXML file 'WebBuyer.fxml'.";
        assert btSetup  != null : "fx:id=\"btSetup\" was not injected: check your FXML file 'WebBuyer.fxml'.";
        assert btHelp   != null : "fx:id=\"btHelp\" was not injected: check your FXML file 'WebBuyer.fxml'.";

        assert rbtSelection != null : "fx:id=\"rbtSelected\" was not injected: check your FXML file 'WebBuyer.fxml'.";
        assert rbtApplet != null : "fx:id=\"rbtApplet\" was not injected: check your FXML file 'WebBuyer.fxml'.";
        assert rbtAll != null : "fx:id=\"rbtAll\" was not injected: check your FXML file 'WebBuyer.fxml'.";
        
        setEvents();
        
	}
}