package com.googlecode.sonic.util;

import org.apache.commons.lang3.StringUtils;


/**
 * アサートを行うユーティリティクラス。
 * 
 * @author hisao takahashi
 * @since 2011/11/11
 */
public class AssertionUtil {

	public static void assertIsValid(String name, Object object)
			throws IllegalArgumentException {
		if (object == null)
			throw new IllegalArgumentException(new StringBuilder().append(name)
					.append(" is null.").toString());
	}

	public static void assertIsValid(String name, String value)
			throws IllegalArgumentException {
		if (StringUtils.isBlank(value)) {
			if (name == null)
				name = "this";
			throw new IllegalArgumentException(new StringBuilder().append(name)
					.append(" is null or white space.").toString());
		}
	}

	public static void assertIsValid(String name, Long value)
			throws IllegalArgumentException {
		assertIsValid(name, value, false);
	}

	public static void assertIsValid(String name, Long value,
			boolean isCheckNegativeNumber) throws IllegalArgumentException {
		if (value == null || (isCheckNegativeNumber && value < 0)) {
			throw new IllegalArgumentException(String.format(
					new StringBuilder().append(name)
							.append(" is invalid value. '%s%n'").toString(),
					value));
		}
	}

}
