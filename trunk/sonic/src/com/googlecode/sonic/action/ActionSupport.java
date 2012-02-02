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
package com.googlecode.sonic.action;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Actionをサポートするクラス。 <br/>
 * 基本的に処理内容は継承/実装している、{@link AbstractAction}、{@link ServletRequestAware}、
 * {@link ServletResponseAware}、 {@link SessionAware}の内容に依存する。
 * 
 * @author hisao takahashi
 * @since 1.0
 */
public class ActionSupport implements Action, Serializable,
		ServletRequestAware, ServletResponseAware, SessionAware {

	private static final long serialVersionUID = -2958239110298404687L;

	private HttpServletRequest request;
	private HttpServletResponse response;

	@Override
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public void setSessionAttributeByFixedKey(String key, Object value) {
		request.getSession(true).setAttribute(key, value);
	}

	@Override
	public Object getSessionAttributeByFixedKey(String key) {
		return getSessionAttributeByFixedKey(key, false);
	}

	@Override
	public Object getSessionAttributeByFixedKey(String key, boolean isRemove) {
		return getSessionAttributeByFixedKey(request.getSession(true), key,
				isRemove);
	}

	private Object getSessionAttributeByFixedKey(HttpSession session,
			String key, boolean isRemove) {
		Object object = session.getAttribute(key);
		if (isRemove) {
			session.removeAttribute(key);
		}
		return object;
	}

}
