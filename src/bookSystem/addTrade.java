package bookSystem;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
 * Servlet implementation class addTrade
 */
@WebServlet("/addTrade")
public class addTrade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public addTrade() {
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
		Date date = Calendar.getInstance().getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String day = df.format(date);
		DateFormat tf = new SimpleDateFormat("HH:mm:ss");
		String time = tf.format(date);
		String MaturityMonthYear = request.getParameter("exp").substring(0, 4)
				+ request.getParameter("exp").substring(5, 7);
		String MaturityDay = "01";// util.generateDay(MaturityMonthYear)
		String expire_t0_sql = request.getParameter("exp") + "-" + MaturityDay;

		Integer ClOrdID = Database.addTrade(request.getParameter("orderType"),
				request.getParameter("symbol"), expire_t0_sql,
				request.getParameter("lots"), request.getParameter("price"),
				request.getParameter("buysell"),
				request.getParameter("trader"), day, time);
		if (ClOrdID < 0) {
			response.sendRedirect(request.getContextPath());
			return;
		}

		int SenderCompID = Integer.parseInt(request.getParameter("trader"));
		String SendingTime = request.getParameter("exp").substring(0, 4)
				+ request.getParameter("exp").substring(5, 7)
				+ request.getParameter("exp").substring(8, 10) + "-" + time;
		Integer Side = request.getParameter("buysell").compareTo("buy") == 0 ? 1
				: 2;
		Integer OrderQty = Integer.parseInt(request.getParameter("lots"));
		Double Price;
		String Symbol = request.getParameter("symbol");
		String OrderType;
		String TimeInForce = "1";
		String TransacTime = SendingTime;
		String ExecInst = "M";
		if (request.getParameter("orderType").compareTo("Market") == 0) {
			Price = 0.0;
			OrderType = "1";
		} else if (request.getParameter("orderType").compareTo("Limit") == 0) {
			Price = Double.parseDouble(request.getParameter("price"));
			OrderType = "2";
		} else {
			Price = Double.parseDouble(request.getParameter("price"));
			OrderType = "P";
		}

		Order order = new Order(SenderCompID, SendingTime, ClOrdID, Side,
				OrderQty, Price, Symbol, OrderType, TimeInForce, TransacTime,
				MaturityMonthYear, MaturityDay, ExecInst);
		try {
			RequestHelper.sendPost(
					"http://vtrade-env.elasticbeanstalk.com/receiveOrder",
					InfoExchange.orderDeparser(order));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath());
	}
}
