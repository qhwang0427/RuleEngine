package ss.pku.re.domain;

import java.io.Serializable;
import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.rule.util.DateUtil;
import ss.pku.re.service.EventService;
import ss.pku.re.service.IEventService;
/**
 * 标识一个DIA中传入的事件
 * @author lqs
 *
 */
public class Event implements Serializable{
		
	private static final long serialVersionUID = 1L;
	private int id;
	private String eventId;
	private String value;
	private int dimension;
	private String values[];
	private String receivedTime;
	private int times;
	private Logger logger = Logger.getLogger(Event.class);
	public enum Punctuation{
		BG(">"),
		BE(">="),
		EQ("="),
		LT("<"),
		LE("<=");
		private String name;
		Punctuation(String name){
			this.name = name;
		}
		public String getName(){
			return name;
		}
	}

	//必须有默认构造函数
	public Event(){}
	
	public Event(int dimension){
		values = new String[dimension];
	}
	
	/*getter setter */
	/**
	 *timeLimit规定的时间内事件发生的次数
	 */
	public int getTimes(int count,int timeLimit) {
		ApplicationContext context = ContextFactory.getContext();
		IEventService eventService = (EventService)context.getBean("eventService");
		int nums = eventService.getTimesByEvent(this, count,timeLimit);
		logger.info("事件Id:"+eventId+" 连续发生的次数"+nums);
		return nums;
	}
	//统计该事件总共发生几次
   public int getTimes(int count){
	   
	   ApplicationContext context = ContextFactory.getContext();
		IEventService eventService = (EventService)context.getBean("eventService");
		int nums = eventService.getTimesByEvent(this, count);
		System.out.println(nums);
		logger.info("事件Id:"+eventId+" 连续发生的次数"+nums);
		return nums;
   }
	/**
	 * 用来判断时序
	 * @param time
	 * @param constraint   由于DIA中可以支持毫秒级别,所有这里constraint的单位是毫秒
	 * 	这边可以暂时可以支持的符号： >=  <=  =  > <
	 * @return  true:符合规则, false: 不符合
	 */
	public boolean checkTime(String time,double constraint,String punctuation){

		System.out.println(time);
		int result = DateUtil.diffOfTime(this.receivedTime, time);
		System.out.println(result);
		
		
		double tempResult = Math.abs(result) - constraint;             //2. 跟约束进行比较
		if(result<0)   // 3. 当事件A-事件B<0 显然不成立
			return false;
		else if(result>=0&&tempResult<0){
			if(punctuation.equals(Punctuation.LE.getName())||punctuation.equals(Punctuation.LT.getName()))
				return true;
		}else if(result>=0&&tempResult>0){
			if(punctuation.equals(Punctuation.BE.getName())||punctuation.equals(Punctuation.BG.getName()))
				return true;
		}else if(result>=0&&tempResult==0&&punctuation.equals(Punctuation.EQ.getName()))
			    return true;
		return false;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(String receivedTime) {
		this.receivedTime = receivedTime;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}

	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}
	
}
