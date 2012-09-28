package ss.pku.re.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import ss.pku.re.dao.EventDao;
import ss.pku.re.dao.IEventDao;
import ss.pku.re.dao.ISubscribeDao;
import ss.pku.re.dao.SubscribeDao;
import ss.pku.re.domain.Event;
import ss.pku.re.domain.Subscribe;
import ss.pku.re.service.EventService;
import ss.pku.re.service.IEventService;

public class TestEventDao {
	
	private IEventDao eventDao;
	private IEventService eventService;
	private ISubscribeDao subscribeDao;
	public TestEventDao(){
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		eventDao = (EventDao)context.getBean("eventDao");
		eventService = (EventService) context.getBean("eventService");
		subscribeDao = (SubscribeDao) context.getBean("subscribeDao");
	}
//	@Test
//	public void testSave(){
//		Event event = new Event();
//		event.setEventId("12");
//		event.setValue("eventVallue");
//		event.setReceivedTime("2012-06-07");
//		eventDao.save(event);
//	}
//	@Test
//	public void testGetTimes(){
//		Event event = eventDao.findEventById(1);
//		System.out.println(eventService.getTimesByEvent(event, 5));
//	}
//	@Test
//	public void testSaveSubscribe(){
//		Subscribe sb = new Subscribe("空调","12");
//		subscribeDao.save(sb);
//	}
//	@Test
//	public void testHasSubscribe(){
//		Subscribe sb = subscribeDao.getById(1);
//		boolean test = subscribeDao.hasSubscribe(sb.getEngineId(), sb.getEventId());
//		System.out.println(test);
//	}
	@Test
	public void testselectall(){
		List<Subscribe> sl=(List<Subscribe>)subscribeDao.getAll();
		if(sl!=null&&sl.size()>0)
		{
		   for(int i=0;i<sl.size();i++){
			   
			   System.out.println(sl.get(i).getTopic()+"-"+sl.get(i).getEventId()+"-"+sl.get(i).getEngineId());
		   }
		}
	}
}
