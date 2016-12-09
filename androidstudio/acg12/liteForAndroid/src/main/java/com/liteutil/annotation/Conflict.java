package com.liteutil.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.liteutil.orm.db.enums.Strategy;

/**
 * 冲突策略
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Conflict {
    public Strategy value();
}
