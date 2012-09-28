package ss.pku.re.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ss.pku.re.domain.Subscribe;

public class SubscribeDao extends HibernateDaoSupport implements ISubscribeDao {
	
	private final static String ENTITYNAME = "ss.pku.re.domain.Subscribe";
	/**
	 * 
	 */
	@Override
	public Subscribe save(Subscribe subscribe) {
		return findById((Integer)this.getHibernateTemplate().save(subscribe));
	}
	private Subscribe findById(Integer id) {
		return (Subscribe) this.getHibernateTemplate().get(ENTITYNAME, id);
	}    
	@Override
	public List<Subscribe> getByEventId(String eventId) {
		return this.getHibernateTemplate().find("from "+ENTITYNAME+" e where e.eventId='"+eventId+"'");
	}
	@Override
	public boolean hasSubscribe(String eventId,String engineId) {
		List<Subscribe> subscribeList =  this.getHibernateTemplate().find("from "+ENTITYNAME+" e where e.eventId='"+eventId+"' and e.engineId='"+engineId+"'");
		if(subscribeList!=null&&subscribeList.size()>0)
			return true;
		return false;
	}
	@Override
	public Subscribe getById(int id) {
		return (Subscribe)this.getHibernateTemplate().get(ENTITYNAME, id);
	}
	@Override
	public List<Subscribe> getAll() {
		// TODO Auto-generated method stub
		List<Subscribe> subscribeList =this.getHibernateTemplate().find("from "+ENTITYNAME+" e");
		
		return subscribeList;
	}
	
}
