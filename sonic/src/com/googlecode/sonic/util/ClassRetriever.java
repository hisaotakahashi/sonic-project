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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * {@link java.lang.Class}を取得するヘルパークラス。
 * 
 * @author hisao takahashi
 * @since 1.0
 */
public class ClassRetriever {

	protected static final Log log = LogFactory.getLog(ClassRetriever.class);

	private static String extension = ".class";

	/**
	 * collapse constructor.
	 */
	private ClassRetriever() {
		// NOP.
	}

	/**
	 * targetPathからクラス名称がregexに一致する{@link java.lang.Class}
	 * を取得する。一致するクラスが存在しない場合は空のリストを取得する。<br/>
	 * isInterfaceがtrueの場合はインターフェースも取得する。<br/>
	 * isAbstractがtrueの場合は抽象クラスも取得する。
	 * 
	 * @param targetPath
	 *            走査対象パス
	 * @param regex
	 *            取得対象ファイルの正規表現。nullの場合は取得制限無し。<br/>
	 *            e.g.
	 *            末尾が"Servlet.class"のクラスを取得する場合は、"^[A-Z]+[A-Za-z]*Servlet\\.class$"
	 * @param isInterface
	 *            インターフェース取得可否
	 * @param isAbstract
	 *            抽象クラス取得可否
	 * 
	 * @return 取得したクラスのリスト
	 */
	public static Set<Class<?>> findClasses(String targetPath, String regex,
			boolean isInterface, boolean isAbstract) {

		if (StringUtils.isBlank(regex))
			regex = ".*";

		Set<Class<?>> classList = new HashSet<Class<?>>();
		try {
			Enumeration<URL> enumeration = Thread.currentThread()
					.getContextClassLoader()
					.getResources(targetPath.replaceAll("\\.", "/"));
			while (enumeration.hasMoreElements()) {
				File file = new File(enumeration.nextElement().getFile());
				classList.addAll(findClasses(file, targetPath, regex,
						isInterface, isAbstract));
			}
		} catch (IOException e) {
			log.error("could not read that " + targetPath, e);
		}
		return classList;
	}

	private static Set<Class<?>> findClasses(File file, String targetPath,
			String regex, boolean isInterface, boolean isAbstract)
			throws IOException {
		Set<Class<?>> classList = new HashSet<Class<?>>();
		for (File f : file.listFiles()) {
			if (f.isDirectory()) {
				classList.addAll(findClasses(f,
						addDirectoryPath(targetPath, f), regex, isInterface,
						isAbstract));
				continue;
			}
			String fileName = f.getName();
			if (fileName.endsWith(extension) && fileName.matches(regex)) {
				String canonicalName = getCanonicalName(targetPath, fileName);
				try {
					Class<?> clazz = Thread.currentThread()
							.getContextClassLoader().loadClass(canonicalName);
					addClass(classList, clazz, isInterface, isAbstract);
				} catch (ClassNotFoundException e) {
					log.error(canonicalName + " Class is not found", e);
				}
			}
		}
		return classList;
	}

	private static String addDirectoryPath(String targetPath, File f)
			throws IOException {
		String splitPoint = targetPath.replaceAll("\\.", "/");
		String[] paths = f.getCanonicalPath().split(splitPoint);
		return new StringBuilder().append(targetPath)
				.append(paths[1].replaceAll("/", "\\.")).toString();
	}

	private static String getCanonicalName(String targetPath, String fileName) {
		return new StringBuilder()
				.append(targetPath)
				.append(".")
				.append(fileName.substring(0,
						fileName.length() - extension.length())).toString();
	}

	private static void addClass(Set<Class<?>> classList, Class<?> clazz,
			boolean isInterface, boolean isAbstract) {
		if (Modifier.isInterface(clazz.getModifiers())) {
			if (isInterface)
				classList.add(clazz);
		} else if (Modifier.isAbstract(clazz.getModifiers())) {
			if (isAbstract)
				classList.add(clazz);
		} else {
			classList.add(clazz);
		}
	}

}
