package bookSystem;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Database;

/**
 * Servlet implementation class getEODSwapTodayCSV
 */
@WebServlet("/getEODSwapTodayCSV")
public class getEODSwapTodayCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getEODSwapTodayCSV() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = Database.getTradeCSVSwapExpireToday();
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"IRSExpireToday.csv\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(result.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
