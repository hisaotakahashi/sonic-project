package com.googlecode.sonic.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.googlecode.sonic.constant.ActionResult;



/**
 * 
 * @author hisao takahashi
 * @since 2011/12/07
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Result {

	ActionResult code();
	
    String to();
}
