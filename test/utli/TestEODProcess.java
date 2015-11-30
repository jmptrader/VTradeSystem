package utli;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

import util.EODProcess;

/**
 * Test class for EOD process.
 * @author pennlio
 *
 */
public class TestEODProcess {
	EODProcess eodProcess = EODProcess.getInstance();

	@Test
	public void testDate(){
		String currentDate = eodProcess.getCurrentDateString();
		eodProcess.goToNextBusinessDay();
		String nextDay = eodProcess.getCurrentDateString();
		Assert.assertFalse(nextDay.equals(currentDate));
		eodProcess.resetSytemDateToNow();
		String finalDay = eodProcess.getCurrentDateString();
		Assert.assertTrue(currentDate.equals(finalDay));
	}
	
	@Test
	public void testSkipHolidays(){
		for (int i= 0; i < 30; i++){
			eodProcess.goToNextBusinessDay();
			Assert.assertTrue(eodProcess.isBusinessDay(eodProcess.getCurrentDate()));
		}
	}
	
	@Test
	public void testGetLastBusinessDay(){
		String yearAndMonth = "201602";
		Date lastDay = eodProcess.getLastBusinessDay(yearAndMonth);
		Assert.assertTrue(eodProcess.isBusinessDay(lastDay));
	}
	
	@Test
	public void testGetThreeDaysLater(){
		Date threeDaysLater = eodProcess.getThreeBusinessDaysLater();
		Date currentDate = eodProcess.getCurrentDate();
		int diffInDays = (int)( (threeDaysLater.getTime() - currentDate.getTime()) 
				/ (1000 * 60 * 60 * 24) );
		Assert.assertTrue(diffInDays >= 3);
		Assert.assertTrue(eodProcess.isBusinessDay((threeDaysLater)));
	}
}
