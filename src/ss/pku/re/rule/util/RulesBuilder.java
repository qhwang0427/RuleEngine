package ss.pku.re.rule.util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import ss.pku.re.SubscribeToEvent.SubscribeProvider;

import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;

import ss.pku.re.SubscribeToEvent.SubscribeProvider;
import ss.pku.re.dao.ISubscribeDao;
import ss.pku.re.dao.SubscribeDao;
import ss.pku.re.domain.Subscribe;
import ss.pku.re.main.RuleEngineMain;
import ss.pku.re.rule.util.domain.BusinessEvent;
import ss.pku.re.rule.util.domain.Rule;
import ss.pku.re.rule.util.domain.Scene;
import ss.pku.re.rule.util.domain.Scenes;
/**
 * 通过建模组XML解析出的对象进行规则的生成
 * @author lqs
 *
 */
public class RulesBuilder {
	public static int version=1;
	private String filePath;
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private StringBuffer buffer;
	private ISubscribeDao subscribeDao;
	public static final String BASERULEFILE = "rules";
	public static final String QUERY = "query fact";
	private Logger logger = Logger.getLogger(RulesBuilder.class);
	private static final String INITCONTEXT = "package rerules\n" +
			"import ss.pku.re.domain.Event;\n"+
			"import ss.pku.re.rule.util.domain.Service;\n"+
			"import ss.pku.re.rule.util.DIAServiceManager;\n"+
			"query '"+QUERY+"'(String id)\n"+                                     //定义Query来实现互斥
			"event:Event(eventId==id)\n"+
			"end\n";
	/*
	 * 传入参数：存储的文件名
	 */
	public RulesBuilder(){
		buffer = new StringBuffer(INITCONTEXT);
		subscribeDao = (SubscribeDao)ContextFactory.getContext().getBean("subscribeDao");
	}
	
	private void parseRule(Scene scene,Rule rule){
		String blueToothEvent_id = null;//added by qhwang
		buffer.append("rule'"+"rule_"+scene.getSceneId()+"_"+rule.getName()+"'\n");
		buffer.append("when"+"\n");
		for(BusinessEvent event:rule.getConditions()){
			String temp = event.getEventId();
			if(temp.matches("([0-9a-fA-F]{2}_){5}[0-9a-fA-F]{2}"))//added by qhwang判断该字符串是否为蓝牙地址（冒号被换成_了）
				blueToothEvent_id=temp;                  //added by qhwang
			buffer.append("e_"+temp+":Event(eventId.equals(");
			buffer.append("'"+event.getEventId()+"'");
			buffer.append(")");
			parseExpression(event.getExpression(),event);
			buffer.append("\n");
		}
		buffer.append("\nthen\n");
		//输出复杂事件的情况
		if(rule.getType()==1){
			buffer.append("Event event= new Event();\n");
			buffer.append("event.setEventId('"+rule.getComplexEvent().getEventId()+"');\n");
			buffer.append("event.setValue('"+rule.getComplexEvent().getValue()+"');\n");
			buffer.append("DIAServiceManager.sendEvent(event);\n end");
		}
		//FIXME：调用服务的情况  这边需要添加正确的调用service代码
		else if(rule.getType()==2){
			buffer.append("Service service= new Service();\n");
			System.out.print(rule.getSerivce().getServiceId());
			buffer.append("service.setServiceId('"+rule.getSerivce().getServiceId()+"');\n");
			buffer.append("service.setValue('"+rule.getSerivce().getValue()+"');\n");
			buffer.append("DIAServiceManager.sendService(service");
			if(null != blueToothEvent_id)       //用于在服务处理的时候传入有关手机蓝牙的事件，added by qhwang
				buffer.append(",e_"+blueToothEvent_id);//added by qhwang
			buffer.append(");\n end");
		}
		
		buffer.append("\n");
	}
	public void createRule(Scenes scenes,String fileName){
		List<Scene> sceneList = scenes.getScenes();
		for(Scene scene:sceneList){
			List<Rule> rules  = scene.getRules();
			for(Rule rule:rules){
				parseRule(scene,rule);
			}
		}
		this.filePath = System.getProperty("user.dir")+"//"+BASERULEFILE+"//"+scenes.getServiceName();
		File file = new File(filePath);
		if(!file.exists()&&!file.isDirectory()){
			boolean success = file.mkdirs();
			String msg = success?"创建文件夹"+scenes.getServiceName()+"成功":"创建文件夹"+scenes.getServiceName()+"失败";
			logger.info(msg);
		}
		String drlName = fileName+DateUtil.formatDate(new Date(), "yyyy-MM-dd"); //生成一个唯一标识的文件名:这里改成和建模组的XML文件名字+当前时间吧。
		filePath+="//"+drlName+".drl";
		//规则引擎自己维护的订阅表，用于事件的分区操作
		for(Scene scene:scenes.getScenes()){
			for(Rule rule:scene.getRules()){
				for(BusinessEvent event:rule.getConditions()){
					if (!subscribeDao.hasSubscribe(event.getEventId(), scenes
							.getServiceName())) {    //当不存在记录的时候才进行订阅记录的新增
						Subscribe sub = new Subscribe(event.getEventId(),
								scenes.getServiceName(),event.getName());
						//保存订阅信息到数据库
						subscribeDao.save(sub);
						//连接DIA动态订阅,需要主题+传感器号
						
							if(SubscribeProvider.getInstance().getWs()!=null){
							try {
								SubscribeProvider.getInstance().SubscriberByTopic(event.getName(),event.getEventId());
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
		writeRule();
	}
	/**这一段用来解析那个表达式*/
	/**values[0]>=31.12;values[0]<=32.11;values[1]<10.11;values[1]>=9.11;values[2]<88.09;values[2]>87.33;times=1;*/
	private void parseExpression(String expression,BusinessEvent event){
		String[] expressions = expression.split(";");
		for(String e:expressions){
			/**如果是值的约束*/
			if(e.startsWith("values") || e.startsWith("!values")){
				if(e.contains("equals"))
				buffer.append(","+e);
				else{
			
					StringBuilder ts=new StringBuilder(e);
					ts.insert(ts.indexOf("]")+1,")");
					String ms=ts.toString();
					buffer.append(",Double.parseDouble("+ms);
					}
				System.out.println(e);
			}
			/**发生次数的约束*/
			else if(e.startsWith("times")){
				if(e.contains(",")){
					String  tl[] = e.split(",");
				    
				     String[] times=tl[0].split("==");
				 	
						if(times.length>=2){
							try{
								//发生的次数
								int time = Integer.parseInt(times[1]);
								//当time>1的时候才需要进行添加代码
								if(time>1){
									
							String[] limit=tl[1].split("==");
							    int limitTime=Integer.parseInt(limit[1]);
										buffer.append(",getTimes("+time+","+limitTime+")=="+time);
										
								}
							}catch(NumberFormatException exception){
								System.out.println("XML 内容解析有误:事件发生的次数不为数字 "+ times[1]);
							}
						}
					
					}
				else{String times[] = e.split("=");
				
				
				
				if(times.length>=2){
					try{
						//发生的次数
						int time = Integer.parseInt(times[1]);
						//当time>1的时候才需要进行添加代码
						if(time>1){
							
							
								buffer.append(",getTimes("+time+")=="+time);
								
						}
					}catch(NumberFormatException exception){
						System.out.println("XML 内容解析有误:事件发生的次数不为数字 "+ times[1]);
					}
				}
				
			}
			}
			/**时序的约束*/
			else if(e.startsWith(event.getEventId())){
				String eventTimes[] = e.split("-");
				if(eventTimes.length>=2){
					String event2[] = eventTimes[1].split("<|>|<=|>=|=");
					if(event2.length>=2){
						String punctuation = eventTimes[1].replaceAll(event2[0]+"|"+event2[1], "");
						try{
							double constraint = Double.parseDouble(event2[1]);
							buffer.append(",checkTime(e_"+event2[0]+".getReceivedTime(),"+constraint+",'"+punctuation+"')==true");
						}catch(NumberFormatException exception){
							logger.error("XML内容解析有误:时序的限制不为数字 "+event2[1]);
						}
						
					}
				}
			}
		}
		buffer.append(")");
	}
	
	public boolean writeRule(){
		try {
			System.out.println(filePath);
			FileWriter writer = new FileWriter(new File(filePath));
			
			/*init the drl file*/
			writer.write(buffer.toString());
			writer.close();
		} catch (IOException e) {
			System.out.println("文件没有找到");
			return false;
		}
		return true;
	}
	
	/*public static void main(String args[]){
		String s = "00_0E_A5_00_50_96";
		System.out.println(s.matches("([0-9a-fA-F]{2}_){5}[0-9a-fA-F]{2}"));//判断该字符串是否为蓝牙地址（冒号被换成_了）
	}*/
}
