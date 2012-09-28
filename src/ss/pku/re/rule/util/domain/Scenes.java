package ss.pku.re.rule.util.domain;

import java.util.List;

/**
 * 对应XML中的<Scenes> 里面包含多个规则
 * @author lqs
 *
 */
public class Scenes {
	private List<Scene> scenes;
	/** 一个*/
	private String serviceName;
	
	
	public List<Scene> getScenes() {
		return scenes;
	}

	public void setScenes(List<Scene> scenes) {
		this.scenes = scenes;
	}

	public String getServiceName() {
		if(serviceName!=null)
			return serviceName;
		return "其他";
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
}
