package hw4_part2.test;

import junit.framework.TestCase;
import hw4_part2.main.InsurancePackage;
import hw4_part2.main.Package;
import hw4_part2.main.Driver;
import org.junit.Assert;
import org.junit.Test;


public class DriverTest extends TestCase {
    /**********************************************
     * Base Shipping Rates:\n"
     * Weights (lbs)    Air    Truck   Mail\n"
     * 1 to 8           2.00   1.50    0.50\n"
	 * 9 to 16          3.00   2.35    1.50\n"
     * 17 and over      4.50   3.25    2.15\n"
	 **********************************************\n"
     * Shipping Cost Insurance Comparison:\n"
     * Before Insurance ($)     Additional Cost ($)\n"
     * 0 to 1.00                2.45\n"
     * 1.01 to 3.00             3.95\n"
     * 3.01 and over            5.55\n"
     **********************************************/
	
	@Test
	public void testCalculateCost() {
	
		// Packages without insurance.
		Package aPackage = new Package(23D, 'A');		
		assertEquals(2.0D, aPackage.getShipCost());
		
		Package bPackage = new Package(12D, 'T');
		assertEquals(1.5D, bPackage.getShipCost());

		Package cPackage = new Package(600D, 'M');
		assertEquals(2.15D, cPackage.getShipCost());

		Package dPackage = new Package(280D, 'A');
		assertEquals(4.50D, dPackage.getShipCost());

		
		// Insurance based packages.
		InsurancePackage ePackage = new InsurancePackage(23D, 'A');
		assertEquals(5.95D, ePackage.getShipCost());

		InsurancePackage fPackage = new InsurancePackage(12D, 'T');
		assertEquals(5.45D, fPackage.getShipCost());

		InsurancePackage gPackage = new InsurancePackage(600D, 'M');
		assertEquals(6.10D, gPackage.getShipCost());

		InsurancePackage hPackage = new InsurancePackage(256D, 'A');
		assertEquals(10.05D, hPackage.getShipCost());
	}
}
