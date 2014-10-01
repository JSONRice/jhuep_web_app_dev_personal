package hw4_part2.main;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Package {
	
	private Double weightOunces;
	private char shipMethod;
	protected Double shipCost;

	private Map<Integer, Double> airPricing;
	private Map<Integer, Double> truckPricing;
	private Map<Integer, Double> mailPricing;
	private Double OUNCE_DIFF = 0.062500;
	private Integer MAX_WEIGHT = 17; // lbs

	public Double getWeightOunces(){
		return weightOunces;
	}

	public char getShipMethod(){
		return shipMethod;
	}
	
	public Double getShipCost(){
		return shipCost;
	}
	
	/***
	 * @desc Shipping based on the following:
	 * Weights (lbs)    Air    Truck   Mail
     * 1 to 8           2.00   1.50    0.50
     * 9 to 16          3.00   2.35    1.50
     * 17 and over      4.50   3.25    2.15
     * 
	 * @param weightOunces
	 * @param shipMethod
	 */
	public Package(Double weightOunces, char shipMethod){
		
		airPricing = new TreeMap<Integer, Double>();
		airPricing.put(new Integer(8),  new Double(2.0));
		airPricing.put(new Integer(9),  new Double(3.0));
		airPricing.put(new Integer(17), new Double(4.50));
		
		truckPricing = new TreeMap<Integer, Double>();
		truckPricing.put(new Integer(8), new Double(1.50));
		truckPricing.put(new Integer(9), new Double(2.35));
		truckPricing.put(new Integer(17), new Double(3.25));
		
		mailPricing = new TreeMap<Integer, Double>();
		mailPricing.put(new Integer(8), new Double(0.50));
		mailPricing.put(new Integer(9), new Double(1.50));
		mailPricing.put(new Integer(17), new Double(2.15));
		
		this.shipCost = calculateCost(weightOunces, shipMethod);
	}
	
	private Double calculateCost(Double weightOunces, char shipMethod){

		if (weightOunces < 0){
			weightOunces = 0D;
		}
		
		int lbs = ouncesToPounds(weightOunces);
		
		// See if the round from ounces to pounds rounds down to zero.
		int round = 0;
		if (lbs == 0) { 
			round++;
		}
		
		switch(shipMethod)
		{
			case 'A':
				return calculateCost(lbs + round, airPricing);
			case 'T':
				return calculateCost(lbs + round, truckPricing);
			case 'M':
				return calculateCost(lbs + round, mailPricing);
			default:
				return 0D;
		}
	}
	
	private Double calculateCost(int lbs, Map<Integer, Double> methodMap){
		
		for(Map.Entry<Integer, Double> entry : methodMap.entrySet()){
			if (lbs >= MAX_WEIGHT){
				return methodMap.get(MAX_WEIGHT);
			}
			else if (lbs <= entry.getKey()){
				return entry.getValue();
			}				
		}
		return 0D; // should never occur
	}

	public int ouncesToPounds(Double ounces){
		return (int) (ounces * OUNCE_DIFF);
	}

}
