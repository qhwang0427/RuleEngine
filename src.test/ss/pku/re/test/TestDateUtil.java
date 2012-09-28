package ss.pku.re.test;

import org.junit.Test;

import ss.pku.re.rule.util.DateUtil;

public class TestDateUtil {
	@Test
	public void testDiffOfTime(){
		String time1 = "2011-07-12 11:22:11 0012";
		String time2 = "2011-07-12 11:22:11 0016"; 
		System.out.println(DateUtil.diffOfTime(time2, time1));
	}
}
