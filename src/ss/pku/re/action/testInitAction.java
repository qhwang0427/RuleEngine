package ss.pku.re.action;

import java.io.UnsupportedEncodingException;

import ss.pku.re.temporaryConnectForTest;
import ss.pku.re.SubscribeToEvent.ConnectForTest;
import ss.pku.re.SubscribeToEvent.IConnectDIA;
import ss.pku.re.rule.util.KnowledgeSessionManager;
import ss.pku.re.rule.util.RuleEngineInit;
import ss.pku.re.service.IRuleService;

import cn.edu.pku.ss.exception.LoginFailure;

import com.opensymphony.xwork2.ActionSupport;

public class testInitAction extends ActionSupport {

	private static final long serialVersionUID = 247156336542356006L;

static boolean dc=false;

	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String initEngine() throws UnsupportedEncodingException, LoginFailure {
		
		//path=new String(path.getBytes("iso-8859-1"),"utf-8");
		if(path.endsWith("\\")){path=path.substring(0,path.length()-1);}
	//    System.out.println(path);
	//	 path=path.replaceAll("\\\\", "/");
		 RuleEngineInit.RuleEnineIniter(path);
		 System.out.println(KnowledgeSessionManager.sessionManager.getSessions());
		
		return SUCCESS;
	}

}
