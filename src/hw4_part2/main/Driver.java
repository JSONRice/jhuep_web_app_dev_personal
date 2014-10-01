package hw4_part2.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

public class Driver {
	
	Package userPackage;
	
	private final String SHIPPING = 
			                        "**********************************************\n"
								  + "* Base Shipping Rates:\n"
			                      + "* Weights (lbs)    Air    Truck   Mail\n"
			                      + "* 1 to 8           2.00   1.50    0.50\n"
		                     	  + "* 9 to 16          3.00   2.35    1.50\n"
	                    		  + "* 17 and over      4.50   3.25    2.15\n"
		                     	  + "**********************************************\n"
	                    		  + "* Shipping Cost Insurance Comparison:\n"
	                              + "* Before Insurance ($)     Additional Cost ($)\n"
	                              + "* 0 to 1.00                2.45\n"
	                              + "* 1.01 to 3.00             3.95\n"
	                              + "* 3.01 and over            5.55\n"
	                              + "**********************************************\n";
	
	private BufferedReader br;

	public Driver(){
		br = new BufferedReader(new InputStreamReader(System.in));
		userPackage = null;
	}
	
	private void display(){
		System.out.println(SHIPPING);
	}
	
	
	public String getInput(final String message){
		System.out.print(message);
		String input;
		do { 
		   try {
			   input = br.readLine().trim();
			   // Ensure input is valid and user wishes to continue:
			   if (input.isEmpty()) {
				   System.out.println("An empty input value was found. Please retry.");
			   }		
			   else if (input.toLowerCase().equals("q")){
				   System.out.println("bye");
				   System.exit(0);
			   }
		       return input;
		   } catch (IOException e) {
			   System.out.print("Error: the provided input is not valid. Try again.");
		   }
		} while (true); // loop until input is valid
	}
	
	public final Double getOunces(){
		final String ouncesMsg = "Enter package weight in ounces ('q' to quit): ";
		String ouncesStr = new String("");
		Double ounces = 0D;
		do {
			ouncesStr = getInput(ouncesMsg);
			try {
				ounces = Double.parseDouble(ouncesStr);
				return ounces; // input is valid - break out
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR: Input is not a numeric sequence: '" + ouncesStr + "'");
			}
		} while (true);	
	}
	
	public final char getShipService(){
		final String shipMsg = "\nEnter shipping service as 'A' airplane, 'T' truck, or 'M' for mail ('q' to quit): ";
		char shipMethod = '\0';
		do {
			shipMethod = getInput(shipMsg).toUpperCase().charAt(0);
			if (shipMethod == 'A' || shipMethod == 'T' || shipMethod == 'M') {
				return shipMethod;
			}
			// else
			System.out.println("ERROR: Input is not valid.");
		} while (true);	
	}
	
	public final boolean getInsuranceOption(){
		final String msg = "\nType 'y' for insurance upgrade 'n' for no insurance ('q' to quit): ";
		String insurance = new String("");
		do {
			insurance = getInput(msg).toLowerCase();
		} while ((insurance.equals("y") || insurance.equals("n")) != true);
		return (insurance.equals("y")) ? true : false;
	}
	
	
	public void driver(){
		System.out.println("*** Welcome to Johnson's Packaging Application ***");
		System.out.println("\nShipping prices are as follows: ");
		display();
		
		/** 
		 * Prompt the user for the weight of the package, and type of shipping service, 
		 * insurance needed if any etc..and display the final price to test the application.
		 */
		Double ounces = getOunces();
		char shipMethod = getShipService();
		boolean insurance = getInsuranceOption();
		
		/** 
		 * At this point the price has been set. Output the weight of package, 
		 * type of shipping service, insurance if any, and the final price of shipping.
		 */
		userPackage = (insurance) ? new InsurancePackage(ounces, shipMethod) : new Package(ounces, shipMethod);
		
		String insuranceStr = (insurance) ? "with" : "without";
		insuranceStr += " insurance";
		
		String outMsg = "Package weighing " + userPackage.ouncesToPounds(ounces) + "lbs (or " + ounces + "oz) being shipped by ";
		switch (shipMethod){
			case 'A':
				outMsg += "air ";
				break;
			case 'T':
				outMsg += "truck ";
				break;
			case 'M':
			default:
				outMsg += "mail ";
		}
		
		outMsg += insuranceStr + ". Final price is: " + userPackage.getShipCost();
		
		System.out.println("\n*************************\n" + outMsg + "\n*************************\n");
	}
	
	public static void main(String[] args) {
		Driver d = new Driver();
		d.driver();
	}

}
