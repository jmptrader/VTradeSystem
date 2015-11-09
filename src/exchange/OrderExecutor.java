/**
 * 
 */
package exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author pennlio
 *
 */
public class OrderExecutor {
	
	private static int transCounter = 0;
	// current order to process
	private List<ExeInfo> outputOrder = null;
	private static int RANDOM_NUMBER_BOUND = 100;
	private static int FULL_FILL_THRESHOLD = 50;
	
//	public Exchange(){
//		
//	}
	
	/**
	 * Generate excution price.
	 * @param order
	 * @return
	 */
//	private float exPriceGenerator(Order order){
	public float exPriceGenerator(float maxPrice, float minPrice){
		// return according to order type
		Random rand = new Random();
		float exPrice = (maxPrice - minPrice) * rand.nextFloat() + minPrice;
		return exPrice;
	}
	
	// reutrn according to order type
	private List<Integer> exAmountGenerator(Order order){
		List<Integer> exAmountList = new ArrayList(); 
		int leaveQty = order.OrderQty;
		boolean isSecondExecution = false;
		if (order.OrdType.equals("1")){  
			//for market order, full fill order amount at one execution.
			exAmountList.add(order.OrderQty);
		}
		else{							
			//for limited and pegged order,  full fill order amount up to 2 executions.
			Random rand = new Random();
			while (leaveQty > 0){
				if (isSecondExecution){
					exAmountList.add(leaveQty); // 
					break;
				}
				else{
					int threshold = rand.nextInt(RANDOM_NUMBER_BOUND);
					if(threshold > FULL_FILL_THRESHOLD){
						exAmountList.add(OrderQty); // full fill at once
						break;
					}
					else{
						int firstExeAmount = rand.nextInt(order.OrderQty-1); // firstExeAmount < OrderQty
						exAmountList.add(firstExeAmount);
						leaveQty -= firstExeAmount;
						isSecondExecution = true;
					}
				}
			}			
		}
		return exAmountList;
	}
	
	public void processOrder(String orderString){
		// parse ordering string
		Order currentOrder = Parser.parse(orderString); //todo
		int leftAmount  = currentOrder.OrderQty; // todo
		// fill order until none left
		
		float maxPrice = 100.00f;
		float minPrice = 5.00f;
		
		while (leftAmount > 0){ 
			float exePrice = this.exPriceGenerator(maxPrice, minPrice);
			int exAmount = this.exAmountGenerator(currentOrder, isPartialFill);
			ExeInfo excutedOrderInfo = new ExeInfo(...); // todo
			leftAmount -= exAmount;
			if (leftAmount > 0){
				isPartialFill = 1;
			}
		}
		return;
	}
	
	
}
