package ss.pku.re.service;

import java.io.IOException;
import java.util.Date;

import cn.edu.pku.ss.controller.DIAController;
import cn.edu.pku.ss.eventProcessor.ContinuedEventProcessor;
import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;
import cn.edu.pku.ss.util.BluetoothCollection;
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

	@Override
	public void sendService(Service s, Event event) {
		System.out.println(s.getValue()+'-'+s.getServiceId());
		System.out.println(event.getEventId()+':'+event.getValues()[0]);
		
		ContinuedEventProcessor cep;
		try {
			cep = ContinuedEventProcessor.getInstance(uri, user, pswd);
			if("16".equals(s.getServiceId())){//针对手机的命令
				if("1600001$".equals(s.getValue()) || "1600002$".equals(s.getValue())){//使用收集器
					
					if(null == BluetoothCollection.cEventProcessor){//尚未初始化
						if("1600001$".equals(s.getValue()))//发送给手机播音频
							BluetoothCollection.sendToLoudspeakerOrPhone = BluetoothCollection.SEND_TO_PHONE;//设置用于控制手机还是喇叭
						else//发送给喇叭播音频
							BluetoothCollection.sendToLoudspeakerOrPhone = BluetoothCollection.SEND_TO_LOUDSPEAKER;//设置用于控制手机还是喇叭
						BluetoothCollection.init(cep);
					}
					BluetoothCollection.addBluetooth(event.getValues()[0]);//将蓝牙添加到收集类中，由该类来做处理
					
				}else{//直接发送控制命令来控制手机
					cep.sendSameAudioToPhoneCommand(event.getValues()[0]+">"+s.getValue());
				}
			}else{//如果不是针对手机的控制命令（即sensorID为16），则直接发送
				cep.sendCommand(s.getServiceId(), s.getValue());
			}
			
			
			//cep.sendCommand(s.getServiceId(), s.getValue());
		} catch (LoginFailure e) {
			e.printStackTrace();
		} catch (MessageFormatError e) {
			e.printStackTrace();
		}
		
	}
}
