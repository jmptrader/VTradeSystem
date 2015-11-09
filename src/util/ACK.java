package util;

/**
 * Class for ACK message. 
 * Both the ExeReport and ACK are inherited from Order class.
 * @author pennlio
 *
 */
public class ACK extends Order{

	/**
	 * Calculated average price of all fills on this order, 0 for ack.
	 */
	double avgPx; 
	
	/**
	 * Total number of shares filled.
	 */
	int cumQty; 
	
	/**
	 * Unique identifier of Execution Report (8) message as assigned by broker. 
	 * Every execution report has a unique ExecID, 
	 */
	int execID; 
	
	/**
	 * Identifies transaction type, 0 for new.
	 */
	int execTransType; 
	
	/**
	 * Market of execution for last fill. 
	 * Since we are using test exchange, so the value is Test.
	 */
	String lastMk; 
	
	/**
	 *  Price of this (last) fill. 0 for ack.
	 */
	double lastPx;
	
	/**
	 *  Quantity of shares bought/sold on this (last) fill. 0 for ack.
	 */
	int lastShares;
	
	/**
	 *  Unique identifier for Order as assigned by broker.  
	 *  can be the same as tag 11 ClOrdID
	 */
	int orderID;
	
	/**
	 * Identifies current status of order. 0 for ack.
	 */
	int ordStatus;
	
	/**
	 * Describes the specific Execution Report (8) 
	 * (i.e. Pending Cancel) while OrdStatus (39) will always identify 
	 * the current order status. 0 for new.
	 */
	int execType;
		
	/**
	 * Amount of shares open for further execution. Value= tag 38 OrdQty.
	 */
	int leavesQty;
	
	/**
	 * General constructor for ACK class.
	 * @param order
	 */
	protected ACK(Order order){
		// common fields for all ACK
		super();
		this.MsgType = "8"; 
		this.lastMk = "Test"; 
		this.execTransType = 0;
	}
	
	/**
	 * Default class for ACK message.
	 * @param order
	 * @param sendingTime
	 * @param execID
	 */
	
	public ACK (Order order, String sendingTime, int execID){
		this(order); // call default constructor.
		this.SendingTime = sendingTime;
		this.execID = execID;
		this.orderID = order.getClOrdID();
		this.avgPx = 0.00f;
		this.cumQty = 0;
		this.lastPx = 0.00f;
		this.lastShares = 0;
		this.ordStatus = 0;
		this.execType = 0;
		this.leavesQty = 0;
	}
	

	public double getAvgPx() {
		return avgPx;
	}

	public void setAvgPx(double avgPx) {
		this.avgPx = avgPx;
	}

	public int getCumQty() {
		return cumQty;
	}

	public void setCumQty(int cumQty) {
		this.cumQty = cumQty;
	}

	public int getExecID() {
		return execID;
	}

	public void setExecID(int execID) {
		this.execID = execID;
	}

	public int getExecTransType() {
		return execTransType;
	}

	public void setExecTransType(int execTransType) {
		this.execTransType = execTransType;
	}

	public String getLastMk() {
		return lastMk;
	}

	public void setLastMk(String lastMk) {
		this.lastMk = lastMk;
	}

	public double getLastPx() {
		return lastPx;
	}

	public void setLastPx(double lastPx) {
		this.lastPx = lastPx;
	}

	public int getLastShares() {
		return lastShares;
	}

	public void setLastShares(int lastShares) {
		this.lastShares = lastShares;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getOrdStatus() {
		return ordStatus;
	}

	public void setOrdStatus(int ordStatus) {
		this.ordStatus = ordStatus;
	}

	public int getExecType() {
		return execType;
	}

	public void setExecType(int execType) {
		this.execType = execType;
	}

	public int getLeavesQty() {
		return leavesQty;
	}

	public void setLeavesQty(int leavesQty) {
		this.leavesQty = leavesQty;
	} 
	
}
