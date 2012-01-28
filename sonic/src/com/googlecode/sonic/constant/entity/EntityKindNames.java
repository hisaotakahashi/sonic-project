package com.googlecode.sonic.constant.entity;

/**
 * {@link com.google.appengine.api.datastore.Entity}の種別を定義する。
 * 
 * @author hisao takahashi
 * @since 2011/10/30
 */
public enum EntityKindNames {

	BlogEntry("BlogEntry"), //
	BlogComment("BlogCommnet");

	private String name;

	private EntityKindNames(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
