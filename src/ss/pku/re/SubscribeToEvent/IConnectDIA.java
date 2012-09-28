package ss.pku.re.SubscribeToEvent;

import cn.edu.pku.ss.exception.LoginFailure;
import cn.edu.pku.ss.exception.MessageFormatError;

public interface IConnectDIA {
	public void init(String p,String username,String password) throws LoginFailure;
	public  boolean SubscriberByTopic(String topic,String SensorID)throws LoginFailure, MessageFormatError;
	public void SubscribeAllEvent();
   
}
