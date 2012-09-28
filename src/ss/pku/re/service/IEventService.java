package ss.pku.re.service;

import ss.pku.re.domain.Event;

public interface IEventService {
	
	public int getTimesByEvent(Event e,int count,int limitTime);
	
	public int getTimesByEvent(Event e,int count);
}
