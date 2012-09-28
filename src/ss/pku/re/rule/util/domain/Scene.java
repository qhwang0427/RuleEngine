package ss.pku.re.rule.util.domain;

import java.util.List;

/**
 * 这个表示一个场景，后续补充
 * @author lqs
 *
 */
public class Scene {
	private String sceneId;
	private List<Field> fields;
	private List<Rule> rules;
	public String getSceneId() {
		return sceneId;
	}
	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public List<Rule> getRules() {
		return rules;
	}
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
}	
