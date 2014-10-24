package hw7.main;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Amortization extends Application implements Initializable, 
	AmortizationAlgorithm {
			
	private boolean validInput;

	@FXML
	private Stage primaryStage;
	
	@FXML
	private TextField input;
	private String inputStr;
	
	@FXML
	private ChoiceBox<Float> interest;
	private Float interestFloat;
	private List interestOptions;
	
	@FXML
	private ChoiceBox<Integer> years;
	private Integer yearsInteger;
	private List yearsOptions;

	@FXML
	private TableView<AmortizationTuple> amortization;
	
	@FXML
	private TextField totalpaymentoutput;
	private String amortValue;

	@FXML
	private Button calculate;

	@FXML
	private Text errormsg;
	
	private Double monthlyPayment;
	
	private Double monthlyInterestRate;

	public void setInvalidInput(final String msg){
		input.setStyle("-fx-background-color:red");
		input.setText(msg);		
		validInput = false;
	}
	
	
	public double computeMonthlyInterestRate(Float yearlyInterestRate){
		return (double) yearlyInterestRate/12/100;
	}
	
	@Override
	public double computeMonthlyPayment(Integer loanAmount, Double monthlyInterestRate,
			Integer numOfYears) {
		return ((loanAmount * monthlyInterestRate) / 
				(1 - (Math.pow(1 / (1 + monthlyInterestRate), numOfYears * 12))));
	}
	
	/**
	 * @author jsnrice
	 * @desc Private inner class to represent the tuple rows of the amortization table.
	 * These object instances are utilized when the amortization gets calculated and hence
	 * the table must be updated to reflect the amortization calculations.
	 */
	public class AmortizationTuple{
		private  final SimpleStringProperty monthNum = new SimpleStringProperty("");
        private  final SimpleStringProperty payment = new SimpleStringProperty(""); // monthly payment
        private  final SimpleStringProperty princPaid = new SimpleStringProperty("");
        private  final SimpleStringProperty intPaid = new SimpleStringProperty("");
        private  final SimpleStringProperty totIntPaid = new SimpleStringProperty("");
        private  final SimpleStringProperty remBal = new SimpleStringProperty("");
 
        private AmortizationTuple(String monthNum, String payment, 
        		String princPaid, String intPaid, 
        		String totIntPaid, String remBal) {
        	setMonthNum(monthNum);
        	setPayment(payment);
        	setPrincPaid(princPaid);
        	setIntPaid(intPaid);
        	setTotIntPaid(totIntPaid);
        	setRemBal(remBal);
        }
        
        public String getMonthNum() {
 			return monthNum.get();
 		}

 		public void setMonthNum(String monthNum) {
 			this.monthNum.set(monthNum);
 		}

 		public String getPayment() {
 			return payment.get();
 		}

 		public void setPayment(String payment) {
 			this.payment.set(payment);
 		}

 		public String getPrincPaid() {
 			return princPaid.get();
 		}

 		public void setPrincPaid(String princPaid) {
 			this.princPaid.set(princPaid);
 		}

 		public String getIntPaid() {
 			return intPaid.get();
 		}

 		public void setIntPaid(String intPaid) {
 			this.intPaid.set(intPaid);
 		}

 		public String getTotIntPaid() {
 			return totIntPaid.get();
 		}

 		public void setTotIntPaid(String totIntPaid) {
 			this.totIntPaid.set(totIntPaid);
 		}

 		public String getRemBal() {
 			return remBal.get();
 		}

 		public void setRemBal(String remBal) {
 			this.remBal.set(remBal);
 		}
	}
	
	public final String updateAmortizationTable(Integer years){
        amortization.setEditable(true);
        
        ObservableList<AmortizationTuple> data = amortization.getItems();
        data.removeAll(data);
        
        ArrayList<AmortizationTuple> amortizationRows = new ArrayList<AmortizationTuple>();
        
        // Compute the pay out fields and then build the table.
        Integer months = years * 12;
        Double interestPaid = 0D;
        Double principle = (double) Integer.parseInt(inputStr);
        Double principlePaid = 0D;
        Double newBalance = 0D; // temporary placeholder
        Double totalInterestPaid = 0D;
        Double totalPayment = 0D;
        
        // amortize algorithm:
        for (int i = 1;i < (months + 1); i++){
        	
        	interestPaid =  principle * monthlyInterestRate;
        	totalInterestPaid += interestPaid;
        	principlePaid = monthlyPayment - interestPaid;
        	newBalance = principle - principlePaid; // set new balance
        	principle = newBalance; // remaining balance        	

        	amortizationRows.add(new AmortizationTuple("" + i, 
        			"$" + roundOffTenths(monthlyPayment), "$" + roundOffTenths(principlePaid), 
        			"$" + roundOffTenths(interestPaid), "$" + roundOffTenths(totalInterestPaid), 
        			"$" + roundOffTenths(principle)));
        	
        	totalPayment += principlePaid;
        }
        
        totalPayment += totalInterestPaid;

        data = amortization.getItems();

        for (AmortizationTuple at : amortizationRows) {
        	data.add(at);
        }

        return "$" + roundOffTenths(totalPayment);
	}
	
	public String roundOffTenths(Double d){
		if (d <= 0) {
			return "0.00";
		}
		Double value = (double) (((int) ((d * 100.0) + 0.5)) / 100.0);
		return String.format("%.2f", value );
	}
	
	/**
	 * @desc Amortize based on the provided inputs.
	 * 
	 * @param loanamount
	 * @param interest
	 * @param years
	 * @return The total payment amount including interest represented as a string.
	 */
	public final String amortize(Integer loanamount, Float interest, Integer years){
		if (inputStr == null || inputStr.isEmpty()) {
		   return "";
		}
		this.monthlyInterestRate = computeMonthlyInterestRate(interest);
 	    this.monthlyPayment = computeMonthlyPayment(loanamount, monthlyInterestRate,years);
 	   
 	    return updateAmortizationTable(years);
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
		
		years.setTooltip(new Tooltip("Set the number of years to payoff loan."));
		years.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable,
					Number oldValue, Number newValue) {
				yearsInteger =  (Integer) yearsOptions.get(newValue.intValue());				
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
		
		totalpaymentoutput.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e){
				if (!validInput){
					return;
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
				// NOTICE -- the inputStr has already been checked in the input default listener and is a whole number.
				String total = amortize(Integer.parseInt(inputStr),interest.getValue(),years.getValue());
				totalpaymentoutput.setText(total);
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
		assert input              != null : "fx:id=\"input\" was not injected: check your FXML file.";
		assert amortization       != null : "fx:id=\"amortization\" was not injected: check your FXML file.";
		assert calculate          != null : "fx:id=\"calculate\" was not injected: check your FXML file.";
		assert totalpaymentoutput != null : "fx:id=\"totalpaymentoutput\" was not injected: check your FXML file.";

		setEvents();
		
		errormsg = new Text();
		interestOptions = interest.getItems();
		yearsOptions = years.getItems();
		interest.setValue(new Float(3.25));
		years.setValue(new Integer(30));
		amortization.setPlaceholder(new Label(""));
		interestFloat = 3.25F;
		yearsInteger = 30;
	}
}