package ss.pku.re.test;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;

import ss.pku.re.dao.EventDao;
import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.rule.util.DateUtil;
import ss.pku.re.service.EventService;
import ss.pku.re.service.IEventService;
import ss.pku.re.service.IRuleService;
import ss.pku.re.service.RuleServiceByDrools;

/**
 * 用来测试时序问题
 * @author lqs
 *
 */
public class TestTimeSequence {
	private IRuleService service;
	public TestTimeSequence(){
		this.service = (RuleServiceByDrools)ContextFactory.getContext().getBean("ruleService");
	}
	//事件A到来时间 2012-07-01 11:21:12 0012
	//事件B到来时间 2012-07-01 11:21:12 0016 
//	@Test
//	public void testTS(){
//		//1. 来了一个A事件 A=31
//		Event event = new Event(1);
//		event.setEventId("event01");
//		event.setReceivedTime("2012-07-01 11:21:12 0016");
//		event.setValues(new double[]{11});
//		service.receiveEvent(event);
//		Event event3 = new Event(1);
//		event3.setEventId("event02");
//		event3.setReceivedTime("2012-07-01 11:21:12 0023");
//		event3.setValues(new double[]{32});
//		System.out.println(event3.checkTime(event.getReceivedTime(), 100,"<"));
//		
//		service.receiveEvent(event3);
//		
//		
//	}
	@Test
	public void testTimes(){
		Event event = new Event();
		event.setEventId("event01");
		String[] values = new String[1];
		values[0] = "11";
		
		event.setValue("01010101");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
		event.setReceivedTime(format.format(new Date()));
		event.setValues(values);
		Event event2 = new Event();
		
		
		values[0] = "12";
		ApplicationContext context = ContextFactory.getContext();
		IEventService t = (EventService)context.getBean("eventService");
		int i=t.getTimesByEvent(event,20);
		System.out.println(i);
		
		
	}
}
