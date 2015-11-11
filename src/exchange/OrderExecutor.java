/**
 * 
 */
package exchange;

import util.ACK;
import util.ExeReport;
import util.InfoExchange;
import util.Order;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * The class design of order executor.
 * 
 * It takes an order, and according to the order type, side, price and quantity
 * information, returns 1 or 2 exeReports for that order as well as ACK message.
 * 
 * # The prob. over FF and PF is as follows Full-Fill Partial-Fill(up to 2
 * fills) market order: 1 0 limited order: 1/2 1/2 pegged order: 1/2 1/2
 * 
 * Another random mechanism is guaranteed exe price within reasonable range for
 * market, limited and pegged orders.
 * 
 * @author pennlio
 *
 */
public class OrderExecutor {
	public int exeCounter = 0;
	private Date exchangeDate = new Date();
	private static int RANDOM_NUMBER_BOUND = 100;
	private static int FULL_FILL_THRESHOLD = 50;
	private static double MAX_EXE_PRICE = 50.00f;
	private static double MIN_EXE_PRICE = 10.00f;

	private static OrderExecutor instance = null; // singleton

	protected OrderExecutor() {
		// Exists only to defeat instantiation.
	}

	/**
	 * Singleton constructor of OrderExecutor.
	 * 
	 * @return The instance of OrderExecutor.
	 */
	public static OrderExecutor getInstance() {
		if (instance == null) {
			instance = new OrderExecutor();
		}
		return instance;
	}

	public Date getExchangeDate() {
		return instance.exchangeDate;
	}

	public void setExchangeDate(Date exchangeDate) {
		instance.exchangeDate = exchangeDate;
	}

	/**
	 * Generate reasonable execution price according to order type.
	 * 
	 * @param order
	 * @return
	 */
	protected double generateExePrice(Order order) {
		String orderType = order.getOrderType();
		Random rand = new Random();
		double exePrice;
		if (orderType.equals("1") || orderType.equals("P")) {
			exePrice = (MAX_EXE_PRICE - MIN_EXE_PRICE) * rand.nextFloat()
					+ MIN_EXE_PRICE;
		} else {
			if (order.getSide() == 1) { // limited buy order
				exePrice = (order.getPrice() - MIN_EXE_PRICE)
						* rand.nextFloat() + MIN_EXE_PRICE;
			} else { // limited sell order
				exePrice = (MAX_EXE_PRICE - MIN_EXE_PRICE) * rand.nextFloat()
						+ order.getPrice();
			}
		}
		DecimalFormat decimalFormat = new DecimalFormat(".##");
		return Double.parseDouble(decimalFormat.format(exePrice));
	}

	/**
	 * Generate execution report for this order.
	 * 
	 * @param order
	 *            : input order.
	 * @return List of execution reports.
	 */
	public List<ExeReport> generateExeReport(Order order) {
		List<ExeReport> exReportList = new ArrayList<ExeReport>();
		Random rand = new Random();
		int randomIndex = rand.nextInt(RANDOM_NUMBER_BOUND);
		boolean doFullFill = false;
		if (order.getOrderType().equals("1")
				|| randomIndex > FULL_FILL_THRESHOLD) {
			// for market order, do full fill; for others , do full fill
			// randomly
			doFullFill = true;
		}
		if (doFullFill) {
			double exePrice = this.generateExePrice(order);
			ExeReport curReport = new ExeReport(order, ++exeCounter, exePrice);
			exReportList.add(curReport);
		} else {
			// partial order will be filled up to 2 times.
			int leaveQty = order.getOrderQty();
			while (leaveQty > 0) {
				if (doFullFill) {
					double exePrice = this.generateExePrice(order);
					ExeReport lastReport = exReportList.get(0);
					double avgPrice = (lastReport.getLastPx()
							* lastReport.getLastShares() + exePrice * leaveQty)
							/ order.getOrderQty();
					ExeReport curReport = new ExeReport(order, ++exeCounter,
							exePrice, avgPrice, leaveQty, order.getOrderQty());
					exReportList.add(curReport);
					break;
				} else {
					int firstExeQty = rand.nextInt(order.getOrderQty() - 1);
					leaveQty -= firstExeQty;
					double exePrice = this.generateExePrice(order);
					ExeReport curReport = new ExeReport(order, ++exeCounter,
							exePrice, exePrice, firstExeQty, firstExeQty);
					exReportList.add(curReport);
					// 2nd fill must be full fill
					doFullFill = true;
				}
			}
		}
		return exReportList;
	}

}
