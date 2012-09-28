package ss.pku.re.domain;

import java.io.Serializable;

public class Subscribe implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String eventId;
	private String engineId;
	private String topic;
	//必须要有默认构造函数
	public Subscribe(){}
	public Subscribe(String eventId,String engineId){
		this.eventId = eventId;
		this.engineId = engineId;
	}
	public Subscribe(String eventId,String engineId,String topic){
		this.eventId = eventId;
		this.engineId = engineId;
		this.topic=topic;
	}
	/*getter setter */
	public String getEventId() {
		return eventId;
	}
	public int getId() {
		return id;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEngineId() {
		return engineId;
	}
	public void setEngineId(String engineId) {
		this.engineId = engineId;
	}
}
