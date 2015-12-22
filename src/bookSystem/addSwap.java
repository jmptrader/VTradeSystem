package bookSystem;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.InfoExchange;
import util.Order;
import util.RequestHelper;
import DB.Database;

/**
 * Servlet implementation class addSwap
 */
@WebServlet("/addSwap")
public class addSwap extends HttpServlet {
	private double DECISION_BOUND = 0.5f;
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addSwap() {
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
		Date date = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String day = df.format(date);
		DateFormat tf = new SimpleDateFormat("HH:mm:ss");
		String time = tf.format(date);
		double random = new Random().nextDouble();
		if (random <= DECISION_BOUND) {
			// log the swap
			Integer swapId = Database.addSwap(request.getParameter("start"),
					request.getParameter("termination"),
					request.getParameter("floatRate"),
					request.getParameter("spread"),
					request.getParameter("fixedRate"),
					request.getParameter("fixedPayer"),
					request.getParameter("parValue"),
					request.getParameter("trader"), day, time);
		}
		response.sendRedirect(request.getContextPath() + "/swap.jsp");
	}
}
