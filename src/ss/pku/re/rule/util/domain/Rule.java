package ss.pku.re.rule.util.domain;

import java.util.ArrayList;
import java.util.List;

import ss.pku.re.domain.Event;

/***
 * 对应XML文件中的<RuleDefs>
 * @author lqs
 *
 */
public class Rule {
	private String ruleId;
	private String name;
	private List<BusinessEvent> conditions = new ArrayList<BusinessEvent>();
	private Event complexEvent;
	/**1:生成复杂事件  2:调用服务*/
	private int type;
	private Service serivce;
	
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public List<BusinessEvent> getConditions() {
		return conditions;
	}
	public void setConditions(List<BusinessEvent> conditions) {
		this.conditions = conditions;
	}
	
	public Event getComplexEvent() {
		return complexEvent;
	}
	public void setComplexEvent(Event complexEvent) {
		this.complexEvent = complexEvent;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Service getSerivce() {
		return serivce;
	}
	public void setSerivce(Service serivce) {
		this.serivce = serivce;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
