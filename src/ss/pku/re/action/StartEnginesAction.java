package ss.pku.re.action;

import ss.pku.re.SubscribeToEvent.ConnectForTest;
import ss.pku.re.SubscribeToEvent.IConnectDIA;
import ss.pku.re.rule.util.KnowledgeSessionManager;
import ss.pku.re.rule.util.RuleEngineInit;

import com.opensymphony.xwork2.ActionSupport;

public class StartEnginesAction extends ActionSupport {
	private String rulesUrl;
	private String tip;

	public String getRulesUrl() {
		return rulesUrl;
	}

	public void setRulesUrl(String rulesUrl) {
		this.rulesUrl = rulesUrl;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String execute() throws Exception {
		boolean bool = (null == rulesUrl);
//		setTip("null == rulesUrl? " + bool + "||rulesUrl=" + getRulesUrl());
		if (null != rulesUrl) {
			if(rulesUrl.endsWith("\\")){rulesUrl=rulesUrl.substring(0,rulesUrl.length()-1);}
			
				 RuleEngineInit.RuleEnineIniter(rulesUrl);
				 System.out.println(KnowledgeSessionManager.sessionManager.getSessions());
				
				
			return SUCCESS;
		} else
			return ERROR;
	}
}
