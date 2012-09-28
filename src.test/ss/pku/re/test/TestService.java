package ss.pku.re.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.service.IRuleService;
import ss.pku.re.service.RuleServiceByDrools;

public class TestService {
	private IRuleService service;
	public TestService(){
		this.service = (RuleServiceByDrools)ContextFactory.getContext().getBean("ruleService");
	}
	@Test
	public void testReceiveEvent1(){
//		StatefulKnowledgeSession session1 = sessionManager.getSession("test");
//		StatefulKnowledgeSession session2 = sessionManager.getSession("test1");
//		testSession1(session1);
//		testSession2(session2);
	}
	@Test
	public void testSession2(){
		Event event = new Event();
		event.setEventId("event01");
		double[] values = new double[1];
		values[0] = Double.parseDouble("11");
		
		event.setValue("01010101");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
		event.setReceivedTime(format.format(new Date()));
		event.setValues(values);
		Event event2 = new Event();
		event2.setEventId("event03");
		double[] values2 = new double[1];
		values[0] = 12;
		
		event2.setValue("01010101");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
		event2.setReceivedTime(format2.format(new Date()));
		event2.setValues(values);
		
		service.receiveEvent(event);
		service.receiveEvent(event2);
	}
}	

