package ss.pku.re.action;

import org.apache.log4j.Logger;

import ss.pku.re.rule.util.DynamicallyAddRules;
import ss.pku.re.rule.util.RuleEngineInit;

import com.opensymphony.xwork2.ActionSupport;

public class SingleXmlAction extends ActionSupport {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3049668223187653868L;
    private String SXmlPath;
    public String getSXmlPath() {
		return SXmlPath;
	}
    Logger logger = Logger.getLogger(this.getClass());
	public void setSXmlPath(String sXmlPath) {
		SXmlPath = sXmlPath;
	}
	public String execute() throws Exception {
		logger.warn(SXmlPath);
	//	String filename=RuleEngineInit.RemoveAtEnd(SXmlPath.substring(SXmlPath.lastIndexOf("\\")+1, SXmlPath.length()),".xml");
		DynamicallyAddRules ad=new DynamicallyAddRules();
		ad.AddXml(SXmlPath);
    	
		return SUCCESS;
    }
}
