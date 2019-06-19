package com.acg12.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Create by AItsuki on 2018/7/14.
 */
@Module
public abstract class AppModule {

    @Binds
    abstract Context bindContext(Application application);

}
