package com.googlecode.sonic.action;


/**
 * {@link javax.servlet.http.HttpSession}啓示インターフェース。
 * 
 * @author hisao takahashi
 * @since 2011/12/10
 */
public interface SessionAware {

	public abstract void setSessionAttributeByFixedKey(String key, Object value);

	public abstract Object getSessionAttributeByFixedKey(String key);

	public abstract Object getSessionAttributeByFixedKey(String key,
			boolean isRemove);

}
