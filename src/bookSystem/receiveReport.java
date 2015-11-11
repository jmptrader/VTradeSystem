package bookSystem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DB.Database;
import util.ExeReport;
import util.InfoExchange;

/**
 * Servlet implementation class receiveFill
 */
@WebServlet("/receiveReport")
public class receiveReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public receiveReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String fixMessage = request.getParameter("data");
		if (fixMessage == null) {
			System.out.println("Book system have received fill: "
					+ request.getParameter("data") + ", which is null.");
		}
		ExeReport rep = InfoExchange.ExeParser(request.getParameter("data"));
		int res = Database.addFill(rep.getExecID(), rep.getOrderID(),
				rep.getLastPx(), rep.getLastShares(), rep.getTransacTime());
		if (res < 0) {
			System.out.println("Add report fail");
		}
		response.sendRedirect(request.getContextPath());
	}
}
