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
package com.googlecode.sonic.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

/**
 * 
 * @author hisao takahashi
 * @since 1.0
 */
public class CustomQuery {

	private Query query;
	private FetchOptions fetchOptions = null;

	public CustomQuery(String kind) {
		query = new Query(kind);
	}

	public CustomQuery(String kind, Key key) {
		query = new Query(kind, key);
	}

	public CustomQuery eq(String key, Object value) {
		query.addFilter(key, FilterOperator.EQUAL, value);
		return this;
	}

	public CustomQuery ne(String key, Object value) {
		query.addFilter(key, FilterOperator.NOT_EQUAL, value);
		return this;
	}

	public CustomQuery lt(String key, Object value) {
		query.addFilter(key, FilterOperator.LESS_THAN, value);
		return this;
	}

	public CustomQuery le(String key, Object value) {
		query.addFilter(key, FilterOperator.LESS_THAN_OR_EQUAL, value);
		return this;
	}

	public CustomQuery gt(String key, Object value) {
		query.addFilter(key, FilterOperator.GREATER_THAN, value);
		return this;
	}

	public CustomQuery ge(String key, Object value) {
		query.addFilter(key, FilterOperator.GREATER_THAN_OR_EQUAL, value);
		return this;
	}

	public CustomQuery In(String key, Object value) {
		query.addFilter(key, FilterOperator.IN, value);
		return this;
	}

	public CustomQuery limit(int limit) {
		if (fetchOptions == null) {
			fetchOptions = FetchOptions.Builder.withLimit(limit);
		} else {
			fetchOptions.limit(limit);
		}
		return this;
	}

	public CustomQuery offset(int offset) {
		if (fetchOptions == null) {
			fetchOptions = FetchOptions.Builder.withOffset(offset);
		} else {
			fetchOptions.offset(offset);
		}
		return this;
	}

	public CustomQuery asc(String key) {
		query = query.addSort(key, SortDirection.ASCENDING);
		return this;
	}

	public CustomQuery desc(String key) {
		query = query.addSort(key, SortDirection.DESCENDING);
		return this;
	}

	public Entity asSingle() {
		return DatastoreServiceFactory.getDatastoreService().prepare(query)
				.asSingleEntity();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<?> asList() {

		PreparedQuery q = DatastoreServiceFactory.getDatastoreService()
				.prepare(query);

		Iterator<?> it = null;
		if (fetchOptions == null) {
			it = q.asIterator();
			List results = new ArrayList();
			while (it.hasNext()) {
				results.add(it.next());
			}
			return results;
		} else {
			return q.asList(fetchOptions);
		}
	}
}