package ss.pku.re.service;

import java.util.List;

import ss.pku.re.dao.IEventDao;
import ss.pku.re.domain.Event;

public class EventService implements IEventService{
	private IEventDao eventDao;

	@Override
	public int getTimesByEvent(Event e,int count,int limitTime) {
	
		List<Event> eventList = eventDao.findTimesByEvent(e, count,limitTime);
		if(eventList!=null&&eventList.size()>1){
			Event event = eventList.get(0);
			System.out.println(event.getReceivedTime());
			int sum=1;
			for(int i=1;i<eventList.size(); i++){
				Event e1 = eventList.get(i);
				System.out.println(e1.getReceivedTime());
			//	if(e1.getValue()!=null&&e1.getEventId()!=null&&e1.getEventId().equals(event.getEventId())&&e1.getValue().equals(event.getValue()))
					sum++;
			//	else
		//			break;
			}
			return sum;
		}
		else
			return 0;
	}

	public IEventDao getEventDao() {
		return eventDao;
	}

	public void setEventDao(IEventDao eventDao) {
		this.eventDao = eventDao;
	}

	@Override
	public int getTimesByEvent(Event e, int count) {
		List<Event> eventList = eventDao.findTimesByEvent(e, count);
		if(eventList!=null&&eventList.size()>1){
			Event event = eventList.get(0);
			System.out.println(event.getReceivedTime());
			int sum=1;
			for(int i=1;i<eventList.size(); i++){
				Event e1 = eventList.get(i);
				System.out.println(e1.getReceivedTime());
			//	if(e1.getValue()!=null&&e1.getEventId()!=null&&e1.getEventId().equals(event.getEventId())&&e1.getValue().equals(event.getValue()))
					sum++;
			//	else
		//			break;
			}
			return sum;
		}
		else
			return 0;
	}
	
}
