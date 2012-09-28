package ss.pku.re.rule.util.domain;
/**
 * 封装服务的对象，字段还不全，后续补充
 * @author lqs
 *
 */
public class Service {
	/**通过这个进行分引擎操作*/
	//service id 唯一标识一个service
	private String serviceId;
	private String name;
	private String value;
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
