package hw7.main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Amortization extends Application implements Initializable {
	
	private boolean validInput;

	@FXML
	private Stage primaryStage;

	@FXML
	private TextField input;
	private String inputStr;
	
	@FXML
	private ChoiceBox interest;
	private Float interestFloat;
	private List interestOptions;

	@FXML
	private TableView amortization;

	@FXML
	private TextField totalpaymentoutput;

	@FXML
	private Button calculate;

	@FXML
	private Text errormsg;

	public void setInvalidInput(final String msg){
		input.setStyle("-fx-background-color:red");
		input.setText(msg);		
		validInput = false;
	}
	
	public void setEvents() {
		
		interest.setTooltip(new Tooltip("Set the amortization interest rate."));
		interest.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				interestFloat = (Float) interestOptions.get(newValue.intValue());				
			}
			
		});
	
		input.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean oldPropertyValue, Boolean newPropertyValue) {
				if (newPropertyValue) { // ON-FOCUS
					input.setStyle("-fx-background-color:white");
					input.setText("");
					validInput = true; // innocent until proven guilty
				} 
				else { // OFF-FOCUS
					inputStr = input.getText();
					if (inputStr != null && !inputStr.trim().isEmpty()) {
					    try {
					         if (!(Integer.parseInt(inputStr) > 0)) {
					        	 setInvalidInput("invalid amount");
					         }
					    }
					    catch( Exception e ){
							setInvalidInput("invalid amount");
							return;
					    }
					} 
					else { // no value entered
						setInvalidInput("enter a amount");
					}
				}
			}
		});

		calculate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!validInput){
					return;
				}
				inputStr = input.getText(); // at this point we know input is a whole number					
				System.out.println(inputStr + " " + interestFloat);
			}
			
		});

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		Application.launch(Amortization.class, (java.lang.String[]) null);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			AnchorPane page = FXMLLoader.load(Amortization.class
					.getResource("Amortization.fxml"));
			page.getStylesheets().add("/hw7/main/Amortization.css");
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Mortgage Amortization Calculator");
			primaryStage.show();
		} 
		catch (Exception ex) {
			Logger.getLogger(Amortization.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assert input != null : "fx:id=\"input\" was not injected: check your FXML file.";
		assert amortization != null : "fx:id=\"amortization\" was not injected: check your FXML file.";
		assert calculate != null : "fx:id=\"calculate\" was not injected: check your FXML file.";
		setEvents();
		errormsg = new Text();
		interestOptions = interest.getItems();
		interestFloat = 3.50F;
	}
}