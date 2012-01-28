package com.googlecode.sonic.action;

import javax.servlet.http.HttpServletRequest;

/**
 * {@link javax.servlet.http.HttpServletRequest}啓示インターフェース。
 * 
 * @author hisao takahashi
 * @since 2011/12/10
 */
public interface ServletRequestAware {

	public static final String INVOKED = "setRequest";

	public abstract void setRequest(HttpServletRequest request);

	public abstract HttpServletRequest getRequest();
}
