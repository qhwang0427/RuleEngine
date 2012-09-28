package ss.pku.re.rule.util;

import java.io.File;
import java.util.Map;
import ss.pku.re.dao.ISubscribeDao;
import ss.pku.re.dao.SubscribeDao;
import ss.pku.re.domain.Subscribe;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;

import ss.pku.re.SubscribeToEvent.SubscribeProvider;
import ss.pku.re.domain.Subscribe;
import ss.pku.re.rule.util.domain.BusinessEvent;
import ss.pku.re.rule.util.domain.Rule;
import ss.pku.re.rule.util.domain.Scene;
import ss.pku.re.rule.util.domain.Scenes;

public class DynamicallyAddRules {
	//将指定的XML转换成DRL并添加到引擎中；
	private ISubscribeDao subscribeDao;
   public   void AddXml(String filepath){
	   if(!filepath.endsWith(".xml")){
		   System.out.println("文件格式不对");
	   }
	   else{
		   RulesBuilder builder = new RulesBuilder();
		   String filename=RuleEngineInit.RemoveAtEnd(filepath.substring(filepath.lastIndexOf("\\")+1, filepath.length()),".xml");
	   Scenes scenes = XMLParser.parseXML(filepath);
	   Map<String,StatefulKnowledgeSession> OnSessions =KnowledgeSessionManager.getInstance().getSessions();
	   if(OnSessions.isEmpty()){
		   System.out.println(filepath);
		   builder.createRule(scenes, filename);
		   KnowledgeSessionManager.getInstance().getSession(System.getProperty("user.dir")+"//"+RulesBuilder.BASERULEFILE+"//"+scenes.getServiceName());
		   System.out.println(KnowledgeSessionManager.getInstance().getSessions());
	   }
	   else if(!OnSessions.containsKey(System.getProperty("user.dir")+"//"+RulesBuilder.BASERULEFILE+"//"+scenes.getServiceName()))
	   {
		   builder.createRule(scenes, filename);
		   KnowledgeSessionManager.getInstance().getSession(System.getProperty("user.dir")+"//"+RulesBuilder.BASERULEFILE+"//"+scenes.getServiceName());
		   
	   }
	   else 
			   {
		   
      		
      		builder.createRule(scenes,filename);
      		System.out.println(builder.getFilePath());
      		StatefulKnowledgeSession seesion=OnSessions.get(System.getProperty("user.dir")+"//"+RulesBuilder.BASERULEFILE+"//"+scenes.getServiceName());
      		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
			.newKnowledgeBuilder();
	
		kbuilder.add(ResourceFactory.newFileResource(builder.getFilePath()),
			ResourceType.DRL);
	
	KnowledgeBuilderErrors errors = kbuilder.getErrors();
	if (errors.size() > 0) {
		for (KnowledgeBuilderError error : errors) {
			System.err.println(error);
		}
		throw new IllegalArgumentException("Could not parse knowledge.");
	}
      		seesion.getKnowledgeBase().addKnowledgePackages(kbuilder.getKnowledgePackages());
			   }
	   subscribeDao = (SubscribeDao)ContextFactory.getContext().getBean("subscribeDao");
	   for(Scene scene:scenes.getScenes()){
			for(Rule rule:scene.getRules()){
				for(BusinessEvent event:rule.getConditions()){
					if (!subscribeDao.hasSubscribe(event.getEventId(), scenes
							.getServiceName())) {    //当不存在记录的时候才进行订阅记录的新增
						Subscribe sub = new Subscribe(event.getEventId(),
								scenes.getServiceName());
						//保存订阅信息到数据库
						subscribeDao.save(sub);
						//连接DIA动态订阅,需要主题+传感器号,需要与DIA沟通完善。
						
							if(SubscribeProvider.getInstance().getWs()!=null){
							try {
								SubscribeProvider.getInstance().SubscriberByTopic("",event.getEventId());
							} catch (LoginFailure e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (MessageFormatError e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
			}
		}
	   
   }
	  
   
   }
}
