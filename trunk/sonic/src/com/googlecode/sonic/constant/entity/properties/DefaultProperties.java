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
package com.googlecode.sonic.constant.entity.properties;

/**
 * {@link com.google.appengine.api.datastore.Entity}に設定する共通的なプロパティ名を定義するクラス。
 * 
 * @author hisao takahashi
 * @since 1.0
 */
public class DefaultProperties implements PersistenceProperties {

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
