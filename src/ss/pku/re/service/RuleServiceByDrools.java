package ss.pku.re.service;
 

import java.util.List;
import org.apache.log4j.Logger;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;
import ss.pku.re.dao.IEventDao;
import ss.pku.re.dao.ISubscribeDao;
import ss.pku.re.domain.Event;
import ss.pku.re.domain.Subscribe;
import ss.pku.re.rule.util.KnowledgeSessionManager;
import ss.pku.re.rule.util.RulesBuilder;

public class RuleServiceByDrools implements IRuleService{
	private StatefulKnowledgeSession session;
	private ISubscribeDao subscribeDao;
	private IEventDao eventDao;
	private Logger logger = Logger.getLogger(RuleServiceByDrools.class);
	public RuleServiceByDrools(){
//		session = KnowledgeSessionFactory.getSession("ss/pku/re/rule/test.drl","ss/pku/re/rule/FirstRule.drl");
	}
	@Override
	public void receiveEvent(Event event) {
//		System.out.println("room"+event.getRoomId()+" "+event.toString());
//		session.setGlobal("temp", new Temperature(12,12));
//		QueryResults result = session.getQueryResults(query,event.getRoomId());
//		for(QueryResultsRow pe:result){
//			session.retract(pe.getFactHandle("simpleEvent"));
//		}
		logger.info("接收事件-->事件Id:"+event.getEventId()+" 事件值:"+event.getValues()[0]+"  "+event.getReceivedTime());
		//先保存事件
		eventDao.save(event);
		//事件到了先获得规则引擎的id  一个事件可能属于多个规则引擎
		List<Subscribe> subscribeList = subscribeDao.getByEventId(event.getEventId());
		for(Subscribe sub:subscribeList){
			session = KnowledgeSessionManager.getInstance().getSession(System.getProperty("user.dir")+"//"+RulesBuilder.BASERULEFILE+"//"+sub.getEngineId());
			logger.info("调用规则引擎:"+sub.getEngineId());
			QueryResults result = session.getQueryResults(RulesBuilder.QUERY,event.getEventId());//查询引擎中有相同传感器ID的对象 Event;
	
				for(QueryResultsRow pe:result){
	//				Event a=new Event(1);
	//				a=(Event)pe.get("event");
	//			   System.out.println(a.getEventId());
					System.out.println(pe.getFactHandle("event").toString());
					
				session.retract(pe.getFactHandle("event"));//删除引擎里已经有的同个传感器的对象
				
			}
			//通过新的引用将对象插入引擎，因为session中会保留旧的引用的信息，所以使用旧的引用会引起seesion的识别问题
				System.out.println(event.getEventId()+"\t插入引擎");
				Event a=new Event();
				a.setDimension(event.getDimension());
				a.setEventId(event.getEventId());
				a.setValues(event.getValues());
				a.setReceivedTime(event.getReceivedTime());
		
			session.insert(a); 
			session.fireAllRules();//匹配所有的规则，如果满足规则，调用服务。
			session.dispose();
		
		}
		return;
	}
	@Override
	public QueryResults getQueryResults(String query) {
		return session.getQueryResults(query);
	}
	
	public StatefulKnowledgeSession getSession() {
		return session;
	}
	public void setSession(StatefulKnowledgeSession session) {
		this.session = session;
	}
	public void setSubscribeDao(ISubscribeDao subscribeDao) {
		this.subscribeDao = subscribeDao;
	}
	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}
}
