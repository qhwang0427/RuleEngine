package ss.pku.re.SubscribeToEvent;

import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;

//一个连接DIA的例子

public class SsubscribeMain {

	public void main(){
		SubscribeProvider ep=SubscribeProvider.getInstance();

		try {
			ep.init("ws://localhost:8080/DIAServer/core-socket","wilson","1234");
			try {
				ep.SubscriberByTopic("Temperature","s");
			} catch (MessageFormatError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	} catch (LoginFailure e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		}

}
