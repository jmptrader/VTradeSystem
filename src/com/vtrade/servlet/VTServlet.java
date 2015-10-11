package com.vtrade.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VTServlet
 */
@WebServlet("/VTServlet")
public class VTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
		}
	}


	/**
     * @see HttpServlet#HttpServlet()
     */
    public VTServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String traderId = request.getParameter("traderId");
		PrintWriter pw = response.getWriter();
		try {
			if (conn == null) {
				// Edit the following to use your endpoint, database name,
				// username, and password
				conn = DriverManager
						.getConnection(
								"jdbc:mysql://cs4111.cf7twhrk80xs.us-west-2.rds.amazonaws.com:3306/cs4111",
								"sd2810", "26842810");
				System.out.println("connecting to VSYSTEM-db");
			}
			//put logics here
 




		}catch (SQLException e) {
			pw.println(e.getMessage());
		}
		pw.close();

	}

}
