package test;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import main.CreditCardValidator;

import org.junit.Assert;
import org.junit.Test;

public class CreditCardValidatorTest extends TestCase {
	
	public static long[] goodinput = {1234567890123L, 1234567890123456L, 456492084841234L};
	public static long[] badinput  = {12L, 9223372036854775807L, -14L};
	
	@Test
	public void testIsValid() {
		for (long bad : badinput){
			Assert.assertFalse( "The following is a bad value that tested good: " + bad, CreditCardValidator.isValid(bad));			
		}
	}
	
	@Test
	public void testGetPrefix() {
		// good input
		assertEquals(12345L, CreditCardValidator.getPrefix(goodinput[0], 5));
		assertEquals(1234567890123L, CreditCardValidator.getPrefix(goodinput[0], 13));
		
		// bad input
		assertEquals(0L, CreditCardValidator.getPrefix(badinput[2], 13));
		
	}

}
