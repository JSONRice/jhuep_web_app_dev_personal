package hw4_part1.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;

public class Driver {
	
	private BufferedReader br;
	private RentalRoom rentalRoom;
	
	public Driver(){
		br = new BufferedReader(new InputStreamReader(System.in));
		rentalRoom = null;
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
	
	public final String getRoomType(){
		final String roomTypeMsg = "\nEnter a room type code ('q' to quit) based on the following options: \n"
				+ "'s' - Standard room at the rate of $109.95 per night\n"
				+ "'u' - Upgraded room above room number 199 at the rate of $169.95 per night\n"
				+ "> ";
		
		String roomType = new String("");
		do {
			roomType = getInput(roomTypeMsg);
		} while ((roomType.equals("s") || roomType.equals("u")) != true);
		return roomType;
	}
	
	public final boolean getSuiteOption(){
		final String suiteMsg = "\nType 'y' for suite upgrade 'n' for no upgrade.\n This implies a $40 surcharge to nightly rate: ";
		String suite = new String("");
		do {
			suite = getInput(suiteMsg);
		} while ((suite.equals("y") || suite.equals("n")) != true);
		return (suite.equals("y")) ? true : false;
	}

	public final int getNumNights(){
		final String numNightsMsg = "\nEnter number of nights staying: ";
		String numNightsStr = new String("");
		int numNights = 0;
		do { // input check
			numNightsStr = getInput(numNightsMsg);
			try {
				numNights = Integer.parseInt(numNightsStr);
				return numNights; // input is valid - break out
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR: Input is not a numeric sequence: '" + numNightsStr + "'");
			}
		} while (true);	
	}
	
	public void driver() {
		System.out.println("*** Welcome to Ria's Bed and Breakfast Booking Application ***");
		String roomType = getRoomType();
		boolean suiteOption = getSuiteOption();
		int numNights = getNumNights();
		rentalRoom = new RentalRoom(roomType, suiteOption, numNights);
		
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		// At this point the rental has been set. Output the room rental type, rate, number of nights, number, and total.
		String outMsg = (suiteOption) ? "\n*** Renting out a suite " : "Renting out a room ";
		outMsg += "at a rate of $" + rentalRoom.getRate() + " for " 
				+ numNights + " nights.\nRoom number is: " + rentalRoom.getRoomNumber() 
				+ " with a total cost of: " + formatter.format(rentalRoom.getTotal()) + " ***\n";
				
		System.out.println(outMsg);
	}
	
	public static void main(String[] args){
		Driver d = new Driver();
		d.driver();
	}
}
