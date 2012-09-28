package ss.pku.re.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.service.IRuleService;
import ss.pku.re.service.RuleServiceByDrools;

/**
 * 用来测试互斥现象
 * @author lqs
 *
 */
public class TestMutual {
	private IRuleService service;
	public TestMutual(){
		this.service = (RuleServiceByDrools)ContextFactory.getContext().getBean("ruleService");
	}
	// A>=31.12&A<32.11&B>=12,B<=13的时候触发事件。 
	@Test
	public void testMutual1() throws InterruptedException{
		//1. 来了一个A事件 A=31
		Event event = new Event(1);
		event.setEventId("event02");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		event.setReceivedTime(format.format(new Date()));
		event.setValues(new double[]{32});
		service.receiveEvent(event);
		Thread.sleep(6000);
//		//2. 又来了个A事件 A=33
//		Event event2 = new Event(1);
//		event2.setEventId("event02");
//		event2.setReceivedTime(format.format(new Date()));
//		event2.setValues(new double[]{31});
//		service.receiveEvent(event2);
		//3. 来了个B事件   B = 12.5 看是否触发事件
		Event event3 = new Event(1);
		event3.setEventId("event02");
		event3.setReceivedTime(format.format(new Date()));
		event3.setValues(new double[]{32});
		service.receiveEvent(event3);
		Event event4 = new Event(1);
		event4.setEventId("event03");
		event4.setReceivedTime(format.format(new Date()));
		event4.setValues(new double[]{32});
		service.receiveEvent(event4);
		
	}
}
