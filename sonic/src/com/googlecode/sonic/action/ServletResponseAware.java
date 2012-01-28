package com.googlecode.sonic.action;

import javax.servlet.http.HttpServletResponse;

/**
 * {@link javax.servlet.http.HttpServletResponse}啓示インターフェース。
 * 
 * @author hisao takahashi
 * @since 2011/12/10
 */
public interface ServletResponseAware {
	
	public static final String INVOKED = "setResponse";

	public abstract void setResponse(HttpServletResponse response);

	public abstract HttpServletResponse getResponse();
}
