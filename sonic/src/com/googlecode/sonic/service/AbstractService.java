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
package com.googlecode.sonic.service;

import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.CREATE_DATE;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.CREATE_USER;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.DELETED;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.ID;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.UPDATE_DATE;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.UPDATE_USER;

import java.util.List;

import com.googlecode.sonic.dto.Dto;
import com.googlecode.sonic.entity.EntityAware;
import com.googlecode.sonic.util.AssertionUtil;
import com.googlecode.sonic.util.DateUtil;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * サービスの抽象クラス。 抽象的処理は本クラスに定義している。
 * 
 * @author hisao takahashi
 * @since 1.0
 */
public abstract class AbstractService<T extends Dto> implements Service,
		EntityAware {

	/**
	 * 操作対象{@link com.google.appengine.api.datastore.Entity}の種別。
	 * サブクラスのコンストラクタによって具体的な種別を設定されることを想定している。
	 */
	protected String kindOfEntity;

	/**
	 * @see com.google.appengine.api.datastore.DatastoreService
	 */
	protected DatastoreService datastoreService;
	{
		datastoreService = DatastoreServiceFactory.getDatastoreService();
	}

	/**
	 * @see com.google.appengine.api.users.UserService
	 */
	protected UserService userService;
	{
		userService = UserServiceFactory.getUserService();
	}

	/**
	 * 
	 * @param entityList
	 * @return
	 * @throws Exception
	 */
	protected abstract List<T> entityToDto(List<Entity> entityList)
			throws Exception;

	/**
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	protected abstract T entityToDto(Entity entity) throws Exception;

	@Override
	public Entity createEntity() {
		// TODO kindのみでのEntityの生成方法。
		Entity entity = new Entity(kindOfEntity);
		setCommonProperties(entity);
		return entity;
	}

	@Override
	public Entity createEntityByKey(Long id) {
		AssertionUtil.assertIsValid(ID, id);
		Entity entity = new Entity(KeyFactory.createKey(kindOfEntity, id));
		entity.setProperty(ID, id);
		setCommonProperties(entity);
		return entity;
	}

	private void setCommonProperties(Entity entity) {
		String userName = null;
		if (userService.isUserLoggedIn()) {
			userName = userService.getCurrentUser().getNickname();
		}
		String currentDate = DateUtil.toFormatString(DateUtil.getNow(),
				DateUtil.DATE_FORMAT_SLASH_01);
		entity.setProperty(CREATE_DATE, currentDate);
		entity.setProperty(CREATE_USER, userName);
		entity.setProperty(UPDATE_DATE, currentDate);
		entity.setProperty(UPDATE_USER, userName);
		entity.setProperty(DELETED, false);
	}
	
	protected void setUpdateProperties(Entity entity) {
		String userName = null;
		if (userService.isUserLoggedIn()) {
			userName = userService.getCurrentUser().getNickname();
		}
		String currentDate = DateUtil.toFormatString(DateUtil.getNow(),
				DateUtil.DATE_FORMAT_SLASH_01);
		entity.setProperty(UPDATE_DATE, currentDate);
		entity.setProperty(UPDATE_USER, userName);
	}
}
