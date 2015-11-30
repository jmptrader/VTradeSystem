package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Class for EOD (end of day) process.
 * @author pennlio
 *
 */
public final class EODProcess {
	
	/*
	 * Offsets to initialize Date instance by Date(year, month, day)
	 */
	private static final int YEAR_OFFSET = 1900;
	private static final int MONTH_OFFSET = 1;
	private static final int DAY_OFFSET = 1;
	// Hashmap to record NYSE holidays.
	private HashMap<String, String> nyseHolidays = new HashMap<String, String>();
	private static EODProcess instance = null; 
	// get calendar instance
	private Calendar systemCalendar = Calendar.getInstance();
	private SimpleDateFormat stringFormat = new SimpleDateFormat("yyyyMMdd");     
	
	/**
	 * Constructor of EODProcess.
	 */
	@SuppressWarnings("deprecation")
	private EODProcess() {
		// Exists only to defeat instantiation.
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 0, 1)), "new year's day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(116, 0, 1)), "new year's day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 0, 19)), "martin luther king's day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(116, 0, 18)), "martin luther king's day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 1, 16)), "washington's day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(116, 1, 15)), "washington's day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 3, 3)), "good friday");
		this.nyseHolidays.put(this.stringFormat.format(new Date(116, 2, 25)), "good friday");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 4, 25)), "memorial day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 4, 30)), "memorial day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 6, 4)), "independent day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(116, 6, 4)), "independent day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 8, 7)), "labor day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(116, 8, 5)), "labor day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 10, 26)), "thanksgiving day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(116, 10, 24)), "thanksgiving day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(115, 11, 25)), "christmas day");
		this.nyseHolidays.put(this.stringFormat.format(new Date(116, 11, 25)), "christmas day");
	}
	
	/**
	 * Get instance of EODProcess; use singleton pattern.
	 * @return
	 */
	public static EODProcess getInstance(){
		if (EODProcess.instance == null){
			EODProcess.instance = new EODProcess();
		}
		return EODProcess.instance;
	}
	
	/**
	 * Judge if a date is business day.
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Boolean isBusinessDay(Date date){
		if (date.getDay() == Calendar.SATURDAY - 1 || date.getDay() == Calendar.SUNDAY - 1 || 
				this.nyseHolidays.containsKey(this.stringFormat.format(date))){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Get the last business day of given month and year.
	 * @param yearAndMonth in YYYYMM format.
	 * @return day of Date.
	 */
	@SuppressWarnings("deprecation")
	public Date getLastBusinessDay(String yearAndMonth){
		int year = Integer.parseInt(yearAndMonth.substring(0, 4)) - YEAR_OFFSET ;
		int month = Integer.parseInt(yearAndMonth.substring(4)) - MONTH_OFFSET;
		Date expiry = new Date(year, month, DAY_OFFSET);
		Calendar backCalendar = Calendar.getInstance();
		backCalendar.setTime(expiry);
		// get the maximal date of that month
		backCalendar.set(Calendar.DAY_OF_MONTH, backCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDay = backCalendar.getTime();
		// back-track
		while(!isBusinessDay(lastDay)){
			backCalendar.add(Calendar.DAY_OF_MONTH, -1);
			lastDay = backCalendar.getTime();
		}
		return lastDay;
	}
	
	/**
	 * Get the day string of the last business day of given month and year.
	 * @param yearAndMonth in YYYYMM format.
	 * @return the day string in DD format.
	 */
	public String getLastBusinessDayString(String yearAndMonth){
		Date lastDay = getLastBusinessDay(yearAndMonth);
		return this.stringFormat.format(lastDay).substring(6);
	}
	
	/**
	 * Get the date of three business days later of current day.
	 * @return
	 */
	public Date getThreeBusinessDaysLater(){
		Date currentDate = this.systemCalendar.getTime();
		Calendar forwardCalendar = Calendar.getInstance();
		forwardCalendar.setTime(currentDate);
		int counter = 3;
		while(counter > 0){
			forwardCalendar.add(Calendar.DATE, 1);
			currentDate = forwardCalendar.getTime();
			if (isBusinessDay(currentDate)){
				counter--;
			}
		}
		return currentDate;
	}

	/**
	 * Get current date.
	 * @return the current date.
	 */
	public Date getCurrentDate(){
		return this.systemCalendar.getTime();
	}

	/**
	 * Get current date string.
	 * @return the current date in string.
	 */
	public String getCurrentDateString(){
		Date currentDate = getCurrentDate();
		return this.stringFormat.format(currentDate);
	}
	
	/**
	 * Proceed to next business day. Skip if nextDay is a weekend 
	 * or a NYSE holiday.
	 */
	public void goToNextBusinessDay(){
		Date nextDay;
		do{
			this.systemCalendar.add(Calendar.DATE, 1);
			nextDay = this.systemCalendar.getTime();
		}
		while(!isBusinessDay(nextDay));
	}
	
	/**
	 * Reset system calendar date to now.
	 */
	public void resetSytemDateToNow(){
		this.systemCalendar = Calendar.getInstance();
	}

}
