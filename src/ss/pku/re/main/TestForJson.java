package ss.pku.re.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;

import ss.pku.re.SubscribeToEvent.MessageParser;
import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.service.IRuleService;
import ss.pku.re.service.RuleServiceByDrools;

public class TestForJson {
	private static String host="127.0.0.1";
	private static int port=2222;
	public static ServerSocket ss;
public static void main(String args[]) throws IOException, NumberFormatException, ClassNotFoundException{
		
		try {
			ss=new ServerSocket(port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	  while(true){	
		  Socket mycs=ss.accept();
		JsonThread mythread=new JsonThread(mycs);
		mythread.start();
	  }
		
		
	     
	}
}
	class JsonThread extends Thread{
		private Socket cs;
		public JsonThread(Socket cs){
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
//			DataInputStream reader=new DataInputStream(socketIn);
			//	ApplicationContext context = ContextFactory.getContext();
				IRuleService ruleService  = (RuleServiceByDrools)ContextFactory.getContext().getBean("ruleService");
			//	ObjectInputStream oi = new ObjectInputStream(cs.getInputStream());
//		   	 double[] dvalues=null;
//		   	Event event=new Event(1);
//		   	 int i=2;
//		   	
//		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
//		   	char[] s=null;
//			 String[] a=null;
//		 String message=null;
//			 System.out.println("未开始");
//			    byte[] bs=new byte[40];
			String message=null; 
			   try {
				if((message=reader.readLine())!=null)
				   {
					System.out.println(message);
					MessageParser mp=new MessageParser();
					Event event =mp.MessageParserStringToEvent(message);
					
					System.out.println(event.getEventId()+"/t"+event.getDimension()+"/t"+event.getValues()[0]);
		    	 ruleService.receiveEvent(event);
				   }
				 //  message=message.replace("\\n", "").trim();
					   
//				      
//				    	 System.out.println("读取DIA数据"+message);
//				        
//				    	a=message.split(";");
//				    	 
//				    	Date date=new Date();
//				    	 event.setReceivedTime(sdf.format(date));
//				    	 dvalues=new double[a.length-1];
//				    	 event.setEventId(a[0]);
////			    	 System.out.println(a[1]);
//				    	 event.setDimension(a.length-1);
//				    	
//				    	 for(i=1;i<a.length;i++){
//				    		 dvalues[i-1]=Double.parseDouble(a[i]);
//				    		 System.out.println(Double.parseDouble(a[i]));
//				    	 
//				    	 }
//				    	 event.setValues(dvalues);
//				    	 
//				     
//				    	 System.out.println(event.getEventId()+"/t"+event.getDimension()+"/t"+event.getValues()[0]);
//				    	 ruleService.receiveEvent(event);
//				    	 message=null;
//				    	 try {
//							Thread.sleep(200);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//				     }
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
