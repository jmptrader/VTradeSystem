package exchange;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exchange.OrderExecutor;
import util.ACK;
import util.ExeReport;
import util.InfoExchange;
import util.Order;
import util.RequestHelper;

/**
 * Servlet implementation class receiveOrder
 */
@WebServlet("/receiveOrder")
public class receiveOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static OrderExecutor orderExecutor = OrderExecutor.getInstance();

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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("data") == null)
			return;
		String fixMessage = request.getParameter("data");

		Order order = InfoExchange.orderParser(fixMessage);

		ACK ack = new ACK(order, LocalDateTime.now().toString(),
				++orderExecutor.exeCounter);
		List<ExeReport> exeReports = orderExecutor.generateExeReport(order);

		try {
			RequestHelper.sendPost("http://localhost:8080/VTradeSystem/getACK",
					InfoExchange.ACKDeparser(ack));
			for (ExeReport rep : exeReports) {
				RequestHelper.sendPost(
						"http://localhost:8080/VTradeSystem/receiveFill",
						InfoExchange.ExeReportDeparser(rep));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
