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
package com.googlecode.sonic.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.sonic.action.Action;
import com.googlecode.sonic.action.ServletRequestAware;
import com.googlecode.sonic.action.ServletResponseAware;
import com.googlecode.sonic.annotation.Result;
import com.googlecode.sonic.constant.ActionResult;
import com.googlecode.sonic.util.ClassRetriever;
import com.googlecode.sonic.util.RequestMappingAnnotaionHolder;
import com.googlecode.sonic.util.AssertionUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * リクエストのディスパッチを行うサーブレット。<br/>
 * リクエストの{@link javax.servlet.http.HttpServletRequest#getRequestURI()}
 * によって取得したURIから実行対象の{@link com.googlecode.sonic.action.Action}を取得し、実行する。<br/>
 * <br/>
 * {@link com.googlecode.sonic.action.Action}実行後はそのアクションに設定された
 * {@link com.googlecode.sonic.annotation.Result#to()}の値を元にフォワードする。<br/>
 * なお、前述の一連の処理中に何らかの例外が発生した場合は、エラーページでリダイレクトする。
 * 
 * @author hisao takahashi
 * @since 1.0
 */
public class DispatcherServlet extends HttpServlet {

	protected static final Log log = LogFactory.getLog(DispatcherServlet.class);

	private static final long serialVersionUID = -5159687607334190523L;

	protected static final String ERROR_PAGE = "/error.html";

	private static String encoding;
	private static final String defaultEncoding = "UTF-8";
	private static String actionPackage;
	private static final String actionRegex = "^[A-Z]+[A-Za-z]*Action\\.class$";

	/**
	 * {@inheritDoc} {@Link Action}の実装クラスを収集し、それらを解析して
	 * {@link com.googlecode.sonic.annotation.RequestMapping}をメモリに保持しておく。
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		setActionPackage(config.getInitParameter("actionPackage"));
		setCharacterEncoding(config.getInitParameter("encoding"));
		RequestMappingAnnotaionHolder.parse(ClassRetriever.findClasses(
				actionPackage, actionRegex, false, false));
	}

	private void setActionPackage(String actionPath) {
		actionPackage = actionPath;
	}

	private void setCharacterEncoding(String enocode) {
		encoding = enocode;
		if (StringUtils.isBlank(encoding))
			encoding = defaultEncoding;
	}
	
	@Override
	public void destroy() {
		RequestMappingAnnotaionHolder.expire();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			log.info(this.getClass().getCanonicalName() + "#doGet");
			String destination = dispatch(request, response);
			forward(request, response, destination);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			redirect(response, ERROR_PAGE);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			log.info(this.getClass().getCanonicalName() + "#doPost");
			String destination = dispatch(request, response);
			forward(request, response, destination);
		} catch (Exception e) {
			log.error(this.getClass().getName(), e);
			redirect(response, ERROR_PAGE);
		}
	}

	protected String dispatch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setCharacterEncoding(response);
		String clazzCanonicalNameAndMethodName = RequestMappingAnnotaionHolder
				.get(request.getRequestURI());
		AssertionUtil.assertIsValid("request mapping",
				clazzCanonicalNameAndMethodName);
		String clazzCanonicalName = clazzCanonicalNameAndMethodName.split("#")[0];
		String methodName = clazzCanonicalNameAndMethodName.split("#")[1];
		Class<?> clazz = Thread.currentThread().getContextClassLoader()
				.loadClass(clazzCanonicalName);
		Method method = clazz.getDeclaredMethod(methodName);
		if (Modifier.isPublic(method.getModifiers())
				&& !Modifier.isAbstract(method.getModifiers())) {
			Action action = (Action) clazz.newInstance();
			setRequestAndResponse(action, request, response);
			log.info(clazzCanonicalName + "#" + method.getName() + " -> start");
			ActionResult actionResult = (ActionResult) method.invoke(action);
			log.info(clazzCanonicalName + "#" + method.getName() + " -> end");
			return getDestination(clazz, method, actionResult);
		} else {
			log.error("method : '" + method.getName() + "' is invalid.");
			throw new IllegalStateException();
		}
	}

	private void setCharacterEncoding(HttpServletResponse response) {
		response.setCharacterEncoding(encoding);
	}

	private void setRequestAndResponse(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		Class<?> superClass = action.getClass().getSuperclass();
		superClass.getDeclaredMethod(ServletRequestAware.INVOKED,
				HttpServletRequest.class).invoke(action, request);
		superClass.getDeclaredMethod(ServletResponseAware.INVOKED,
				HttpServletResponse.class).invoke(action, response);
	}

	private String getDestination(Class<?> clazz, Method method,
			ActionResult actionResult) {
		for (Result result : method.getAnnotation(
				com.googlecode.sonic.annotation.RequestMapping.class).results()) {
			if (result.code().equals(actionResult))
				return result.to();
		}
		log.error("destination is not found.");
		throw new IllegalStateException();
	}

	protected void forward(HttpServletRequest request,
			HttpServletResponse response, String destination) {
		RequestDispatcher dispatcher = request
				.getRequestDispatcher(new StringBuilder().append(destination)
						.toString());
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			log.error(this.getClass().getCanonicalName(), e);
			redirect(response, ERROR_PAGE);
		}
	}

	protected void redirect(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			log.error(this.getClass().getCanonicalName(), e);
		}
	}

}
