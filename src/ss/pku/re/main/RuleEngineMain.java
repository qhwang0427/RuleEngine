package ss.pku.re.main;

import java.util.ArrayList;
import java.util.List;

import jxl.common.Logger;

import org.drools.rule.builder.RuleBuilder;
import org.springframework.context.ApplicationContext;

import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;

import ss.pku.re.SubscribeToEvent.SubscribeProvider;
import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.rule.util.RulesBuilder;
import ss.pku.re.rule.util.domain.BusinessEvent;
import ss.pku.re.rule.util.domain.Rule;
import ss.pku.re.rule.util.domain.Scene;
import ss.pku.re.rule.util.domain.Scenes;
import ss.pku.re.service.IRuleService;

public class RuleEngineMain {
	
	private static Logger logger = Logger.getLogger(RuleEngineMain.class);
	public static SubscribeProvider ep=null;
	public static void main(String args[]){
		logger.info("开始启动规则引擎");	
			
		SubscribeProvider ep=SubscribeProvider.getInstance();
		//	 ep = new SubscribeProvider("ws://localhost:8080/DIAServer/core-socket","wilson","1234");//new EventProcessor("ws://localhost:8080/DIAServer/core-socket","wilson","1234");
			//EventProcessor ep = new EventProcessor("ws://localhost:8080/DIAServer/core-socket","wilson","1234");//new EventProcessor("ws://localhost:8080/DIAServer/core-socket","wilson","1234");
			try {
				ep.init("ws://localhost:8080/DIAServer/core-socket","wilson","1234");
				try {
					ep.SubscriberByTopic("Temperature","s");
				} catch (MessageFormatError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (LoginFailure e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
//			
//					Scenes scenes = new Scenes();
//					scenes.setServiceName("AirCondition");
//					Scene scene = new Scene();
//					Rule rule = new Rule();
//					BusinessEvent event = new BusinessEvent();
//					event.setEventId("12");
//					event.setExpression("values[0]>=31.12");
//					List<BusinessEvent> eventList = new ArrayList<BusinessEvent>();
//					eventList.add(event);
//					rule.setConditions(eventList);
//					rule.setName("rule01");
//					rule.setType(1);
//					Event complex = new Event();
//					complex.setEventId("08");
//					complex.setValue("0800002$");
//					rule.setComplexEvent(complex);
//					List<Rule> rules = new ArrayList<Rule>();
//					rules.add(rule);
//					scene.setRules(rules);
//					List<Scene> sceneList = new ArrayList<Scene>();
//					sceneList.add(scene);
//					scenes.setScenes(sceneList);
//					RulesBuilder builder=new RulesBuilder();
//					builder.createRule(scenes,"Test");
		
		
	}
}

