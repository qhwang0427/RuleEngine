package ss.pku.re.SubscribeToEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;

import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.service.IRuleService;
import ss.pku.re.service.RuleServiceByDrools;
import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;
//该类用于测试，真正用于连接DIA的是实现IConnectDIA接口的另一个类SubscribeProvider;
public class ConnectForTest implements IConnectDIA {

	@Override
	public boolean SubscriberByTopic(String topic, String SensorID)
			throws LoginFailure, MessageFormatError {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	private static String host="127.0.0.1";
	private static int port=2222;
	public static Socket cs;
	@Override
	public void init(String p, String username, String password){
		
		try {
			cs=new Socket("127.0.0.1",port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	 
		 
		ConnectThread mythread=new ConnectThread(cs);
		mythread.start();
	  }
	@Override
	public void SubscribeAllEvent() {
		// TODO Auto-generated method stub
		
	}
	
	
		
		
	     
	
}
	class ConnectThread extends Thread{
		private Socket cs;
		public ConnectThread(Socket cs){
			this.cs=cs;
			
		}
		public void run(){
			InputStream socketIn = null;
			try {
				socketIn = cs.getInputStream();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BufferedReader reader=new BufferedReader(new InputStreamReader(socketIn));

				IRuleService ruleService  = (RuleServiceByDrools)ContextFactory.getContext().getBean("ruleService");
			
		  
		   	Event event;
		
		   	
	//	   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
		   	
			 
		 String message=null;
			 System.out.println("开始监听");
			  
			 
			   try {
				while((message=reader.readLine())!=null)
				   {
					System.out.println(message);
					MessageParser mp=new MessageParser();
					 event =mp.MessageParserStringToEvent(message);
					
					System.out.println(event.getEventId()+"\t"+event.getDimension()+"\t");
		    	 ruleService.receiveEvent(event);
				System.out.println("处理结束");
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
