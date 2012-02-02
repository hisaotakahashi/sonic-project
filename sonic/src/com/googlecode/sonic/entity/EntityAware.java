/*
 * Copyright 2012 the Sonic Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.sonic.entity;

import com.google.appengine.api.datastore.Entity;

/**
 * {@link com.google.appengine.api.datastore.Entity}啓示インターフェース。
 * 
 * @author hisao takahashi
 * @since 1.0
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
