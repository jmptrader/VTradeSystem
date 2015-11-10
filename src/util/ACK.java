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
    double AvgPx; 
    
    /**
     * Total number of shares filled.
     */
    int CumQty; 
    
    /**
     * Unique identifier of Execution Report (8) message as assigned by broker. 
     * Every execution report has a unique ExecID, 
     */
    int ExecID; 
    
    /**
     * Identifies transaction type, 0 for new.
     */
    int ExecTranType; 
    
    /**
     * Market of execution for last fill. 
     * Since we are using test exchange, so the value is Test.
     */
    String LastMk; 
    
    /**
     *  Price of this (last) fill. 0 for ack.
     */
    double LastPx;
    
    /**
     *  Quantity of shares bought/sold on this (last) fill. 0 for ack.
     */
    int LastShares;
    
    /**
     *  Unique identifier for Order as assigned by broker.  
     *  can be the same as tag 11 ClOrdID
     */
    int OrderID;
    
    /**
     * Identifies current status of order. 0 for ack.
     */
    int OrdStatus;
    
    /**
     * Describes the specific Execution Report (8) 
     * (i.e. Pending Cancel) while OrdStatus (39) will always identify 
     * the current order status. 0 for new.
     */
    int ExecType;
        
    /**
     * Amount of shares open for further execution. Value= tag 38 OrdQty.
     */
    int LeavesQty;
    
    /**
     * General constructor for ACK class.
     * @param order
     */
    protected ACK(Order order){
        // common fields for all ACK
        super();
        this.MsgType = "8"; 
        this.LastMk = "Test"; 
        this.ExecTranType = 0;
    }
    
    /**
     * Default class for ACK message.
     * @param order
     * @param sendingTime
     * @param ExecID
     */
    
    public ACK (Order order, String sendingTime, int ExecID){
        this(order); // call default constructor.
        this.SendingTime = sendingTime;
        this.ExecID = ExecID;
        this.OrderID = order.getClOrdID();
        this.AvgPx = 0.00f;
        this.CumQty = 0;
        this.LastPx = 0.00f;
        this.LastShares = 0;
        this.OrdStatus = 0;
        this.ExecType = 0;
        this.LeavesQty = 0;
    }
    

    public double getAvgPx() {
        return AvgPx;
    }

    public void setAvgPx(double AvgPx) {
        this.AvgPx = AvgPx;
    }

    public int getCumQty() {
        return CumQty;
    }

    public void setCumQty(int CumQty) {
        this.CumQty = CumQty;
    }

    public int getExecID() {
        return ExecID;
    }

    public void setExecID(int ExecID) {
        this.ExecID = ExecID;
    }

    public int getExecTranType() {
        return ExecTranType;
    }

    public void setExecTranType(int ExecTranType) {
        this.ExecTranType = ExecTranType;
    }

    public String getLastMk() {
        return LastMk;
    }

    public void setLastMk(String LastMk) {
        this.LastMk = LastMk;
    }

    public double getLastPx() {
        return LastPx;
    }

    public void setLastPx(double LastPx) {
        this.LastPx = LastPx;
    }

    public int getLastShares() {
        return LastShares;
    }

    public void setLastShares(int LastShares) {
        this.LastShares = LastShares;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int OrderID) {
        this.OrderID = OrderID;
    }

    public int getOrdStatus() {
        return OrdStatus;
    }

    public void setOrdStatus(int OrdStatus) {
        this.OrdStatus = OrdStatus;
    }

    public int getExecType() {
        return ExecType;
    }

    public void setExecType(int ExecType) {
        this.ExecType = ExecType;
    }

    public int getLeavesQty() {
        return LeavesQty;
    }

    public void setLeavesQty(int LeavesQty) {
        this.LeavesQty = LeavesQty;
    } 
    
}
