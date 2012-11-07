package ss.pku.re;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.service.IRuleService;
import ss.pku.re.service.RuleServiceByDrools;


public class temporaryConnectForTest extends Thread {
	private static String host="127.0.0.1";
	private static int port=2222;
	public static Socket cs;
	public void run(){
		
		try {
			cs=new Socket(host,port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream socketIn = null;
		try {
			socketIn = cs.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader reader=new BufferedReader(new InputStreamReader(socketIn));
//	DataInputStream reader=new DataInputStream(socketIn);
	//	ApplicationContext context = ContextFactory.getContext();
		IRuleService ruleService  = (RuleServiceByDrools)ContextFactory.getContext().getBean("ruleService");
		
		String[] dvalues=null;
   	Event event=new Event(1);
   	 int i=2;
   	
   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
	 String[] a=null;
 String message=null;
	 System.out.println("未开始");
	    
	 
	   try {
		while(!((message = reader.readLine())== null))
		   {
	//		 message=message.replace("\\n", "").trim();
		    
		    	 System.out.println("读取DIA数据");
		        
		    	a=message.split(",");
		    	 
		    	Date date=new Date();
		    	 event.setReceivedTime(sdf.format(date));
		    	 dvalues=new String[a.length-1];
		    	 event.setEventId(a[0]);
		    	 event.setDimension(Integer.parseInt(a[1]));
		    	 System.out.println(event.getDimension());
		    	 if(event.getDimension()==2){
		    	 for(i=2;i<a.length;i++){
		    		 dvalues[i-2]=a[i];
		    		 System.out.println(Double.parseDouble(a[i]));
		    	 }
		    	 
		    	 event.setValues(dvalues);
		    	 }
		    	 else if(event.getDimension()==1)
		    	 {
		    		 event.setValue(a[2]);
		    		 
		    	 }
		     
		    	 System.out.println(event.getEventId()+"/t"+event.getDimension()+"/t"+event.getValues()[0]);
		    	 ruleService.receiveEvent(event);
		    	 message=null;
		    	 try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     }
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	     
	}
}
