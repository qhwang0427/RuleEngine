package ss.pku.re.rule.util;

import java.math.BigInteger;

import org.apache.log4j.Logger;

/**
 * 跟时间相关转换的工具类
 * @author lqs
 *
 */
public class DateUtil {
	/**
	 *  将时间的格式转换成double类型进行比较
	 * @param time
	 * @return  time的double表示  如：2012-07-01 11:02:11 0014 转换成 201207011102110014
	 * 时间转换不成功 则 返回-1
	 */
	private static Logger logger = Logger.getLogger(DateUtil.class);
	
	public static  BigInteger timeToDouble(final String time){
		try{
			String temp = time.replaceAll("-|:| ", "");
			BigInteger cmpTime = new BigInteger(temp,10);
			return cmpTime;
		}catch(NumberFormatException e){
			e.printStackTrace();
			logger.debug(time+":时间格式出错");
		}
		return null;
	}
	/**
	 * 用来计算两个时间相差的毫秒数
	 * @return time1-time2的毫秒数   条件必须是 time1>time2
	 * 2012-07-01 11:02:11 0014 
	 * 返回0表示,目前只实现一天内的时候差
	 */
	public static int diffOfTime(String time1,String time2){
		int diff=0;
		String times1[] = time1.split(" ");
		String times2[] = time2.split(" ");
		if(times1.length==3&&times2.length==3){
			if(times1[0].equals(times2[0])){         //必须是同一天
				String tt1[] = times1[1].split(":");
				String tt2[] = times2[1].split(":");
				if(tt1.length==3&&tt2.length==3){
					int tempHour = Integer.parseInt(tt1[0])-Integer.parseInt(tt2[0]);
					int tempMin = Integer.parseInt(tt1[1])-Integer.parseInt(tt2[1]);
					int tempSecond = Integer.parseInt(tt1[2])-Integer.parseInt(tt2[2]);
					diff+= ((tempHour*60+tempMin)*60+tempSecond)*1000;
				}else{
					logger.info("52:解析的时间格式不对");
				}
				diff+=Integer.parseInt(times1[2])-Integer.parseInt(times2[2]);
			}
		}else{
		logger.info("57:解析的时间格式不对");
		}
		return diff;
	}
}
