package hw4_part2.main;

public class InsurancePackage extends Package {
	
	/**
	 * @desc
	 * Shipping Cost        Before Insurance ($)     Additional Cost ($)
     *                      0 to 1.00                2.45
     *                      1.01 to 3.00             3.95
     *                      3.01 and over            5.55
     *                      
	 * @param weightOunces
	 * @param shipMethod
	 */
	public InsurancePackage(Double weightOunces, char shipMethod){
		super(weightOunces, shipMethod);
		if (shipCost >= 3.01){
			shipCost += 5.55D;
		}
		else if (shipCost >= 1.01){
			shipCost += 3.95D;
		}
		else if (shipCost >= 0.0){
			shipCost += 2.45D;
		}
	}
}
