package ss.pku.re.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.sun.org.apache.bcel.internal.generic.NEW;

import ss.pku.re.domain.Event;

public class EventDao extends HibernateDaoSupport implements IEventDao {
	
	private final static String ENTITYNAME = "ss.pku.re.domain.Event";
	private SimpleDateFormat sdfFromDIA = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSS"); 
	private SimpleDateFormat sdfToDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS");
	/**
	 * 
	 */
	@Override
	public Event save(Event event) {		
		try {
			event.setReceivedTime(sdfToDB.format(sdfFromDIA.parse(event.getReceivedTime())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return findById((Integer)this.getHibernateTemplate().save(event));
	}
	
	public Event findById(int id){
		return (Event) this.getHibernateTemplate().get(ENTITYNAME, id);
	}
	
	@Override
	public Event findEventById(int id) {
		return (Event) this.getHibernateTemplate().get(ENTITYNAME, id);
	}

	@Override
	public List<Event> findTimesByEvent(final Event event,final int count,final int limitTime) {
		
		List eventList = this.getHibernateTemplate().executeFind(new HibernateCallback() {
					@SuppressWarnings("deprecation")
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String queryString = "from "
								+ ENTITYNAME
								+ "  e where e.receivedTime>=? and e.receivedTime<=? order by e.receivedTime desc";// order by e.receivedTime desc
						Query query = session.createQuery(queryString);
						
						 
						try {
							query.setString(0,sdfFromDIA.format(new Date(sdfFromDIA.parse(event.getReceivedTime()).getTime()-limitTime*60*1000)));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						query.setString(1,event.getReceivedTime());  
						
						
						
						query.setMaxResults(count);
						return query.list();
					}
				});
		return eventList;
	}
	@Override
	public List<Event> findTimesByEvent(final Event event,final int count) {
		List eventList = this.getHibernateTemplate().executeFind(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						String queryString = "from "
								+ ENTITYNAME
								+ "  e where e.eventId=? order by e.receivedTime desc";
						Query query = session.createQuery(queryString);
						query.setString(0, event.getEventId());
						query.setMaxResults(count);
						return query.list();
					}
				});
		return eventList;
	}
	
}
