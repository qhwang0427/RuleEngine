package ss.pku.re.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.apache.log4j.Logger;

import ss.pku.re.bean.EngineRecord;
import ss.pku.re.rule.util.KnowledgeSessionManager;

import com.opensymphony.xwork2.ActionSupport;

public class ManageEnginesAction extends ActionSupport {

	private static final long serialVersionUID = -9111550663831454464L;
	private String select;
	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}


	// 获取Log4j日志输出
	Logger logger = Logger.getLogger(this.getClass());

	private ArrayList<EngineRecord> list;

	public ArrayList<EngineRecord> getList() {
		return list;
	}

	public void setList(ArrayList<EngineRecord> list) {
		this.list = list;
	}

	public String execute() throws Exception {
		logger.warn("进入到ManageEnginesAction.execute()");
		logger.warn(getEngineRecords().toString());
		// 获得引擎的列表信息
		
		setList(getEngineRecords());
		return SUCCESS;
	}
	public String pauseEngines() throws Exception {
		logger.warn("进入到ManageEnginesAction.pauseEngines()");
		String[] chkValues = this.getSelect().split(", ");
		logger.warn("chkValues=" + Arrays.toString(chkValues));
		return SUCCESS;
	}
	/**
	 * 使用method指定的处理方法，获取页面选定的复选框，恢复运行相应的规则引擎
	 * @return
	 * @throws Exception
	 */
	public String resumeEngines() throws Exception {
		logger.warn("进入到ManageEnginesAction.resumeEngines()");
		String[] chkValues = this.getSelect().split(", ");
		logger.warn("chkValues=" + Arrays.toString(chkValues));
		return SUCCESS;
	}


	private ArrayList<EngineRecord> getEngineRecords() {
		ArrayList<EngineRecord> arrayList = new ArrayList<EngineRecord>();
		Set<String> Engines=KnowledgeSessionManager.getInstance().getSessions().keySet();
		int i=0;
		for(String k : Engines) {
			
		
			EngineRecord record = new EngineRecord();
			record.setId("ID00" + i);
			record.setName(k.substring(k.lastIndexOf("/")+1,k.length()));
			record.setState("运行");
			arrayList.add(record);
			i++;
		}
		return arrayList;
	}
}
