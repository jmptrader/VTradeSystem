package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import util.EODProcess;
import util.JSONUtil;

/***
 * This connection handle the communication with database. This include create
 * and close connection, as well as construct query and send query to database
 * 
 * @author zeweijiang
 *
 */
public class Database {
	static String jdbcUrl = "jdbc:mysql://cs4111.cf7twhrk80xs.us-west-2.rds.amazonaws.com:3306/V_Trade";
	// static String jdbcUrl = "jdbc:mysql://localhost:8889/vtrade";
	static Connection conn = null;
	private static Statement stmt;

	/**
	 * This is a constructor
	 * 
	 * @throws SQLException
	 */
	public Database() throws SQLException {
		connect();
	}

	/**
	 * This function create a connection to database
	 * 
	 * @throws SQLException
	 */
	public static void connect() throws SQLException {
		if (conn != null) {
			conn.close();
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userid = "sd2810";
		String password = "26842810";
		// String userid = "root";
		// String password = "root";
		conn = DriverManager.getConnection(jdbcUrl, userid, password);
	}

	/**
	 * Close the connection to database
	 */
	public static void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * Get all the transaction data as json format
	 * 
	 * @return return data in json format
	 */
	public static JSONObject getTrade() {
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from Orders");
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("test", JSONUtil.convert(rset));
			return jsonobject;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Add a new transaction
	 * 
	 * @param symbol
	 * @param exp
	 *            : expire date of this future
	 * @param lots
	 * @param price
	 * @param buysell
	 *            : buy or sell
	 * @param trader
	 *            : trader id
	 * @param transDate
	 * @param transTime
	 * @return true if record add successfully
	 */
	public static int addTrade(String orderType, String symbol, String exp,
			String lots, String price, String buysell, String trader,
			String transDate, String transTime) {
		try {
			connect();
			stmt = conn.createStatement();

			addCommodity(symbol, exp);
			addTrader(trader);

			String query = "INSERT INTO Orders"
					+ "(orderType, traderId, symbol, expire_date, action,price,lots,date,time) VALUES"
					+ "(\"" + orderType + "\"," + trader + ",\"" + symbol
					+ "\",\"" + exp + "\",\"" + buysell + "\"," + price + ",\""
					+ ((buysell.compareTo("buy") == 0) ? lots : ("-" + lots))
					+ "\",\"" + transDate + "\",\"" + transTime + "\")";

			stmt.executeUpdate(query, stmt.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
				generatedKey = rs.getInt(1);
				return generatedKey;
			}
			return -1;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	/***
	 * Add a new transaction sent from exchange
	 * 
	 * @param reportId
	 * @param orderId
	 * @param price
	 * @param lots
	 * @param transactTime
	 * @return
	 */
	public static int addFill(int reportId, int orderId, double price,
			int lots, String transactTime) {
		try {
			connect();
			stmt = conn.createStatement();

			String query = "INSERT INTO Report"
					+ "(reportId, orderId, price, lots, transactTime) VALUES"
					+ "(" + reportId + "," + orderId + "," + price + "," + lots
					+ ",\"" + transactTime + "\")";
			stmt.executeUpdate(query);
			return 1;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	/**
	 * Add a trader to trader table
	 * 
	 * @param trader
	 *            : trader id
	 */
	private static void addTrader(String trader) {
		// TODO Auto-generated method stub
		try {
			connect();
			stmt = conn.createStatement();
			String name = "test name";
			String query = "INSERT INTO Trader" + "(name, traderId) VALUES"
					+ "(\"" + name + "\"," + trader + ")";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Add a commodity to Commodity table
	 * 
	 * @param symbol
	 * @param exp
	 *            : expire date of future
	 */
	private static void addCommodity(String symbol, String exp) {
		// TODO Auto-generated method stub
		try {
			connect();
			stmt = conn.createStatement();

			String query = "INSERT INTO Commodity"
					+ "(symbol, expire_date) VALUES" + "(\"" + symbol + "\",\""
					+ exp + "\")";
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Get all the transaction from database and return in as a csv format
	 * string
	 * 
	 * @return csv format string
	 */
	public static String getTradeCSV() {
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', orderId, traderId, symbol, expire_date,"
							+ "action, price, lots, date, time) from Orders");
			StringBuilder sb = new StringBuilder();
			sb.append("transactionId, traderId, symbol, expire_date, action, price, lots, date, time\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Get the future contracts expires on current date.
	 * 
	 * @return
	 */
	public static String getTradeCSVFutureExpireToday() {
		EODProcess eodProcess = EODProcess.getInstance();
		Date currentDate = eodProcess.getCurrentDate();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = fmt.format(currentDate);
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', orderId, traderId, symbol, expire_date,"
							+ "action, price, lots, date, time) from Orders where expire_date =\""
							+ dateString + "\"");
			StringBuilder sb = new StringBuilder();
			sb.append("transactionId, traderId, symbol, expire_date, action, price, lots, date, time\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Get the future contracts expires after current date.
	 * 
	 * @return
	 */
	public static String getTradeCSVFutureExpireAfterToday() {
		EODProcess eodProcess = EODProcess.getInstance();
		Date currentDate = eodProcess.getCurrentDate();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = fmt.format(currentDate);
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', orderId, traderId, symbol, expire_date,"
							+ "action, price, lots, date, time) from Orders where expire_date >\""
							+ dateString + "\"");
			StringBuilder sb = new StringBuilder();
			sb.append("transactionId, traderId, symbol, expire_date, action, price, lots, date, time\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Get the swap contract expires on current date.
	 * 
	 * @return
	 */
	public static String getTradeCSVSwapExpireToday() {
		EODProcess eodProcess = EODProcess.getInstance();
		Date currentDate = eodProcess.getCurrentDate();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = fmt.format(currentDate);
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', swapId, trader, start, termination, floatRate,"
							+ "spread, fixedRate, fixedPayer, parValue, date, time) from Swaps "
							+ "where termination =\"" + dateString + "\"");
			StringBuilder sb = new StringBuilder();
			sb.append("transactionId, trader, start, termination, floatRate, spread, fixedRate, fixedPayer"
					+ " parValue, date, time\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Get the swap contracts expires after current date.
	 * 
	 * @return
	 */
	public static String getTradeCSVSwapAfterToday() {
		EODProcess eodProcess = EODProcess.getInstance();
		Date currentDate = eodProcess.getCurrentDate();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = fmt.format(currentDate);
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', swapId, trader, start, termination, floatRate,"
							+ "spread, fixedRate, fixedPayer, parValue, date, time) from Swaps "
							+ "where termination >\"" + dateString + "\"");
			StringBuilder sb = new StringBuilder();
			sb.append("transactionId, trader, start, termination, floatRate, spread, fixedRate, fixedPayer"
					+ " parValue, date, time\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/***
	 * Get detail transaction for given traderId in csv format
	 * 
	 * @param traderId
	 * @return
	 */
	public static String getTranctionCSVWithCondition(String traderId) {
		// TODO Auto-generated method stub
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', traderId, symbol, expire_date, profit)"
							+ "from (Select t.traderId, t.symbol, t.expire_date, (( (Select sum(s.price * s.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as s Where s.action = 'sell' and s.traderId = t.traderId and s.symbol = t.symbol and s.expire_date = t.expire_date ) - (Select sum(b.price * b.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as b Where b.action = 'buy' and b.traderId = t.traderId and b.symbol = t.symbol and b.expire_date = t.expire_date ) )*250.0) as profit from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as t group by t.traderId, t.symbol, t.expire_date) as d"
							+ " where traderId=" + traderId);
			StringBuilder sb = new StringBuilder();
			sb.append("traderId, symbol, expire_date, profit\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/***
	 * Get transaction in csv format
	 * 
	 * @return
	 */
	public static String getTransactionCSV() {
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', traderId, profit)"
							+ "from (select traderId, sum(profit*250) as profit from (Select t.traderId, t.symbol, t.expire_date, ( (Select sum(s.price * s.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as s Where s.action = 'sell' and s.traderId = t.traderId and s.symbol = t.symbol and s.expire_date = t.expire_date ) - (Select sum(b.price * b.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as b Where b.action = 'buy' and b.traderId = t.traderId and b.symbol = t.symbol and b.expire_date = t.expire_date ) ) as profit from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as t group by t.traderId, t.symbol, t.expire_date) as f group by traderId) as d");
			StringBuilder sb = new StringBuilder();
			sb.append("traderId, profit\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/***
	 * Get all the transactions
	 * 
	 * @return
	 */
	public static JSONObject getTransaction() {
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report Where Orders.orderId = Report.orderId");
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("test", JSONUtil.convert(rset));
			return jsonobject;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/***
	 * Generage aggregation position output give trader id
	 * 
	 * @param traderId
	 * @return
	 */
	public static String getTransactionCSVWithPrice(String traderId) {
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', traderId, symbol, expire_date, lots, buy_average, sell_average)"
							+ "from (select traderId, symbol, expire_date, ( ( Select sum(b.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as b Where b.action = 'buy' and b.traderId = t.traderId and b.symbol = t.symbol and b.expire_date = t.expire_date ) - ( Select sum(s.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as s Where s.action = 'sell' and s.traderId = t.traderId and s.symbol = t.symbol and s.expire_date = t.expire_date ) ) as lots, (( Select sum(b.price * b.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as b Where b.action = 'buy' and b.traderId = t.traderId and b.symbol = t.symbol and b.expire_date = t.expire_date )/( Select sum( s.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as s Where s.action = 'buy' and s.traderId = t.traderId and s.symbol = t.symbol and s.expire_date = t.expire_date )) as buy_average, (( Select sum(s.price * s.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as s Where s.action = 'sell' and s.traderId = t.traderId and s.symbol = t.symbol and s.expire_date = t.expire_date )/( Select sum( s.lots) from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as s Where s.action = 'sell' and s.traderId = t.traderId and s.symbol = t.symbol and s.expire_date = t.expire_date )) as sell_average from ( select Report.reportId, Report.price, Report.lots, Report.transactTime, Orders.symbol, Orders.expire_date, Orders.action, Orders.traderId from Orders join Report on Orders.orderId = Report.orderId ) as t group by traderId, symbol, expire_date) as d"
							+ " where traderId=" + traderId);
			StringBuilder sb = new StringBuilder();
			sb.append("traderId, symbol, expire_date, lots, buy_average, sell_average\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static Integer addSwap(String start, String termination,
			String floatRate, String spread, String fixedRate,
			String fixedPayer, String parValue, String trader, String day,
			String time) {
		// TODO Auto-generated method stub
		try {
			connect();
			stmt = conn.createStatement();

			addTrader(trader);

			String query = "INSERT INTO Swaps"
					+ "(start, termination, floatRate, spread, fixedRate, fixedPayer, parValue, trader,date,time) VALUES"
					+ "(\"" + start + "\",\"" + termination + "\",\""
					+ floatRate + "\"," + spread + "," + fixedRate + ",\""
					+ fixedPayer + "\"," + parValue + "," + trader + ",\""
					+ day + "\",\"" + time + "\")";

			stmt.executeUpdate(query, stmt.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
				generatedKey = rs.getInt(1);
				return generatedKey;
			}
			return -1;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	public static JSONObject getSwap() {
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from Swaps");
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("test", JSONUtil.convert(rset));
			return jsonobject;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String getSwapCSVGivenTraderId(String traderId) {
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', swapId, trader, start, termination, floatRate,"
							+ "spread, fixedRate, fixedPayer, parValue, date, time) from Swaps "
							+ "where trader =" + traderId);
			StringBuilder sb = new StringBuilder();
			sb.append("transactionId, trader, start, termination, floatRate, spread, fixedRate, fixedPayer"
					+ " parValue, date, time\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public static String getSwapCSV() {
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', swapId, trader, start, termination, floatRate,"
							+ "spread, fixedRate, fixedPayer, parValue, date, time) from Swaps ");
			StringBuilder sb = new StringBuilder();
			sb.append("transactionId, trader, start, termination, floatRate, spread, fixedRate, fixedPayer"
					+ " parValue, date, time\n");
			while (rset.next()) {
				sb.append(rset.getString(1));
				sb.append("\n");
			}
			return sb.toString();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}