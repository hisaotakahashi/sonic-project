package com.googlecode.sonic.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.googlecode.sonic.annotation.RequestMapping;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

//XXX 他に機能は必要では？安直過ぎる。
/**
 * {@link com.googlecode.sonic.annotation.RequestMapping}のヘルパークラス。
 * 
 * @author hisao takahashi
 * @since 2011/12/10
 */
public class RequestMappingAnnotaionHolder {

	protected static final Log log = LogFactory
			.getLog(RequestMappingAnnotaionHolder.class);

	private static HashMap<String, String> holder = null;
	static {
		if (holder == null)
			holder = new HashMap<String, String>();
	}

	/**
	 * collapse constructor.
	 */
	private RequestMappingAnnotaionHolder() {
		// NOP.
	}

	public static void putIfAbsent(String key, String value) {
		if (!contains(key))
			holder.put(key, value);
	}

	public static void put(String key, String value) {
		holder.put(key, value);
	}

	public static String get(String key) {
		return holder.get(key);
	}

	public static void parse(Set<Class<?>> classes) {
		for (Class<?> clazz : classes) {
			String canonicalName = clazz.getCanonicalName();
			for (Method method : clazz.getMethods()) {
				parse(canonicalName, method);
			}
		}
		if (log.isInfoEnabled()) {
			Map<String, String> holder = getHolder();
			for (Iterator<String> i$ = holder.keySet().iterator(); i$.hasNext();) {
				String key = i$.next();
				log.info("key -> " + key + " / " + "value -> "
						+ holder.get(key));
			}
		}
	}

	private static void parse(String canonicalName, Method method) {
		if (!Modifier.isPublic(method.getModifiers())
				|| Modifier.isAbstract(method.getModifiers())) {
			return;
		}
		RequestMapping requestMapping = method
				.getAnnotation(RequestMapping.class);
		if (requestMapping != null)
			putIfAbsent(requestMapping.to(),
					new StringBuilder().append(canonicalName).append("#")
							.append(method.getName()).toString());
	}

	public static Map<String, String> getHolder() {
		return SerializationUtils.clone(holder);
	}

	public static void expire() {
		holder.clear();
	}

	public static int size() {
		return holder.keySet().size();
	}

	public static boolean contains(String key) {
		return StringUtils.isNotBlank(get(key));
	}

	public static boolean isEmpty() {
		return holder.isEmpty();
	}

	// XXX 必要？
	public static int getHashCode() {
		return holder.hashCode();
	}
}
