package com.googlecode.sonic.exception;

/**
 * 
 * @author hisao takahashi
 * @since 2011/10/30
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -3391182188599061674L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}