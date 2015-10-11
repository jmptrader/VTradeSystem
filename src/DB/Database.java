package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;

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
			return ;
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

	public static JSONArray test() {
		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from Trader");
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}
}