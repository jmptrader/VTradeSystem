package util;



public class Order {

	int SenderCompID;
	String SendingTime;
	double Price;
	int Side;
	String Symbol;
	String OrderType;
	int OrderQty;
	String TimeInForce;
	String TransacTime;
	String MaturityMonthYear;
	String MaturityDay;
	String MsgType;
	int ClOrdID;
	String ExecInst;
	
	public int getSenderCompID() {
		return SenderCompID;
	}
	public void setSenderCompID(int senderCompID) {
		SenderCompID = senderCompID;
	}
	public String getSendingTime() {
		return SendingTime;
	}
	public void setSendingTime(String sendingTime) {
		SendingTime = sendingTime;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public int getSide() {
		return Side;
	}
	public void setSide(int side) {
		Side = side;
	}
	public String getSymbol() {
		return Symbol;
	}
	public void setSymbol(String symbol) {
		Symbol = symbol;
	}
	public String getOrderType() {
		return OrderType;
	}
	public void setOrderType(String orderType) {
		OrderType = orderType;
	}
	public int getOrderQty() {
		return OrderQty;
	}
	public void setOrderQty(int orderQty) {
		OrderQty = orderQty;
	}
	public String getTimeInForce() {
		return TimeInForce;
	}
	public void setTimeInForce(String timeInForce) {
		TimeInForce = timeInForce;
	}
	public String getTransacTime() {
		return TransacTime;
	}
	public void setTransacTime(String transacTime) {
		TransacTime = transacTime;
	}
	public String getMaturityMonthYear() {
		return MaturityMonthYear;
	}
	public void setMaturityMonthYear(String maturityMonthYear) {
		MaturityMonthYear = maturityMonthYear;
	}
	public String getMaturityDay() {
		return MaturityDay;
	}
	public void setMaturityDay(String maturityDay) {
		MaturityDay = maturityDay;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public int getClOrdID() {
		return ClOrdID;
	}
	public void setClOrdID(int clOrdID) {
		ClOrdID = clOrdID;
	}
	public String getExecInst() {
		return ExecInst;
	}
	public void setExecInst(String execInst) {
		ExecInst = execInst;
	}
}








