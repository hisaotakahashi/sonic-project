package com.googlecode.sonic.constant.entity.properties;

/**
 * {@link com.google.appengine.api.datastore.Entity}に設定する共通的なプロパティ名を定義するクラス。
 * 
 * @author hisao takahashi
 * @since 2011/11/05
 */
public class CommonProperties implements PersistenceProperties {

	/** ID */
	public static final String ID = "id";
	/** Entityの種別 */
	public static final String KIND = "kind";
	/** 作成日時 */
	public static final String CREATE_DATE = "createDate";
	/** 作成ユーザー */
	public static final String CREATE_USER = "createUser";
	/** 更新日時 */
	public static final String UPDATE_DATE = "updateDate";
	/** 更新ユーザー */
	public static final String UPDATE_USER = "updateUser";
	/** 削除フラグ */
	public static final String DELETED = "deleted";

}
