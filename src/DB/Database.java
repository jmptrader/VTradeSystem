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

public class Database {
	static String jdbcUrl = "jdbc:mysql://cs4111.cf7twhrk80xs.us-west-2.rds.amazonaws.com:3306/V_Trade";
	static Connection conn = null;
	private static Statement stmt;

	public Database() throws SQLException {
		connect();
	}

	public static void connect() throws SQLException {
		if (conn != null)
			return;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userid = "sd2810";
		String password = "26842810";
		conn = DriverManager.getConnection(jdbcUrl, userid, password);
	}

	public static void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}

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

	public static boolean addTrade(String symbol, String exp, String lots,
			String price, String buysell, String trader, String transDate,
			String transTime) {
		try {
			connect();
			stmt = conn.createStatement();

			addCommodity(symbol, exp);
			addTrader(trader);

			String query = "INSERT INTO Transaction"
					+ "(traderId, symbol, expire_date, action,price,lots,date,time) VALUES"
					+ "(" + trader + ",\"" + symbol + "\",\"" + exp + "\",\""
					+ buysell + "\"," + price + ",\""
					+ ((buysell.compareTo("buy") == 0) ? lots : ("-" + lots))
					+ "\",\"" + transDate + "\",\"" + transTime + "\")";
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

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

	public static String getTradeCSVWithCondition(String traderId) {
		// TODO Auto-generated method stub
		try {
			connect();
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select CONCAT_WS(',', symbol, sum(lots) )"
							+ "from Transaction " + "where traderId="
							+ traderId
							+ " and expire_date > Curdate() group by symbol");
			StringBuilder sb = new StringBuilder();
			sb.append("symbol, Lots\n");
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