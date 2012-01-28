
package com.googlecode.sonic.annotation;

import java.lang.annotation.Documented;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.sonic.constant.HttpMethod;


/**
 * 
 * @author hisao takahashi
 * @since 2011/12/07
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

	String to();
	
	Result[] results();
	
	HttpMethod[] method() default {};
	
	@Deprecated
	String interceptorRef() default "";
}
