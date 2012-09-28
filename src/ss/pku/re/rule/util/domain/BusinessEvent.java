package ss.pku.re.rule.util.domain;
/**
 * 对应XML文件中的<BusinessEvent>
 * @author lqs
 *
 */
public class BusinessEvent {
	/**这边必须和DIA相对应*/
	private String eventId;
	private String name;
	private int dimension;
	/**这个字段是关键，在RulesBuilde中进行解析*/
	private String expression;
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDimension() {
		return dimension;
	}
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
}
