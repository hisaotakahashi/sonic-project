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

import org.apache.commons.lang3.StringUtils;

/**
 * アサートを行うユーティリティクラス。
 * 
 * @author hisao takahashi
 * @since 1.0
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
