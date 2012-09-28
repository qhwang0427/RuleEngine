package ss.pku.re.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.edu.pku.ss.exception.LoginFailure;

import ss.pku.re.SubscribeToEvent.SubscribeProvider;
import ss.pku.re.domain.Event;
import ss.pku.re.main.RuleEngineMain;
import ss.pku.re.rule.util.RulesBuilder;
import ss.pku.re.rule.util.domain.BusinessEvent;
import ss.pku.re.rule.util.domain.Rule;
import ss.pku.re.rule.util.domain.Scene;
import ss.pku.re.rule.util.domain.Scenes;
public class TestParser {
	private RulesBuilder builder;
	public TestParser(){
		System.out.println(System.getProperty("user.dir"));
		builder = new RulesBuilder();
	}
	@Test
	public void testBuildRule1(){
//		RuleEngineMain.ep = new SubscribeProvider("ws://localhost:8080/DIAServer/core-socket","wilson","1234");//new EventProcessor("ws://localhost:8080/DIAServer/core-socket","wilson","1234");
//		try {
//			RuleEngineMain.ep.init();
//		} catch (LoginFailure e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Scenes scenes = new Scenes();
		scenes.setServiceName("AirCondition");
		Scene scene = new Scene();
		Rule rule = new Rule();
		BusinessEvent event = new BusinessEvent();
		event.setEventId("e12");
		event.setExpression("values[0]>=31.12");
		List<BusinessEvent> eventList = new ArrayList<BusinessEvent>();
		eventList.add(event);
		rule.setConditions(eventList);
		rule.setName("rule01");
		rule.setType(1);
		Event complex = new Event();
		complex.setEventId("compId");
		complex.setValue("01010101010");
		rule.setComplexEvent(complex);
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(rule);
		scene.setRules(rules);
		List<Scene> sceneList = new ArrayList<Scene>();
		sceneList.add(scene);
		scenes.setScenes(sceneList);
		builder.createRule(scenes,"Test");
	}
	
  // @Test
	public void testBuildRule2(){
		Scenes scenes = new Scenes();
		scenes.setServiceName("AirCondition");
		Scene scene = new Scene();
		Rule rule = new Rule();
		BusinessEvent event = new BusinessEvent();
		event.setEventId("event02");
		event.setExpression("values[0]>=31.12;values[0]<=32.11;times=1");
		BusinessEvent event2 = new BusinessEvent();
		event2.setEventId("event03");
		event2.setExpression("values[0]>=12;values[0]<=13");
		List<BusinessEvent> eventList = new ArrayList<BusinessEvent>();
		eventList.add(event);
		eventList.add(event2);
		rule.setConditions(eventList);
		rule.setName("rule01");
		rule.setType(1);
		Event complex = new Event();
		complex.setEventId("08");
		complex.setValue("01010101010");
		rule.setComplexEvent(complex);
		List<Rule> rules = new ArrayList<Rule>();
		rules.add(rule);
		scene.setRules(rules);
		List<Scene> sceneList = new ArrayList<Scene>();
		sceneList.add(scene);
		scenes.setScenes(sceneList);
		builder.createRule(scenes,"Test2");
	}
}
