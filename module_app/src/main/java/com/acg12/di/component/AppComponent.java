package com.acg12.di.component;

import android.app.Application;


import com.acg12.app.AppApplication;
import com.acg12.di.module.ActivityBindingModule;
import com.acg12.di.module.ApiModule;
import com.acg12.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


/**
 * Create by AItsuki on 2018/7/14.
 * @Inject：此注解用于告诉Dagger2，我们需要这个类的实例对象。主要用于标记哪个类是需要注入的。
 * @Module：此注解里面全是方法，用于对外提供对象，自己定义方法，方法上使用@Provides。自定义一个类，以Module结尾，用@Module注解。
 * @Provides：此注解用于标记方法，表示可以通过这个方法获取一个对象，一般用于自定义类中。
 * @Component：此注解主要用于关联自定义module类和MainActivity；关联module使用：@Component(modules={UserModule.class})；关联Activity，以方法参数的形式传入MainActivity到连接器中
 * @Named与@Qualifier：用于区别不同对象的实例。必须要成对出现，否则会报错。
 * @PerActivity：限定对象的生命周期和Activity一样。一般应用于自定义的Component上。
 * @Singleton：标记为单例模式，如果在自定义Module中使用了此注解，在自定义的Component上也要使用该注解。
 */
@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        ActivityBindingModule.class,
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<AppApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder app(Application application);

        AppComponent build();
    }
}
