package hw7.main;

/**
 * @author jsnrice
 * @desc Interface containining the algorithm standard for computing amortization.
 * 
 * Amortization Algorithm:
 * 
 * double monthlyPayment = loanAmount*monthlyInterestRate /
 * (1 - (Math.pow(1 / (1 + monthlyInterestRate), numOfYears * 12)));
 * 
 * Keep in mind the monthlyInterestRate must be computed separately.
 * If an interest rate is provided as a whole number (e.g. 3.25) it must
 * be decomposed to 0.0325 by dividing the original of 3.25 by 100.
 * 
 * To clarify the amortization algorithm consider the following Java example:
 * 
 *   double principle = 450000;
 *   double yearlyinterest = 3.25;
 *   double years = 30;
 *   double monthlyinterest = yearlyinterest/100/12;
 *    
 *   double test = (principle*monthlyinterest)/(1-((Math.pow(1/(1+monthlyinterest), 360))));
 *   System.out.println("test is: " + test);
 *
 * This would yield:
 * 
 * 1958.4284358257075
 * 
 * When rounding up to the tens decimal place this would result in the final monthly payment:
 * 
 * 1958.43
 * 
 */
public interface AmortizationAlgorithm {
	
	/**
	 * @desc
	 * (yearlyInterestRate/100)/12
	 * 
	 * Note: The yearly interest rate must not be normalized. For example 3.25% must be
	 * 3.25 and not 0.0325
	 * 
	 * @return monthlyInterestRate
	 */
	public double computeMonthlyInterestRate(Float yearlyInterestRate);
	
	/**
	 * @desc Based off of the monthly interest rate. Computes the monthly 
	 * payments to reach amortization goal.
	 * 
	 * @param loanAmount
	 * @param monthlyInterestRate
	 * @param numOfYears
	 * @return
	 */
	public double computeMonthlyPayment(
			Integer loanAmount, Double monthlyInterestRate, 
			Integer numOfYears);	
}
