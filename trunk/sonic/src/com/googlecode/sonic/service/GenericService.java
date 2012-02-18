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

import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.DELETED;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.ID;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.CREATE_DATE;

import java.util.Iterator;
import java.util.List;

import com.googlecode.sonic.dto.Dto;
import com.googlecode.sonic.exception.ServiceException;
import com.googlecode.sonic.util.AssertionUtil;
import com.googlecode.sonic.util.CustomQuery;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

/**
 * 一般的なサービスクラス。 一般的/抽象的な処理/メソッドを実装している。
 * 
 * @author hisao takahashi
 * @since 1.0
 * @param <T>
 *            Dtoの子クラス。
 */
public class GenericService<T extends Dto> extends AbstractService<T> {

	@Override
	protected List<T> entityToDto(List<Entity> entityList) throws Exception {
		// NOP.
		return null;
	}

	@Override
	protected T entityToDto(Entity entity) throws Exception {
		// NOP.
		return null;
	}

	/**
	 * idを条件にkindで指定した{@link com.google.appengine.api.datastore.Entity}を検索する。
	 * ただし論理削除フラグfalseのデータに限る。<br/>
	 * 
	 * @param id
	 * @return kindで指定したEntityクラス。
	 */
	protected Entity select(Long id) {
		return new CustomQuery(kindOfEntity).eq(ID, id).eq(DELETED, false)
				.asSingle();
	}

	/**
	 * idを条件にkindで指定した{@link com.google.appengine.api.datastore.Entity}を検索する。
	 * ただし論理削除フラグがtrueのデータも検索対象とする。<br/>
	 * 対象データが存在しない場合はServiceExceptionをthrowする。
	 * 
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	protected Entity selectForcibly(Long id) throws ServiceException {
		AssertionUtil.assertIsValid(ID, id);
		try {
			return datastoreService.get(KeyFactory.createKey(kindOfEntity, id));
		} catch (EntityNotFoundException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 特定のkindのEntityを全て取得する。<br/>
	 * 存在しない場合は空リストを取得する。<br/>
	 * 以下特記事項。<br/>
	 * ・deleteFlag=falseのEntityが取得対象。<br/>
	 * ・ソートは更新日時の降順。
	 * 
	 * @return 全てのEntity
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	protected List<Entity> selectAll() throws ServiceException {
		try {
			CustomQuery customQuery = new CustomQuery(kindOfEntity);
			return (List<Entity>) customQuery.eq(DELETED, false)
					.desc(CREATE_DATE).asList();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 特定のkindの合計件数を取得する。<br/>
	 * 以下特記事項。<br/>
	 * ・deleteFlag=falseのEntityが取得対象。
	 * 
	 * @return
	 * @throws ServiceException
	 */
	protected int count() throws ServiceException {
		List<Entity> entities = selectAll();
		if (entities.isEmpty()) {
			return 0;
		}
		return entities.size();
	}

	/**
	 * idを条件にkindで指定した{@link com.google.appengine.api.datastore.Entity}を物理削除する。
	 * 
	 * @param id
	 *            ID
	 * @throws ServiceException
	 * @throws AssertionException
	 */
	protected void physicalDelete(Long id) {
		Key key = KeyFactory.createKey(kindOfEntity, id);
		datastoreService.delete(key);
	}

	protected Key update(Entity entity) throws ServiceException {
		setUpdateProperties(entity);
		return datastoreService.put(entity);
	}

	/**
	 * idを条件にkindで指定した{@link com.google.appengine.api.datastore.Entity}を論理削除する。
	 * 
	 * @param id
	 *            ID
	 * @throws ServiceException
	 * @throws AssertionException
	 */
	protected Key logicalDelete(Long id) throws ServiceException {
		Entity entity = select(id);
		entity.setProperty(DELETED, true);
		setUpdateProperties(entity);
		return datastoreService.put(entity);
	}

	/**
	 * kindで指定した{@link com.google.appengine.api.datastore.Entity}を全て物理削除する。
	 */
	protected void truncate() {
		for (Iterator<Entity> i$ = datastoreService.prepare(
				new Query(kindOfEntity)).asIterator(); i$.hasNext();) {
			datastoreService.delete(i$.next().getKey());
		}
	}

	/**
	 * 最大ID値+1のIDを取得するメソッド。
	 * 
	 * @return 最大ID値+1のID
	 */
	@SuppressWarnings("unchecked")
	protected long getNextId() {
		List<Entity> entity = (List<Entity>) new CustomQuery(kindOfEntity)
				.desc(ID).asList();
		if (entity.isEmpty()) {
			return 1L;
		} else {
			return (Long) entity.get(0).getProperty(ID) + 1;
		}
	}

	// TODO IDがduplicatedでないかチェックするメソッド。
}
