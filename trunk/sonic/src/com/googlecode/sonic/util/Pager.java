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

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * Pager専用List
 * 
 * @param <E>
 * @author hisao takahashi
 */
public class Pager<E> extends LinkedList<E> implements List<E> {

	private static final long serialVersionUID = 7992081446172844643L;

	// リクエストパラメーター等に使用する定数。
	public static final String PAGE_NO = "pageNo";

	// 現在のページNo
	private int currentPageNo = 0;

	// 合計ページ数
	private int totalPageCnt = 1;

	// offset値
	private int offset = 0;

	// 1ページの表示件数5
	public static final int ONE_PAGE_LIMIT_5 = 5;

	// 1ページの表示件数5
	public static final int ONE_PAGE_LIMIT_10 = 10;

	// 1ページの表示件数20
	public static final int ONE_PAGE_LIMIT_20 = 20;

	// 1ページの表示件数40
	public static final int ONE_PAGE_LIMIT_40 = 40;

	// 1ページの表示件数60
	public static final int ONE_PAGE_LIMIT_60 = 60;

	/**
	 * Pager生成。 1ページの表示件数はデフォルト値を使用。
	 * 
	 * @param pageNo
	 * @param totalCnt
	 * @param list
	 */
	public Pager(int pageNo, int totalCnt, List<E> list) {
		this(pageNo, ONE_PAGE_LIMIT_5, totalCnt, list);
	}

	/**
	 * Pager生成。 1ページの表示件数をlimitで指定可能。
	 * 
	 * @param pageNo
	 * @param limit
	 * @param totalCnt
	 * @param list
	 */
	public Pager(int pageNo, int limit, int totalCnt, List<E> list) {
		addAll(list);

		// 総件数がlimit以下の場合はcurrentPageNo,totalPageCnt,offsetはデフォルト値のままでよい。
		if (totalCnt <= limit)
			return;

		this.offset = pageNo * limit;
		this.currentPageNo = pageNo;
		this.totalPageCnt = totalCnt / limit;
		if (totalCnt % limit > 0) {
			this.totalPageCnt++;
		}
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public int getTotalPageCnt() {
		return totalPageCnt;
	}

	public int getOffset() {
		return offset;
	}

	public boolean isNext() {
		if ((totalPageCnt - 1) == currentPageNo) {
			return false;
		}
		return true;
	}

	public int getNextPageNo() {
		if (!isNext())
			return currentPageNo;
		return currentPageNo + 1;
	}

	public boolean isPrev() {
		if (0 == currentPageNo) {
			return false;
		}
		return true;
	}

	public int getPrevPageNo() {
		if (!isPrev())
			return currentPageNo;
		return currentPageNo - 1;
	}

	public static String getOffsetLimit(int pageNo, int onePageLimit) {
		int offset = pageNo * onePageLimit;
		StringBuffer buff = new StringBuffer();
		return buff.append(" limit ").append(onePageLimit).append(" offset ")
				.append(offset).toString();
	}

	public static int getCurentPageNo(HttpServletRequest request) {
		return StringUtils.isNotBlank(request.getParameter(Pager.PAGE_NO)) ? Integer
				.parseInt(request.getParameter(Pager.PAGE_NO)) : 0;
	}
}