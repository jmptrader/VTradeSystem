/**
 * 
 */
package exchange;

import util.ACK;
import util.ExeReport;
import util.InfoExchange;
import util.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author pennlio
 *
 */
public class OrderExecutor {
	private int exeCounter = 0;
	private static Date exchangeDate = new Date();
	private static int RANDOM_NUMBER_BOUND = 100;
	private static int FULL_FILL_THRESHOLD = 50;
	private static double MAX_EXE_PRICE = 30.00f;
	private static double MIN_EXE_PRICE = 5.00f;
	private static OrderExecutor instance = null; //singleton
	
	protected OrderExecutor() {
	      // Exists only to defeat instantiation.
	}
	
	public static OrderExecutor getInstance(){
		if(instance == null){
			instance = new OrderExecutor();
		}
		return instance;
	}
	
//	private float exPriceGenerator(Order order){
	public double generateExePrice(double maxPrice, double minPrice){
		// return according to order type
		Random rand = new Random();
		double exePrice = (maxPrice - minPrice) * rand.nextFloat() + minPrice;
		return exePrice;
	}
	
	
	// reutrn according to order type
	public List<ExeReport> generateExeReport(Order order){
		List<ExeReport> exReportList = new ArrayList();
		List<Integer> exAmountList = new ArrayList();
		
		if (order.getOrderType().equals("1")){  // market order
			//for market order, full fill
			ExeReport exeReport = new ExeReport();
			double exeAvgPrice = this.generateExePrice(MAX_EXE_PRICE, MIN_EXE_PRICE);
			exeReport.setLastPx(exeAvgPrice);
			exeReport.setAvgPx(exeAvgPrice);
			exeReport.setCumQty(order.getOrderQty());
			exeReport.setLastShares(order.getOrderQty());
			exeReport.setOrdStatus(2);
			exeReport.setExecType(2);
			exeReport.setLeavesQty(0);
			// for ack part
			exeReport.setExecID(++exeCounter);
			exeReport.setOrderID(order.getClOrdID());
			exReportList.add(exeReport);
		}
		else{							
			//for limited and pegged order,  full fill order amount up to 2 executions.
			int leaveQty = order.getOrderQty();
			boolean isSecondExecution = false;
			Random rand = new Random();
			while (leaveQty > 0){
				if (isSecondExecution){
					exAmountList.add(leaveQty); // 
					break;
				}
				else{
					int threshold = rand.nextInt(RANDOM_NUMBER_BOUND);
					if(threshold > FULL_FILL_THRESHOLD){
						exAmountList.add(leaveQty); // full fill at once
						break;
					}
					else{
						int firstExeAmount = rand.nextInt(order.getOrderQty()-1); // firstExeAmount < OrderQty
						exAmountList.add(firstExeAmount);
						leaveQty -= firstExeAmount;
						isSecondExecution = true;
					}
				}
			}			
		}
		return exReportList;
	}
	
	public ACK generateACKMessage(Order currentOrder){
		ACK newAck = new ACK(exchangeDate.toString(), ++exeCounter, currentOrder.getClOrdID());
		return newAck;
	}
	
//	public void processOrder(String orderString){
//		// parse ordering string
//		InfoExchange parser = new InfoExchange();
//		Order currentOrder = parser.orderParser(orderString); //todo
//		List<>
//		
//
//		
//		while (leftAmount > 0){ 
//			float exePrice = this.exPriceGenerator(maxPrice, minPrice);
//			int exAmount = this.exAmountGenerator(currentOrder, isPartialFill);
//			ExeInfo excutedOrderInfo = new ExeInfo(...); // todo
//			leftAmount -= exAmount;
//			if (leftAmount > 0){
//				isPartialFill = 1;
//			}
//		}
//		return;
//	}
	
	
}
