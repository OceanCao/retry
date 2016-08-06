/**
 * 
 */
package com.chrhc.xjs.retry.persist;

import java.util.List;

/**
 * @author 605162215@qq.com
 *
 * 2016��8��6�� ����2:34:49
 */
public interface PersistService<T> {
	
	public void save(T t);
	
	public void delete(T t);
	
	public List<T> getAll();
	
	public int size();
}
