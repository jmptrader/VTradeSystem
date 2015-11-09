package exchange;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exchange.OrderExecutor;
import util.ACK;
import util.InfoExchange;
import util.Order;
import util.RequestHelper;

/**
 * Servlet implementation class receiveOrder
 */
@WebServlet("/receiveOrder")
public class receiveOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public receiveOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("data") == null)
			return;
		String fixMessage = request.getParameter("data");
		
		InfoExchange info = new InfoExchange();
				
		Order order = info.orderParser(fixMessage);
		OrderExecutor oe = OrderExecutor.getInstance();
		ACK ack = new ACK(order, OrderExecutor.getExchangeDate().toString(), ++oe.exeCounter);

		// String ackMessage = info.ackDeparser(ack);
		 
		try {
			RequestHelper.sendPost("http://localhost:8080/VTradeSystem/getACK",
					"this is an ack from: " + fixMessage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * InfoExchange.getACK(fixMessage)); Exchange.add(newOrder);
		 */
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
