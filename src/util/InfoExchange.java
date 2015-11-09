package util;
import java.util.HashMap;

public class InfoExchange {
	private static String fixOrder = "35=%s^A34=1^A49=%d^A52=%s^A56=EXCHANGE^A11=%d^A54=%d^A38=%d^A44=%f^A55=%s^A40=%s^A59=%s^A47=A^A60=%s^A21=1^A167=FUT^A200=%s^A205=%s^A207=Test^A18=%s^A10=101^A";
	private static String fixExeInfo = "";
	private static String fixACK = "";
	private static final HashMap<String,Integer> map;
	static{
		map = new HashMap<String,Integer>();
		map.put("MsgType", 35);
		map.put("SenderCompID",49);
		map.put("SendingTime",52);
		map.put("Side",54);
		map.put("OrderQty",38);
		map.put("Price",10);
		map.put("Symbol",55);
		map.put("OrderType",40);
		map.put("TimeInForce",59);
		map.put("MaturityDay",205);
		map.put("MaturityMonthYear",200);
		map.put("TransacTime",60);
		map.put("ClOrdID", 11);
		map.put("ExecInst", 18);
	}
	
	public InfoExchange(){

	}
	
	public static String orderDeparser(Order order){
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

        int len = fixOrderBody.length();
        String prefix = String.format("8=FIX.4.2^A9=%d^A",len);
        return prefix+fixOrderBody;
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
        return order;
	}
	
}
