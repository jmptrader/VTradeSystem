/**
 * 
 */
package util;

import java.time.LocalTime;

/**
 * Class for Execution Report.
 * Both the ExeReport and ACK are inherited from Order class.
 * @author pennlio
 *
 */
public class ExeReport extends ACK {
	
	/**
	 *  Broker capacity in order execution, 1 for agent.
	 */
	int LastCapacity;
	
	/**
	 * Genernal constructor.
	 * @param order
	 * @param exeCounter
	 */
	protected ExeReport(){
		// fill the common fields
		super();
		
	}
	
	protected ExeReport(Order order, int exeCounter){
		// fill the common fields
		super(order, LocalTime.now().toString(), exeCounter);
		this.LastCapacity = 1; // for agent
		this.setOrderID(order.getClOrdID());
	}
	
	/**
	 * The constructor for full-fill execution report.
	 * @param order
	 * @param exeCounter
	 * @param exePrice
	 */
	public ExeReport(Order order, int exeCounter, double exePrice) {
		this(order, exeCounter);
		// for full fill exeReport
		this.setLastPx(exePrice); // same interface
		this.setAvgPx(exePrice); // change
		this.setLastShares(order.getOrderQty()); //same interface
		this.setCumQty(order.getOrderQty()); //change:
		this.setOrdStatus(2); //changed 1 for partial fill
		this.setExecType(2); // change
		this.setLeavesQty(0); // change
	}
	/**
	 * The constructor for partial order execution report.
	 * @param order
	 * @param exeCounter
	 * @param exePrice
	 * @param avgPrice
	 * @param exeQty
	 * @param cumQty
	 */
	public ExeReport(Order order, int exeCounter, double exePrice, double avgPrice, 
			int exeQty, int cumQty){
		this(order, exeCounter);
		// for partial fill
		this.setLastPx(exePrice); // same interface
		this.setAvgPx(avgPrice); // change
		this.setCumQty(cumQty); //change:
		this.setOrdStatus(1); //changed 1 for partial fill
		this.setLastShares(exeQty); //same interface
		this.setExecType(1); // change
		this.setLeavesQty(order.getOrderQty()-cumQty); // change	
	}
}
