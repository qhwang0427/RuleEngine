package ss.pku.re.service;

import java.io.IOException;
import java.util.Date;

import cn.edu.pku.ss.controller.DIAController;
import cn.edu.pku.ss.eventProcessor.ContinuedEventProcessor;
import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;
import ss.pku.re.SubscribeToEvent.SubscribeProvider;
import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.domain.Service;

public class DIAServiceImplForTest implements DIAService {
	private String uri = "ws://192.168.213.105:8080/DIAServer/core-socket";
	private String user = "jerry";
	private String pswd = "2012727";

	@Override
	public void sendEvent(Event e) {
		System.out.println(e.getValue());
		System.out.println(e.getEventId());
		System.out.println(e.getDimension());
		// 向DIA发送命令的代码

		if (SubscribeProvider.getInstance().getWs() != null) {
			Date date = new Date();
			System.out
					.println("Send command at " + date + " " + date.getTime());
			// c.sendCommand("14", "");
			// int i = 1;
			try {
				SubscribeProvider
						.getInstance()
						.getWs()
						.send("control:topic=Command" + "&sensorID="
								+ e.getEventId() + "&command=" + e.getValue());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void sendService(Service s) {
		System.out.println(s.getValue());
		System.out.println(s.getServiceId());
		// DIAController c = new
		// DIAController("ws://localhost:8080/DIAServer/core-socket","wilson","1234");
		// try {
		ContinuedEventProcessor cep;
		try {
			cep = ContinuedEventProcessor.getInstance(uri, user, pswd);
			cep.sendCommand(s.getServiceId(), s.getValue());
		} catch (LoginFailure e) {
			e.printStackTrace();
		} catch (MessageFormatError e) {
			e.printStackTrace();
		}
		/*
		 * if(SubscribeProvider.getInstance().getWs()!=null){ try {
		 * SubscribeProvider
		 * .getInstance().getWs().send("control:topic=Command"+"&sensorID="
		 * +s.getServiceId()+"&command="+s.getValue()); } catch (IOException e)
		 * { // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * }
		 */

	}
}
