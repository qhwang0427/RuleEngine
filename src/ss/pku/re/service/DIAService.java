package ss.pku.re.service;

import ss.pku.re.domain.Event;
import ss.pku.re.rule.util.domain.Service;

/**
 * 调用DIA的传送事件接口
 * @author lqs
 *
 */
public interface DIAService {
	/**
	 * 发送一个事件到event bus
	 */
	public void sendEvent(Event e);
	/**
	 * 
	 */
	public void sendService(Service s);
	public void sendService(Service s, Event e);
}
