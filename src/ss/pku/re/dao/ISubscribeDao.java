package ss.pku.re.dao;

import java.util.List;

import ss.pku.re.domain.Subscribe;



public interface ISubscribeDao {
		/**
		 * 保存订阅对象
		 * @param subscribe
		 * @return
		 */
		public Subscribe save(Subscribe subscribe);
		/**
		 * 根据eventId获取所有的订阅对象
		 * @param eventId
		 * @return
		 */
		public List<Subscribe> getByEventId(String eventId);
		/**
		 * 是否已经包含了订阅记录
		 * @param engineId
		 * @param eventId
		 * @return
		 */
		public boolean hasSubscribe(String engineId,String eventId);
		/**
		 * 通过Id获取记录
		 * @param id
		 * @return
		 */
		public Subscribe getById(int id);
		public List<Subscribe> getAll();
}
