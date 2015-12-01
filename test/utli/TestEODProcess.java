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
	public void testGetLastButThreeBusinessDay(){
		String yearAndMonth = "201601";
		Date lastDay = eodProcess.getLastButThreeBusinessDay(yearAndMonth);
		Assert.assertTrue(eodProcess.isBusinessDay(lastDay));
		System.out.println(lastDay.getDate());
	}
}
