package ss.pku.re.rule.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.domain.BusinessEvent;
import ss.pku.re.rule.util.domain.Field;
import ss.pku.re.rule.util.domain.Rule;
import ss.pku.re.rule.util.domain.Scene;
import ss.pku.re.rule.util.domain.Scenes;
import ss.pku.re.rule.util.domain.Service;

public class XMLParser {
	private static final Iterator ruleIterator = null;

	/**
	 * parse the xml and create the drl files
	 * 
	 * @return
	 */

	// public static Scenes parseXML() {
	// ArrayList<Condition> conditions = new ArrayList<Condition>();
	// Condition condition = null;
	// SAXReader reader = new SAXReader();
	// try {
	// Document document = reader.read(new File("src/user_event_1.xml"));
	// List<Element> secondNodes = document.selectNodes("//result/event");
	// for (Element secondElement : secondNodes) {
	// condition = new Condition();
	// List<Element> thirdNodes = secondElement.elements();
	// Iterator it = thirdNodes.iterator();
	// while (it.hasNext()) {
	// Element thirdElement = (Element) it.next();
	// if (thirdElement.getName().equals("id")) {
	// condition.setKey(thirdElement.getText());
	// }
	// if (thirdElement.getName().equals("values")) {
	// List<Element> fourthNodes = thirdElement.elements();
	// Iterator iit = fourthNodes.iterator();
	// while (iit.hasNext()) {
	//
	// Element fourthElement = (Element) iit.next();
	// if (fourthElement.getName().equals("type")) {
	// condition.setType(fourthElement.getText());
	// }
	// if (fourthElement.getName().equals("max")) {
	// condition.setMax(fourthElement.getText());
	// }
	// if (fourthElement.getName().equals("min")) {
	// condition.setMin(fourthElement.getText());
	// }
	//
	// }
	// }
	//
	// }
	// conditions.add(condition);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return conditions;
	// }

	public static Scenes parseXML(String fileName) {

		SAXReader reader = new SAXReader();

		Scenes scenes = new Scenes();
		List<Scene> sceneList = new ArrayList<Scene>();
		List<Field> fieldList = null;
		List<Rule> ruleList = null;
		List<BusinessEvent> conditions = null;
		Scene scene = null;
		Field field = null;
		Rule rule = null;
		Service service = null;

		try {
			Document document = reader.read(new File(fileName));
			Element root = document.getRootElement();
			// 遍历Scene结点
			for (Iterator ite = root.elementIterator("Scene"); ite.hasNext();) {
			
				scene = new Scene();
				fieldList = new ArrayList<Field>();
				ruleList = new ArrayList<Rule>();

				Element sceneElement = (Element) ite.next();
				// Scene ID="scene1"
				String sceneID = sceneElement.attributeValue("ID");
				 
		
//					if(sceneID.equals(root.element("TransitionsDef").element("TransitionDef").element("To").element("SceneID").getText()))
//				{
//				continue;}
				// System.out.println(sceneID);
				// <Fields>
				Element fields = sceneElement.element("Fields");
				// <Field>
				for (Iterator fieldIterator = fields.elementIterator("Field"); fieldIterator.hasNext();) {
					Element fieldElement = (Element) fieldIterator.next();
					// Field下Name结点
					Element fieldNameElement = fieldElement.element("Name");
					// Field field=new Field();
					// field.setName(fieldNameElement.getText());
					// System.out.println(fieldNameElement.getName()+" : "+fieldNameElement.getText());
					field = new Field();
					field.setName(fieldNameElement.getText());
					fieldList.add(field);
				}
				// <Rules>
				Element rules = sceneElement.element("Rules");
				// <ruleID>
				for (Iterator ruleIterator = rules.elementIterator("ruleID"); ruleIterator
						.hasNext();) {
					Element ruleElement = (Element) ruleIterator.next();
					// System.out.println(ruleElement.getName()+" : "+ruleElement.getText());
					// 根据<ruleID>内容遍历<RulesDef>下<RuleDef ID="ruleId1"
					// name="name1">相应ID结点
					Element rulesDef = root.element("RulesDef");
					for (Iterator ruleDefIterator = rulesDef
							.elementIterator("RuleDef"); ruleDefIterator
							.hasNext();) {
						Element ruleDefElement = (Element) ruleDefIterator
								.next();
						// 判断<ruleID>内容与<RuleDef ID="ruleId1"
						// name="name1">ID属性值相同？
						if (ruleDefElement.attributeValue("ID").equals(
								ruleElement.getText())) {

							rule = new Rule();
							rule.setRuleId(ruleElement.getText());
							// 解析相应的RuleDef下内容type，condition，action
							// 解析name属性
							String ruleName = ruleDefElement
									.attributeValue("name");
							rule.setName(ruleName);
							// 解析type
							Element typeElement = ruleDefElement
									.element("type");
							// System.out.println(typeElement.getName()+" : "+typeElement.getText());
							rule.setType(Integer.parseInt(typeElement.getText()));
							// 解析condition
							Element conditionElement = ruleDefElement
									.element("Condition");
							conditions = new ArrayList<BusinessEvent>();
							for (Iterator businessEventElementIterator = conditionElement
									.elementIterator("BusinessEvent"); businessEventElementIterator
									.hasNext();) {

								BusinessEvent businessEvent = new BusinessEvent();
								Element businessEventElement = (Element) businessEventElementIterator
										.next();
								String businessEventID = businessEventElement
										.attributeValue("ID");
								// System.out.println(businessEventID);
								Element nameElement = businessEventElement
										.element("Name");
								// System.out.println(nameElement.getName()+" : "+nameElement.getText());
								Element valueElement = businessEventElement
										.element("Value");
								Element dimensionElement = valueElement
										.element("dimension");
								// System.out.println(dimensionElement.getName()+" : "+dimensionElement.getText());
								Element expressionElement = valueElement
										.element("expression");
								// System.out.println(expressionElement.getName()+" : "+expressionElement.getText());
								businessEvent.setEventId(businessEventID);
								businessEvent.setName(nameElement.getText());
								businessEvent.setDimension(Integer
										.parseInt(dimensionElement.getText()));
								businessEvent.setExpression(expressionElement
										.getText());
								System.out.println(expressionElement
										.getText());
								conditions.add(businessEvent);
								rule.setConditions(conditions);
							}
							// 解析Action
							Element actionElement = ruleDefElement
									.element("Action");
							// type=2
							if (typeElement.getText().equals("2")) {
								service = new Service();

								// 解析Action下Transition标签
								Element transitionElement = actionElement
										.element("Transition");
								Element transitionIDElement = transitionElement
										.element("TransitionID");
								// System.out.println(transitionIDElement.getName()+" : "+transitionIDElement.getText());
								// 根据<TransitionID>内容遍历<TransitionsDef>下<TransitionDef
								// ID="t1">相应ID结点
								Element transitionsDef = root
										.element("TransitionsDef");
								Element transitionDefElement = transitionsDef
										.element("TransitionDef");
								if (transitionDefElement.attributeValue("ID")
										.equals(transitionIDElement.getText())) {
									Element fromElement = transitionDefElement
											.element("From");
									Element fromSceneIDElement = fromElement
											.element("SceneID");
									// System.out.println(fromSceneIDElement.getName()+" : "+fromSceneIDElement.getText());
									Element toElement = transitionDefElement
											.element("To");
									Element toSceneIDElement = toElement
											.element("SceneID");
									// System.out.println(toSceneIDElement.getName()+" : "+toSceneIDElement.getText());
								}
								// 解析Action下Service标签
								Element serviceElement = actionElement
										.element("Service");
								Element serviceIDElement = serviceElement
										.element("ServiceID");
								// System.out.println(serviceIDElement.getName()+" : "+serviceIDElement.getText());
								// 根据<ServiceID>内容遍历<ServicesDef>下<Service
								// ID="s1" Name="空调">相应ID结点
								Element servicesDef = root
										.element("ServicesDef");
								Element serviceDefElement = servicesDef
										.element("ServiceDef");
								if (serviceDefElement.attributeValue("ID")
										.equals(serviceIDElement.getText())) {
									// System.out.println(serviceDefElement.attributeValue("ID"));
									String serviceName = serviceDefElement
											.attributeValue("Name");
									// System.out.println(serviceDefElement.attributeValue("Name"));
									Element typesElement = serviceDefElement
											.element("Types");
									Element messageElement = serviceDefElement
											.element("Message");
									Element portTypeElement = serviceDefElement
											.element("portType");
									Element operationElement = portTypeElement
											.element("operation");
									Element inputElement = operationElement
											.element("input");
									Element outputElement = operationElement
											.element("output");
									Element faultElement = operationElement
											.element("fault");
									Element serviceTypeElement = serviceDefElement
											.element("ServiceType");
									Element bindingElement = serviceDefElement
											.element("binding");
									service.setServiceId(serviceIDElement
											.getText());
									service.setName(serviceName);
									service.setValue(messageElement.getText());
									scenes.setServiceName(serviceName);
									rule.setSerivce(service);
								}
							} else if (typeElement.getText().equals("1")) {
								// type=1
								Event complexEvent = new Event();
								Element businessEventElement = actionElement
										.element("BusinessEvent");
								String businessEventID = businessEventElement
										.attributeValue("ID");
								// System.out.println(businessEventID);
								Element nameElement = businessEventElement
										.element("Name");
								// System.out.println(nameElement.getName()+
								// " : " + nameElement.getText());
								Element valueElement = businessEventElement
										.element("Value");
								// System.out.println(valueElement.getName()+" : "+valueElement.getText());
								complexEvent.setEventId(businessEventID);
//								complexEvent.setName(nameElement.getText());
								complexEvent.setValue(valueElement.getText());
								// complexEvent.set
								rule.setComplexEvent(complexEvent);
							}

						}
					}

					ruleList.add(rule);
				}

				scene.setSceneId(sceneID);
				scene.setFields(fieldList);
				scene.setRules(ruleList);
				sceneList.add(scene);
			}
			scenes.setScenes(sceneList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scenes;
	}

	public static void main(String[] args) {
		
		
		String fileName = "Model";
	File file=new File("D:\\test2\\例4_次数_发布.xml");
		 file=new File("D:\\test2\\例1_单值_发布.xml");
		file=new File("D:\\test2\\例2_组合_发布.xml");
		 file=new File("D:\\test2\\例3_多值_发布.xml");
		 file=new File("D:\\test2\\例5_时序_发布.xml");
		String m=file.getAbsolutePath();
		Scenes scenes = XMLParser.parseXML(m);
		System.out.println(file.getAbsolutePath());
		RulesBuilder builder = new RulesBuilder();
		builder.createRule(scenes,fileName);
		// List<Condition> conditions = XMLParser.parseXML();
		// for (Condition con : conditions) {
		// System.out.println(con.getKey() + " " + con.getType() + " "
		// + con.getMax() + " " + con.getMin());
		// }
		// System.out.println(scenes);

	}
}
