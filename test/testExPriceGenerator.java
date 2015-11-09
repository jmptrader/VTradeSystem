import junit.framework.Assert;

import org.junit.Test;

import exchange.OrderExecutor;

public class testExPriceGenerator {

	static OrderExecutor exe = new OrderExecutor();  
	
	@SuppressWarnings("deprecation")
	@Test
	public void testPrice(){
		float exPrice = exe.exPriceGenerator(20.00f, 5.00f);
		Assert.assertNotNull(exPrice);
		Assert.assertTrue(exPrice > 5);
		Assert.assertTrue(exPrice <= 20);
	}
}
