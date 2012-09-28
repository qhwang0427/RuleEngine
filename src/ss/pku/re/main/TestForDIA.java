package ss.pku.re.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

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

public class TestForDIA {
	private static String host="127.0.0.1";
	private static int port=2222;
	public static Socket cs;
	
	public static void main(String args[]) throws IOException, NumberFormatException, ClassNotFoundException{
		
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
//			DataInputStream reader=new DataInputStream(socketIn);
			//	ApplicationContext context = ContextFactory.getContext();
				IRuleService ruleService  = (RuleServiceByDrools)ContextFactory.getContext().getBean("ruleService");
			//	ObjectInputStream oi = new ObjectInputStream(cs.getInputStream());
		   	 double[] dvalues=null;
		   	Event event=new Event(1);
		   	 int i=2;
		   	
		   	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
		   	char[] s=null;
			 String[] a=null;
		 String message=null;
			 System.out.println("未开始");
			    byte[] bs=new byte[40];
			 
			   try {
				while((message=reader.readLine())!=null)
				   {
					System.out.println(message);
					MessageParser mp=new MessageParser();
					 event =mp.MessageParserStringToEvent(message);
					
					System.out.println(event.getEventId()+"/t"+event.getDimension()+"/t"+event.getValues()[0]);
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
	     
	
	

