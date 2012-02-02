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
package com.googlecode.sonic.entity.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 改行コードは<code><br/></code>に変換する。
 * 
 * @author hisao takahashi
 * @since 1.0
 */
public class LineFeedCode2BRTag extends TagSupport {

	private static final long serialVersionUID = 3653253507197671719L;

	private static final String BREAK_REGEX = "(\r\n|\r|\n)";
	private static final String BREAK_TAG = "<br/>";

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			String value = getValue();
			// value = StringEscapeUtils.escapeHtml4(value);
			// value = value.replaceAll("&", "&amp;").replaceAll(">", "&gt;")
			// .replaceAll("\"", "&quot;").replaceAll("<", "&lt;");
			value = value.replaceAll(BREAK_REGEX, BREAK_TAG);
			out.println(value);
		} catch (IOException e) {
			throw new JspTagException(e);
		}
		return SKIP_BODY;
	}
}
