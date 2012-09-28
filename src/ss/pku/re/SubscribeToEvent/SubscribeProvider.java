package ss.pku.re.SubscribeToEvent;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.context.ApplicationContext;

import ss.pku.re.dao.ISubscribeDao;
import ss.pku.re.dao.SubscribeDao;
import ss.pku.re.domain.Event;
import ss.pku.re.domain.Subscribe;
import ss.pku.re.rule.util.ContextFactory;
import ss.pku.re.service.IRuleService;
import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;
import cn.edu.pku.ss.safe.MD5andKL;
import cn.edu.pku.ss.util.ConstMessage;
import cn.edu.pku.ss.util.WebSocket;

public class SubscribeProvider implements IConnectDIA {
	private URI uri;
	private WebSocket ws;
	private String username;
	private String password;
	private String app_no;
	private ISubscribeDao subscribedao;
	private boolean conn = false;

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public WebSocket getWs() {
		return ws;
	}

	public void setWs(WebSocket ws) {
		this.ws = ws;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isConn() {
		return conn;
	}

	public void setConn(boolean conn) {
		this.conn = conn;
	}

	public static SubscribeProvider SubscriberManager = new SubscribeProvider();

	private SubscribeProvider() {
	}

	/*private SubscribeProvider(String s, String username, String password) {
		try {
			uri = new URI(s);
			ws = new WebSocket(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.username = username;
		this.password = password;
	}*/

	public static SubscribeProvider getInstance() {
		if (null == SubscriberManager) {
			SubscriberManager = new SubscribeProvider();
		}
		return SubscriberManager;
	}

	// 连接DIA
	public void init(String addr, String username, String password)
			throws LoginFailure {
		try {
			uri = new URI(addr);
			ws = new WebSocket(uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.username = username;
		this.password = password;
		try {
			ws.connect();
			ws.send(MD5andKL.KL("connect:" + username + password));
			byte[] bs = ws.recv();
			String s = new String(bs);
			s = MD5andKL.JM(s);
			if (!s.equals(ConstMessage.SUCCESS)) {
				ws.close();
				throw new LoginFailure();
			}else{
				app_no = s.replaceAll("Successful:", "");
				conn = true;
				System.out.println("Login Successfully");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t = null;
		t = new Thread() {
			public void run() {
				System.out.println("开始监听");
				ApplicationContext context = ContextFactory.getContext();
				IRuleService ruleService = (IRuleService) context
						.getBean("ruleService");
				Event event = new Event();

				while (true) {
					byte[] bs;
					try {
						// When receive message from server, it will parse it.
						bs = ws.recv();
						System.out.println("Received: " + bs);
						String ss = new String(bs);
						ss = MD5andKL.JM(ss);
						// The listener has started on the server, so the
						// listener on the client should start too.
						if (bs.equals(ConstMessage.WRONG_MESSAGE)) {
							// ws.close();
							try {
								throw new MessageFormatError();
							} catch (MessageFormatError e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else if(ss.equals(ConstMessage.NO_PERMISSION)){ 
							System.err.println(ConstMessage.NO_EVENT_PERMISSION);
						}else if (ss.equals(ConstMessage.SUCCESS)) {
							System.out.println("Successful");

						} else if (ss.equals("Start")) {
							// listener.onStarted();
						} else if (ss.equals("CleanUp")) {
							try {
								// listener.cleanUp();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							// The listener is trigered on the server. Then,
							// call the onMessage() and use the messge as the
							// parameter.
							// 根据收到的json格式字符串解析出事件
							MessageParser mp = new MessageParser();
							System.out.println("subscribeProvider: " + ss);
							event = mp.MessageParserStringToEvent(ss);

							// 处理收到的事件
							ruleService.receiveEvent(event);

							// Open Air Conditioning to let the room cool down
							// return;//exist the thread.
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();

	}

	// 根据主题+传感器号订阅,DIA目前只实现了温度的订阅，所以该部分待完善
	public boolean SubscriberByTopic(String topic, String SensorID)
			throws LoginFailure, MessageFormatError {
		if (!this.conn)
			throw new LoginFailure();
		try {
			ws.send(MD5andKL.KL(app_no+"@addGenerator:topic=" + topic));
			ws.send(MD5andKL.KL(app_no+"@addListener:epl=" + "Select * From " + topic));// +" where SensorID="+SensorID);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	// 根据数据库保存的订阅表订阅
	public void SubscribeAllEvent() {
		subscribedao = (SubscribeDao) ContextFactory.getContext().getBean(
				"subscribeDao");
		List<Subscribe> subscribeList = (List<Subscribe>) subscribedao.getAll();
		if (subscribeList != null && subscribeList.size() > 0) {
			for (int i = 0; i < subscribeList.size(); i++) {
				try {
					SubscriberByTopic(subscribeList.get(i).getTopic(),
							subscribeList.get(i).getEventId());
				} catch (LoginFailure e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessageFormatError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
