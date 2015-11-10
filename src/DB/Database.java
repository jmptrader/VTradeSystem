package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONObject;

import util.JSONUtil;

/***
 * This connection handle the communication with database. This include create
 * and close connection, as well as construct query and send query to database
 * 
 * @author zeweijiang
 *
 */
public class Database {
	// static String jdbcUrl =
	// "jdbc:mysql://cs4111.cf7twhrk80xs.us-west-2.rds.amazonaws.com:3306/V_Trade";
	static String jdbcUrl = "jdbc:mysql://localhost:8889/vtrade";
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
		// String userid = "sd2810";
		// String password = "26842810";
		String userid = "root";
		String password = "root";
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
			ResultSet rset = stmt.executeQuery("select * from Transaction");
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

			String query = "INSERT INTO Transaction"
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

	/**
	 * Add a new transaction sent from exchange
	 * 
	 * @param orderType
	 * @param symbol
	 * @param exp
	 * @param lots
	 * @param price
	 * @param buysell
	 * @param trader
	 * @param transDate
	 * @param transTime
	 * @return
	 */
	public static int addFill(String orderType, String symbol, String exp,
			String lots, String price, String buysell, String trader,
			String transDate, String transTime) {
		try {
			connect();
			stmt = conn.createStatement();

			addCommodity(symbol, exp);
			addTrader(trader);

			String query = "INSERT INTO Transaction"
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
					.executeQuery("select CONCAT_WS(',', transactionId, traderId, symbol, expire_date,"
							+ "action, price, lots, date, time) from Transaction");
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
	 * Get aggregation transaction data given trader id.
	 * 
	 * @param traderId
	 * @return un-expired transaction data for the given trader id group by
	 *         symbol
	 */
	public static String getTradeCSVWithCondition(String traderId) {
		// TODO Auto-generated method stub
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', symbol, expire_date,sum(lots) )"
							+ " from Transaction "
							+ "where traderId="
							+ traderId + " group by symbol, expire_date");
			StringBuilder sb = new StringBuilder();
			sb.append("symbol, expire_date, Lots\n");
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