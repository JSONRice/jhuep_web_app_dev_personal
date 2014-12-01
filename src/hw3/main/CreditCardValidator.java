package hw3.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author jrice30
 *
 */
public class CreditCardValidator {
	
	private static final int[] VALID_CC = {4 /* visa */, 5 /*master*/, 37 /*discover*/, 6 /*american*/};
	
	/** Return true if the card number is valid */
	public static boolean isValid(long number){
       long finalSum = 0;
  	   // PRE-CONDITIONS: At this point it's safe to assume we received a 13-16 digit numeric value from the user.
       for (int encoding : VALID_CC){
          if (prefixMatched(number, encoding)){
    	      finalSum = sumOfDoubleEvenPlace(number) + sumOfOddPlace(number);				         	  
     		  return ((finalSum % 10) == 0) ? true : false; // Luhn's mod 10 check
          }
       }
       return false; // -- default
	}

	/** Return this number if it is a single digit, otherwise, return the sum of the two digits */
	public static int getDigit(int number){
		if (number < 0){
			return 0;
		}
		return (number < 10) ? number : ((number / 10) + (number % 10));
	}

	/** Get the result from Step 2 */
	public static int sumOfDoubleEvenPlace(long number) {
		int sum = 0;
		for (int i = 1; i <= 16; i++) {
			if (i % 2 == 0) /* only factor in even place digits */ {
				sum += getDigit((int) (number % 10) * 2); // the mod 10 enforces the range
			}
			number /= 10;
		}
		return sum;
	}

	
	/** Return sum of odd place digits in number */
	public static int sumOfOddPlace(long number){
		int sum = 0;
	    for (int i = 1; i <= 16; i++) {
	       if (i % 2 == 1){ /* only factor in odd place digits */
	          sum += (int)(number % 10);
	       }
	       number /= 10;
	    }
	    return sum;
	}

	/** Return true if the digit d is a prefix for number */
	public static boolean prefixMatched(long number, int d){
		return (d == getPrefix(number,(getSize(d)))) ? true : false;
	}


	/** Return the number of digits in d */
	public static int getSize(long d){
		if (d < 0){ // safety
			d = 0;
		}
		return (d == 0) ? 1 : (int) (Math.log10(d)+1);
	}

	/** Return the first k (prefix) number of digits from number. If the
	* number of digits in number is less than k, return number. */
	public static long getPrefix(long number, int k){
		// perform safety checks first
		if (k < 0){ 
			k = 0;
		}
		else if (number <= 0){
			return 0;
		}
		
		/***
		  The following grabs the first k letters from a string and return the substring set.
		  A Long is returned but then downcast to a long primitive.
		 ***/
		return Long.valueOf(String.valueOf(number).substring(0, k));
	}
	
	public static void driver(String input, Long number, BufferedReader br) throws IOException{
		System.out.print("Enter a cc number to validate ('q' to quit): ");
		input = br.readLine().trim();
		
		// Ensure input is valid and user wishes to continue:
		if (input.isEmpty()) {
			System.out.println("An empty input value was found. Please retry.");
		}		
		else if (input.toLowerCase().equals("q")){
			System.out.println("bye");
			return;
		}
		else if (input.length() < 13 || input.length() > 16) {
			System.out.println("Invalid input entered: '" + input + "'");
			System.out.println("Please enter a valid 13-16 digit cc number.");
		}
		else {
			try {
				number = Long.parseLong(input);
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR: Input is not a numeric sequence: '" + input + "'");
				driver(input = "", number = 0L, br);
			}
			// If all conditions pass continue:
			System.out.print("The cc number: '" + number + "' is ");
			if (isValid(number)){
				System.out.println("valid.\n");
			}
			else{
				System.out.println("invalid.\n");
			}
		}	
		driver(input = "", number = 0L, br);
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   String input = new String("");
	   Long number  = new Long(0);
	   CreditCardValidator.driver(input, number, br);
	}
}
