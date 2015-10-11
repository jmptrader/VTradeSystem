package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

import org.json.JSONArray;

import util.JSONUtil;

public class Database {
	static String jdbcUrl = "jdbc:mysql://cs4111.cf7twhrk80xs.us-west-2.rds.amazonaws.com:3306/cs4111";
	static Connection conn;
	private static Statement stmt;

	public Database() throws SQLException {
		connect();
	}

	public static void connect() throws SQLException {
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
			ResultSet rset = stmt.executeQuery("show tables");
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showEvents(String order, String cname, String type,
			String id, Date date) {
		try {
			String orderstring = ((order == null) ? "" : " order by " + order);
			LinkedList<String> l = new LinkedList<String>();
			String condition1 = !(cname != null && cname.equals("----") == false) ? ""
					: "companyName = \"" + cname + "\"";
			String condition2 = !(type != null && type.equals("----") == false) ? ""
					: "typeName = \"" + type + "\"";
			String condition3 = !(id != null && id.equals("----") == false) ? ""
					: "email = \"" + id + "\"";
			String condition4 = date == null ? "" : "( startTime <= \""
					+ new Timestamp(date.getTime()) + "\" and endTime >= \""
					+ new Timestamp(date.getTime()) + "\")";

			l.add(condition1);
			l.add(condition2);
			l.add(condition3);
			l.add(condition4);
			String condition = "";
			int count = 0;
			for (int i = 0; i < l.size(); i++) {
				if (count == 0 && l.get(i) != "") {
					condition = " where " + l.get(i);
					count++;
				} else if (count > 0 && l.get(i) != "") {
					condition = condition + " and " + l.get(i);
				}
			}

			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select * from SubcontractFirms natural join CanWorkFor natural join EventType natural join Events natural join Customers natural join Venue"
							+ condition + orderstring);
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showcustomerid() {
		try {

			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from Customers ");
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showcompanyid() {
		try {

			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select * from SubcontractFirms ");
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showvenueid() {
		try {

			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select vid, venueName,streetAddress,city,state,Country,zipCode from Venue natural join Address");
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showeventtypeid() {
		try {

			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from EventType");
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public boolean insertEvent(String eventName, String cid, String vid,
			String tid, String cssn, Date startTime, Date endTime) {
		try {
			stmt = conn.createStatement();
			System.out
					.println("select * from Events natural join Venue where vid="
							+ vid
							+ " and (( startTime <= \""
							+ new Timestamp(startTime.getTime())
							+ "\" and startTime <= \""
							+ new Timestamp(endTime.getTime())
							+ "\") or ( endTime >= \""
							+ new Timestamp(startTime.getTime())
							+ "\" and endTime <= \""
							+ new Timestamp(endTime.getTime()) + "\"))");
			ResultSet rset = stmt
					.executeQuery("select * from Events natural join Venue where vid="
							+ vid
							+ " and (( startTime <= \""
							+ new Timestamp(startTime.getTime())
							+ "\" and startTime <= \""
							+ new Timestamp(endTime.getTime())
							+ "\") or ( endTime >= \""
							+ new Timestamp(startTime.getTime())
							+ "\" and endTime <= \""
							+ new Timestamp(endTime.getTime()) + "\"))");
			if (rset.next())
				return false;
			String query = "INSERT INTO Events"
					+ "(eventName, cid, vid, tid,cssn,startTime,endTime,timebooked) VALUES"
					+ "(\"" + eventName + "\"," + cid + "," + vid + "," + tid
					+ "," + cssn + ",\"" + new Timestamp(startTime.getTime())
					+ "\",\"" + new Timestamp(startTime.getTime())
					+ "\",CURRENT_TIMESTAMP)";
			System.out.println(query);
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			return false;
		}

	}

	public JSONArray showCustomers(String order, String telephoneNumber,
			String email, String firstName, String lastName) {
		// TODO Auto-generated method stub
		try {
			String orderstring = ((order == null) ? "" : " order by " + order);

			LinkedList<String> l = new LinkedList<String>();
			String condition1 = !(telephoneNumber != null && telephoneNumber
					.equals("----") == false) ? "" : "telephoneNumber = \""
					+ telephoneNumber + "\"";
			String condition2 = !(email != null && email.equals("----") == false) ? ""
					: "email = \"" + email + "\"";
			String condition3 = !(firstName != null && firstName.equals("----") == false) ? ""
					: "firstName = \"" + firstName + "\"";
			String condition4 = !(lastName != null && lastName.equals("----") == false) ? ""
					: "lastName = \"" + lastName + "\"";
			l.add(condition1);
			l.add(condition2);
			l.add(condition3);
			l.add(condition4);
			String condition = "";
			int count = 0;
			for (int i = 0; i < l.size(); i++) {
				if (count == 0 && l.get(i) != "") {
					condition = " where " + l.get(i);
					count++;
				} else if (count > 0 && l.get(i) != "") {
					condition = condition + " and " + l.get(i);
				}
			}

			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from Customers"
					+ condition + orderstring);
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showCompanies(String order, String city, String state,
			String country, String companyName) {
		// TODO Auto-generated method stub
		try {
			String orderstring = ((order == null) ? "" : " order by " + order);

			LinkedList<String> l = new LinkedList<String>();
			String condition1 = !(companyName != null && companyName
					.equals("----") == false) ? "" : "companyName = \""
					+ companyName + "\"";
			String condition2 = !(city != null && city.equals("----") == false) ? ""
					: "city = \"" + city + "\"";
			String condition3 = !(state != null && state.equals("----") == false) ? ""
					: "state = \"" + state + "\"";
			String condition4 = !(country != null && country.equals("----") == false) ? ""
					: "country = \"" + country + "\"";
			l.add(condition1);
			l.add(condition2);
			l.add(condition3);
			l.add(condition4);
			String condition = "";
			int count = 0;
			for (int i = 0; i < l.size(); i++) {
				if (count == 0 && l.get(i) != "") {
					condition = " where " + l.get(i);
					count++;
				} else if (count > 0 && l.get(i) != "") {
					condition = condition + " and " + l.get(i);
				}
			}

			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from SubcontractFirms"
					+ condition + orderstring);
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showActivities(String order, String typeName) {
		// TODO Auto-generated method stub
		try {
			String orderstring = ((order == null) ? "" : " order by " + order);

			LinkedList<String> l = new LinkedList<String>();
			String condition1 = !(typeName != null && typeName.equals("----") == false) ? ""
					: "typeName = \"" + typeName + "\"";
			l.add(condition1);
			String condition = "";
			int count = 0;
			for (int i = 0; i < l.size(); i++) {
				if (count == 0 && l.get(i) != "") {
					condition = " where " + l.get(i);
					count++;
				} else if (count > 0 && l.get(i) != "") {
					condition = condition + " and " + l.get(i);
				}
			}

			stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from EventType"
					+ condition + orderstring);
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showVenues(String order, String min, String max,
			String city, String state, String country, String zipCode) {
		// TODO Auto-generated method stub
		try {
			String orderstring = ((order == null) ? "" : " order by " + order);

			LinkedList<String> l = new LinkedList<String>();
			String condition1 = !(city != null && city.equals("----") == false) ? ""
					: "city = \"" + city + "\"";
			String condition2 = !(state != null && state.equals("----") == false) ? ""
					: "state = \"" + state + "\"";
			String condition3 = !(country != null && country.equals("----") == false) ? ""
					: "country = \"" + country + "\"";
			String condition4 = !(zipCode != null && zipCode.equals("----") == false) ? ""
					: "zipCode = \"" + zipCode + "\"";
			l.add(condition1);
			l.add(condition2);
			l.add(condition3);
			l.add(condition4);
			String condition = min == null ? "" : " where capacity > " + min
					+ " and capacity < " + max;
			int count = min == null ? 0 : 1;
			for (int i = 0; i < l.size(); i++) {
				if (count == 0 && l.get(i) != "") {
					condition = " where " + l.get(i);
					count++;
				} else if (count > 0 && l.get(i) != "") {
					condition = condition + " and " + l.get(i);
				}
			}
			System.out.println("select * from Venue natural join Address"
					+ condition + orderstring);
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select * from Venue natural join Address"
							+ condition + orderstring);
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public boolean insertCustomer(String a, String b, String c, String d,
			String e) {
		// TODO Auto-generated method stub
		try {
			stmt = conn.createStatement();

			String query = "INSERT INTO Customers"
					+ "(cssn, email, firstName, lastName,telephoneNumber) VALUES"
					+ "(" + a + " ,\"" + b + "\",\"" + c + "\",\"" + d
					+ "\",\"" + e + "\")";
			System.out.println(query);
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException error) {
			return false;
		}
	}

	public JSONArray canWorkFor(String cid) {
		// TODO Auto-generated method stub
		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select * from SubcontractFirms natural join CanWorkFor natural join EventType where cid = "
							+ cid);
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray canWorkForActivity(String tid) {
		// TODO Auto-generated method stub
		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select * from SubcontractFirms natural join CanWorkFor natural join EventType where tid = "
							+ tid);
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public JSONArray showPartipants(String eid) {
		// TODO Auto-generated method stub
		try {
			stmt = conn.createStatement();
			ResultSet rset = stmt
					.executeQuery("select * from Events natural join ParticipateIn natural join Participants where eid="
							+ eid);
			return JSONUtil.convert(rset);
		} catch (SQLException e) {
			return null;
		}
	}

	public boolean insertParticipant(String eid, String ssn, String firstName,
			String lastName) {
		try {
			stmt = conn.createStatement();

			String query = "INSERT INTO Participants"
					+ "(ssn,firstName, lastName) VALUES" + "(" + ssn + " ,\""
					+ firstName + "\",\"" + lastName + "\")";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);

			stmt = conn.createStatement();
			query = "INSERT INTO ParticipateIn" + "(ssn, eid) VALUES" + "("
					+ ssn + " ," + eid + ")";
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (SQLException error) {
			return false;
		}
	}
}