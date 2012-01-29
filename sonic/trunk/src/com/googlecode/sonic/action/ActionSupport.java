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
 * @since 2011/12/10
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
