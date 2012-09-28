package ss.pku.re.rule.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import ss.pku.re.bean.EngineRecord;
/***
 * 用来处理并行处理的情况
 * 主要实现一个功能
 * 传入一个文件名->用来获取一个session
 * @author lqs
 *
 */
public class KnowledgeSessionManager {
	private  Map<String,StatefulKnowledgeSession> sessions; 
	public static KnowledgeSessionManager sessionManager = new KnowledgeSessionManager();
	
	private KnowledgeSessionManager() {
		sessions = new HashMap<String,StatefulKnowledgeSession>();
	}

	public static KnowledgeSessionManager getInstance(){
		return sessionManager;
	}

	
	//适用于从drl文件中生成规则的情况
	public Map<String, StatefulKnowledgeSession> getSessions() {
		return sessions;
	}

	public void setSessions(Map<String, StatefulKnowledgeSession> sessions) {
		this.sessions = sessions;
	}
	
	
	/**
	 * 传入一个文件名返回一个session
	 * 文件名中保存着所有session中需要的drl文件
	 * @param drls
	 * @return
	 * @throws Exception 
	 */
	public StatefulKnowledgeSession getSession(String fileName){
		if(sessions.containsKey(fileName))
		{
	//		System.out.println(fileName);
			return sessions.get(fileName);
		}
		else{
//			String name = System.getProperty("user.dir")+"\\rules\\"+fileName;
			File file = new File(fileName);
			if(file.isDirectory()){
				List<String> fileNames = new ArrayList<String>();
				String files[] = file.list();
				for(int i=0; i<files.length; i++){
					if(files[i].endsWith(".drl")){
						files[i] =fileName+"//"+files[i];
						fileNames.add(files[i]);
					}
				}
				String names[] = new String[fileNames.size()];
				for(int i=0; i<fileNames.size(); i++)
					names[i] = fileNames.get(i);
				StatefulKnowledgeSession session = readKnowledgeBase(names).newStatefulKnowledgeSession();//
				sessions.put(fileName, session);
				return session;
			}
		}
		return null;
	}
	/**
	 * 写点注释。。
	 * @param drls
	 * @throws Exception
	 */
	private static KnowledgeBase readKnowledgeBase(String...drls){
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();//
		for(String drl:drls){
			kbuilder.add(ResourceFactory.newFileResource(drl),
				ResourceType.DRL);//
		}
		KnowledgeBuilderErrors errors = kbuilder.getErrors();//
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kBase = KnowledgeBaseFactory.newKnowledgeBase();//
		kBase.addKnowledgePackages(kbuilder.getKnowledgePackages());//
		return kBase;
	}
	
	
}
