package util;
import java.util.HashMap;

public class InfoExchange {
	private static String fixOrder = "35=%s^A34=1^A49=%d^A52=%s^A56=EXCHANGE^A11=%d^A54=%d^A38=%d^A44=%f^A55=%s^A40=%s^A59=%s^A47=A^A60=%s^A21=1^A167=FUT^A200=%s^A205=%s^A207=Test^A18=%s";
	private static String fixACK = 	 "^A17=%d^A37=%d";
	private static String fixExeInfo = "^A6=%f^A14=%d^A17=%d^A20=%d^A29=%d^A30=TEST^A31=%f^A32=%d^A37=%d^A39=%d^A58=NewOrder^A150=%d^A151=%d";
	private static String fixTail = "^A10=101^A";
	private static final HashMap<String,Integer> map;
	static{
		map = new HashMap<String,Integer>();
		map.put("MsgType", 35);
		map.put("SenderCompID",49);
		map.put("SendingTime",52);
		map.put("Side",54);
		map.put("OrderQty",38);
		map.put("Price",44);
		map.put("Symbol",55);
		map.put("OrderType",40);
		map.put("TimeInForce",59);
		map.put("MaturityDay",205);
		map.put("MaturityMonthYear",200);
		map.put("TransacTime",60);
		map.put("ClOrdID", 11);
		map.put("ExecInst", 18);
		map.put("AvgPx", 6);
		map.put("CumQty", 14);
		map.put("ExecID", 17);
		map.put("ExecTransType", 20);
		map.put("LastMkt", 30);
		map.put("LastPx", 31 );
		map.put("LastShares", 32);
		map.put("OrderID", 37);
		map.put("Text", 58);
		map.put("ExecType", 150);
		map.put("LeavesQty", 151);
		map.put("LastCapacity", 29);
	}
	
	public InfoExchange(){

	}
	
	public static void OrderFiller(Order order, HashMap<Integer,String> orderMap){
        order.OrderType = orderMap.get(map.get("OrderType"));
        order.SenderCompID = Integer.parseInt(orderMap.get(map.get("SenderCompID")));
        order.SendingTime = orderMap.get(map.get("SendingTime"));
        order.Side =  Integer.parseInt(orderMap.get(map.get("Side")));
        order.Symbol = orderMap.get(map.get("Symbol"));
        order.OrderQty = Integer.parseInt(orderMap.get(map.get("OrderQty")));
        order.TimeInForce = orderMap.get(map.get("TimeInForce"));
        order.MaturityMonthYear = orderMap.get(map.get("MaturityMonthYear"));
        order.MaturityDay = orderMap.get(map.get("MaturityDay"));
        order.TransacTime = orderMap.get(map.get("TransacTime"));
        order.MsgType = orderMap.get(map.get("MsgType"));
        order.ClOrdID = Integer.parseInt(orderMap.get(map.get("ClOrdID")));
        
        if(orderMap.containsKey(map.get("Price")))
            order.Price = Double.parseDouble(orderMap.get(map.get("Price")));
        if(orderMap.containsKey(map.get("ExecInst")))
            order.ExecInst = orderMap.get(map.get("ExecInst"));
    }

    public static String OrderFixConstructor(Order order){
        int SenderCompID = order.SenderCompID;
        String SendingTime = order.SendingTime;
        double Price = order.Price;
        int Side = order.Side;
        String Symbol = order.Symbol;
        String OrderType = order.OrderType;
        int OrderQty = order.OrderQty;
        String TimeInForce = order.TimeInForce;
        String TransacTime = order.TransacTime;
        String MaturityDay = order.MaturityDay;
        String MaturityMonthYear = order.MaturityMonthYear;
        String MsgType = order.MsgType;
        int ClOrdID = order.ClOrdID;
        String ExecInst = order.ExecInst;
        String fixOrderBody = String.format(fixOrder,
                MsgType,SenderCompID,SendingTime,ClOrdID,Side,OrderQty,Price,Symbol,OrderType,TimeInForce,TransacTime,MaturityMonthYear,MaturityDay,ExecInst);

        return fixOrderBody;

    }

    public static String orderDeparser(Order order){
        String fixOrderBody = OrderFixConstructor(order);
        int len = fixOrderBody.length()+fixTail.length();
        String prefix = String.format("8=FIX.4.2^A9=%d^A",len);
        return prefix+fixOrderBody+fixTail;
    }

    public static Order orderParser(String fixMsg){
        String[] orderInfo = fixMsg.split("\\^A");
        Order order = new Order();
        HashMap<Integer, String> orderMap = new HashMap<Integer, String>();
        for(String str : orderInfo){
            String[] temp = str.split("=");
            int tag = Integer.parseInt(temp[0]);
            String val = temp[1];
            orderMap.put(tag, val);
        }
        OrderFiller(order,orderMap);
        return order;
    }


    public static String ACKDeparser(ACK ack){
        Integer ExecID = ack.ExecID;
        Integer OrderID = ack.OrderID;
        String fixOrderBody = OrderFixConstructor(ack);
        String fixACKBody = String.format(fixACK, ExecID, OrderID);
        int len = fixOrderBody.length()+fixACKBody.length() + fixTail.length();
        String prefix = String.format("8=FIX.4.2^A9=%d^A",len);
        return prefix+fixOrderBody+fixACKBody;
    }

    public static ACK ACKParser(String fixMsg){
        String[] orderInfo = fixMsg.split("\\^A");
        HashMap<Integer, String> ackMap = new HashMap<Integer, String>();
        for(String str : orderInfo){
            String[] temp = str.split("=");
            int tag = Integer.parseInt(temp[0]);
            String val = temp[1];
            ackMap.put(tag, val);
        }
        ACK ack = new ACK();
        OrderFiller(ack,ackMap);
        ack.SendingTime = ackMap.get(map.get("SendingTime"));
        ack.ExecID =  Integer.parseInt(ackMap.get(map.get("ExecID")));
        ack.OrderID = Integer.parseInt(ackMap.get(map.get("OrderID")));
        return ack;
    }


    public static ExeReport ExeParser(String fixMsg){
        String[] exeInfo = fixMsg.split("\\^A");
        HashMap<Integer, String> exeMap = new HashMap<Integer, String>();
        for(String str : exeInfo){
            String[] temp = str.split("=");
            int tag = Integer.parseInt(temp[0]);
            String val = temp[1];
            exeMap.put(tag, val);
        }
        ExeReport exeReport = new ExeReport();
        OrderFiller(exeReport,exeMap);
        if(exeMap.containsKey(map.get("SendingTime")))
            exeReport.SendingTime = exeMap.get(map.get("SendingTime"));
        if(exeMap.containsKey(map.get("AvgPx")))
            exeReport.AvgPx = Double.parseDouble(exeMap.get(map.get("AvgPx")));
        if(exeMap.containsKey(map.get("CumQty")))
            exeReport.CumQty = Integer.parseInt(exeMap.get(map.get("CumQty")));
        if(exeMap.containsKey(map.get("ExecTranType")))
            exeReport.ExecTranType = Integer.parseInt(exeMap.get(map.get("ExecTranType")));
        if(exeMap.containsKey(map.get("LastPx")))
            exeReport.LastPx = Double.parseDouble(exeMap.get(map.get("LastPx")));
        if(exeMap.containsKey(map.get("LastShares")))
            exeReport.LastShares = Integer.parseInt(exeMap.get(map.get("LastShares")));
        if(exeMap.containsKey(map.get("OrdStatus")))
            exeReport.OrdStatus = Integer.parseInt(exeMap.get(map.get("OrdStatus")));
        if(exeMap.containsKey(map.get("ExecTranType")))
            exeReport.ExecTranType = Integer.parseInt(exeMap.get(map.get("ExecTranType")));
        if(exeMap.containsKey(map.get("LeavesQty")))
            exeReport.LeavesQty = Integer.parseInt(exeMap.get(map.get("LeavesQty")));
        if(exeMap.containsKey(map.get("lastCapacity")))
            exeReport.LastCapacity = Integer.parseInt(exeMap.get(map.get("lastCapacity")));
        return exeReport;
    }

    public static String ExeReportDeparser(ExeReport exeReport){
        Integer ExecID = exeReport.ExecID;
        Integer OrderID = exeReport.OrderID;
        //String SendingTime = exeReport.SendingTime;
        Double AvgPx = exeReport.AvgPx;
        Integer CumQty = exeReport.CumQty;
        Integer ExecTransType = exeReport.ExecTranType;
        //String LastMkt = exeReport.LastMk;
        Double LastPx = exeReport.LastPx;
        Integer LastShares = exeReport.LastShares;
        Integer OrdStatus = exeReport.OrdStatus;
        Integer ExecType = exeReport.ExecType;
        Integer LeavesQty = exeReport.LastShares;
        Integer LastCapacity = exeReport.LastCapacity;
        String fixOrderBody = OrderFixConstructor(exeReport);
        String fixExeBody = String.format(fixExeInfo,
                AvgPx, CumQty,ExecID,ExecTransType,LastCapacity,LastPx,LastShares,OrderID,OrdStatus,ExecType,LeavesQty);
        int len = fixOrderBody.length()+fixExeBody.length()+fixTail.length();
        String prefix = String.format("8=FIX.4.2^A9=%d^A",len);
        return prefix+fixOrderBody+fixExeBody;
    }
}
