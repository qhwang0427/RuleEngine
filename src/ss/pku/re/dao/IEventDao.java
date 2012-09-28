package ss.pku.re.dao;

import java.util.List;

import ss.pku.re.domain.Event;

public interface IEventDao {
	/**
	 * 保存Event对象
	 * @param event
	 * @return
	 */
	public Event save(Event event);
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Event findEventById(int id);
	
	public List<Event> findTimesByEvent(Event event,int count,int limitTime);
	public List<Event> findTimesByEvent(Event event, int count);
}
