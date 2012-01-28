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
 * @since 2012/01/15
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
