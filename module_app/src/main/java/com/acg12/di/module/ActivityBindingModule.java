package com.acg12.di.module;


import com.acg12.di.scope.PerActivity;
import com.acg12.ui.activity.MainActivity;
import com.acg12.ui.activity.setting.SettingActivity;
import com.acg12.ui.contract.MainContract;
import com.acg12.ui.contract.SettingContract;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityManageBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these
 * subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 * We are also telling Dagger.Android that this generated SubComponent needs to include the specified modules and be aware of a scope annotation @ActivityScoped
 * When Dagger.Android annotation processor runs it will create 4 subcomponents for us.
 */
@Module
public abstract class ActivityBindingModule {

    @PerActivity
    @ContributesAndroidInjector(modules = MainContract.MainModule.class)
    abstract MainActivity MainActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = SettingContract.SettingModule.class)
    abstract SettingActivity SettingActivity();

}
