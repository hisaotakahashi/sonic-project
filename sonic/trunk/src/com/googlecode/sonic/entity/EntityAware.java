package com.googlecode.sonic.entity;

import com.google.appengine.api.datastore.Entity;

/**
 * {@link com.google.appengine.api.datastore.Entity}啓示インターフェース。
 * 
 * @author hisao takahashi
 * @since 2011/11/06
 */
public interface EntityAware {

	/**
	 * 新規に{@link com.google.appengine.api.datastore.Entity}を生成する。<br/>
	 * 新規生成時に使用するのはkindのみ。
	 * 
	 * @see com.google.appengine.api.datastore.Entity#Entity(String)
	 * 
	 * @return {@link com.google.appengine.api.datastore.Entity}
	 */
	public abstract Entity createEntity();

	/**
	 * 新規に{@link com.google.appengine.api.datastore.Entity}を生成する。<br/>
	 * idを {@link com.google.appengine.api.datastore.Key}として新規生成する。
	 * 
	 * @see com.google.appengine.api.datastore.Entity#Entity(Key)
	 * 
	 * @param id
	 *            {@link com.google.appengine.api.datastore.Key}となるid
	 * @return {@link com.google.appengine.api.datastore.Entity}
	 */
	public abstract Entity createEntityByKey(Long id);
}
