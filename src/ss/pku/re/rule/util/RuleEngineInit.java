package ss.pku.re.rule.util;

import java.io.File;

import org.drools.runtime.StatefulKnowledgeSession;

import ss.pku.re.rule.util.domain.Scenes;

public class RuleEngineInit {
	//根据引擎文件夹初始化引擎
	public static void RuleEnineIniter(String filepath){
		//从根文件遍历存放XML的所有文件，查找所有定义规则的XML并解析成DRL
		
		File dir = new File(filepath);
		XmlToDrl(dir);
		DrlToRuleEngine();
		
	}
	//订阅初试化，根据MySql订阅表向DIA发送订阅信息
	public static void SubscribeInit(){}
	
    public static void XmlToDrl(File file){
    	  
     
                    //用递归方法获取所有后缀为.XML的文件
          if(file.isDirectory()){
           File files[] = file.listFiles(); //获得目录下所有文件  
           
           for (int i = 0; i < files.length; i++)  
           {  
             //取所有后缀为.XML的文件
               if(files[i].getName().endsWith(".xml")){  
            	   Scenes scenes = XMLParser.parseXML(file.getAbsolutePath()+"/"+files[i].getName());
            	System.out.println(scenes.getServiceName());
           		RulesBuilder builder = new RulesBuilder();
           		String filename=RemoveAtEnd(files[i].getName(),".xml");
           		builder.createRule(scenes,filename);
               }  
                 
               if (files[i].isDirectory())  //用递归列出子目录  
               {  
                   XmlToDrl(files[i]);  
               }  
           }  }
         
    		//将所有drl文件打包放入引擎
    }
    public static void DrlToRuleEngine(){
    	File drlFile=new File(System.getProperty("user.dir")+"//"+RulesBuilder.BASERULEFILE);
    	String[] filenames=drlFile.list();
    	for(int i=0;i<filenames.length;i++)
    	{
       KnowledgeSessionManager.getInstance().getSession(System.getProperty("user.dir")+"//"+RulesBuilder.BASERULEFILE+"//"+filenames[i]);
    	}
    }
    
    //去掉尾部文件类型标示符
    public static String RemoveAtEnd(String sourceString,String deletingString)
    {
      try{
             if(sourceString.indexOf(deletingString)<0)
                {throw new Exception("文件不属于XML类型");}
              String tmpString=sourceString;
              int len1=sourceString.length();
               int len2=deletingString.length();
             int deletePos=len1-len2;
            String tempSubString=sourceString.substring(deletePos);
              if(tempSubString.toUpperCase()==deletingString.toUpperCase());
              { tmpString=sourceString.substring(0,deletePos);
              }
                
                 return tmpString;

           }
     catch(Exception e){
       
    	 return sourceString;
         }


    }
}
