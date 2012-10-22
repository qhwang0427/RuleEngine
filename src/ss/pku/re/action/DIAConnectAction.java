package ss.pku.re.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ss.pku.re.SubscribeToEvent.MessageParser;
import ss.pku.re.dao.SubscribeDao;
import ss.pku.re.domain.Event;
import ss.pku.re.domain.Subscribe;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.service.IRuleService;
import cn.edu.pku.ss.bean.DIAMessage;
import cn.edu.pku.ss.bean.DIATemperatureMessage;
import cn.edu.pku.ss.eventProcessor.ContinuedEventProcessor;
import cn.edu.pku.ss.eventProcessor.DIAListener;
import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;
import cn.edu.pku.ss.exception.NoPermissionException;
import cn.edu.pku.ss.util.Util;

import com.opensymphony.xwork2.ActionSupport;

public class DIAConnectAction extends ActionSupport {

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		/*
		 * IConnectDIA conn=new ConnectForTest(); conn.init("","","");
		 */
		/*
		 * IConnectDIA conn = SubscribeProvider.getInstance();
		 * conn.init("ws://192.168.213.222:8080/DIAServer/core-socket"
		 * ,"jerry","2012727"); conn.SubscribeAllEvent();
		 */
		/*
		 * try { URI uri = new URI("http://localhost:8080/test"); Desktop
		 * desktop = null; if (Desktop.isDesktopSupported()) { desktop =
		 * Desktop.getDesktop(); } if (desktop != null) desktop.browse(uri); }
		 * catch (IOException ioe) { ioe.printStackTrace(); } catch
		 * (URISyntaxException e) { e.printStackTrace(); }
		 */
		final IRuleService ruleService = (IRuleService) ContextFactory.getContext().getBean("ruleService");
		ContinuedEventProcessor cep = null;
		try {
			cep= ContinuedEventProcessor.getInstance(
					"ws://192.168.213.105:8080/DIAServer/core-socket", "jerry", "2012727");
		} catch (LoginFailure e1) {
			e1.printStackTrace();
		}
		
		SubscribeDao subscribedao = (SubscribeDao) ContextFactory.getContext()
				.getBean("subscribeDao");
		List<Subscribe> subscribeList = (List<Subscribe>) subscribedao.getAll();
	
		DIAListener l = new DIAListener() {
			public void cleanUp() throws Exception {
			}

			public void onStarted() {
				System.out.println("Listener started!");
			}

			public void onMessage(Map<String, String> msg) {
				Util.traceEvent(msg);
				System.out.println("TOPIC: "+msg.get(DIAMessage.TOPIC)+"  Time: "+msg.get(DIAMessage.OCCUR_TIME));
				// 处理收到的事件
				MessageParser mp = new MessageParser();
				Event event = mp.MessageParserMapToEvent(msg,msg.get(DIAMessage.TOPIC));
				
				ruleService.receiveEvent(event);
				// Open Air Conditioning to let the room cool down
			}

		};
		
		if (subscribeList != null && subscribeList.size() > 0) {
			for (int i = 0; i < subscribeList.size(); i++) {
				try {
					cep.addEventGenerator(subscribeList.get(i).getTopic());
					//为了能够使用phone主题的处理，此处加个是否为PHONE主题的判断，
					//如果是的话，new一个类来保存获得的phone的蓝牙地址，用于回发给DIAServer
					//如何回发给DIAServer，就是要在service类里面加上相应的处理代码了
					// subscribeList.get(i).getEventId()
					
					cep.addListener("Select * From "
							+ subscribeList.get(i).getTopic()+" where SENSOR_ID equals "+subscribeList.get(i).getEventId());

				} catch (LoginFailure e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessageFormatError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoPermissionException e) {
					e.printStackTrace();
				}
			}
			
			cep.startListener(l);////start the thread that is used to receive message from the listeners in DIAServer
		}

		return SUCCESS;
	}

}
