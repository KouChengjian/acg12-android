package com.liteutil.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.liteutil.orm.db.enums.Relation;

/**
 * 关系映射
 * 
 * @author mty
 * @date 2013-6-8上午1:13:18
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {
	Relation value();
}
