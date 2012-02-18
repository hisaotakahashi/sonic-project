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

import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.CREATE_DATE;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.CREATE_USER;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.DELETED;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.KIND;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.UPDATE_DATE;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.UPDATE_USER;
import static com.googlecode.sonic.constant.entity.properties.DefaultProperties.ID;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.googlecode.sonic.dto.Dto;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import com.google.appengine.api.datastore.Entity;

/**
 * 
 * @author hisao takahashi
 * @since 1.0
 */
public class ExBeanUtil extends BeanUtils {

	protected static final Log log = LogFactory.getLog(ExBeanUtil.class);

	private static final List<String> ignoredProperties = Arrays
			.asList(new String[] { ID, KIND, CREATE_DATE, CREATE_USER,
					UPDATE_DATE, UPDATE_USER, DELETED });

	/**
	 * 
	 * @param source
	 * @param destination
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws InvocationTargetException
	 */
	public static void copyProperties(Entity source, Dto destination)
			throws IllegalAccessException, IntrospectionException,
			InvocationTargetException {
		for (Iterator<Entry<String, Object>> i$ = source.getProperties()
				.entrySet().iterator(); i$.hasNext();) {
			Entry<String, Object> entry = i$.next();
			if (log.isDebugEnabled()) {
				log.debug("entry.getKey() -> " + entry.getKey());
				log.debug("entry.getValue() -> " + entry.getValue());
			}
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
					entry.getKey(), destination.getClass());
			Method method = propertyDescriptor.getWriteMethod();
			if (method != null) {
				method.invoke(destination, new Object[] { entry.getValue() });
			}
		}
	}

	// XXX method名が微妙。コード重複箇所共通化。
	public static void copyPropertiesIgnorePropertiesAware(Entity source,
			Dto destination) throws IllegalAccessException,
			IntrospectionException, InvocationTargetException {
		for (Iterator<Entry<String, Object>> i$ = source.getProperties()
				.entrySet().iterator(); i$.hasNext();) {
			Entry<String, Object> entry = i$.next();
			if (ignoredProperties.contains(entry.getKey()))
				continue;
			if (log.isDebugEnabled()) {
				log.debug("entry.getKey() -> " + entry.getKey());
				log.debug("entry.getValue() -> " + entry.getValue());
			}
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(
					entry.getKey(), destination.getClass());
			Method method = propertyDescriptor.getWriteMethod();
			if (method != null) {
				method.invoke(destination, new Object[] { entry.getValue() });
			}
		}
	}

}
