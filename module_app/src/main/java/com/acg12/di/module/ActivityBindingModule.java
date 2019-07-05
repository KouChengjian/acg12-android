package com.acg12.di.module;


import com.acg12.di.scope.PerActivity;
import com.acg12.ui.activity.DownloadActivity;
import com.acg12.ui.activity.MainActivity;
import com.acg12.ui.activity.RecordActivity;
import com.acg12.ui.activity.SkinActivity;
import com.acg12.ui.activity.setting.AboutActivity;
import com.acg12.ui.activity.setting.FeedbackActivity;
import com.acg12.ui.activity.setting.SettingActivity;
import com.acg12.ui.activity.user.AlterPwdActivity;
import com.acg12.ui.contract.AboutContract;
import com.acg12.ui.contract.AlterPwdContract;
import com.acg12.ui.contract.DownloadContract;
import com.acg12.ui.contract.FeedbackContract;
import com.acg12.ui.contract.MainContract;
import com.acg12.ui.contract.RecordContract;
import com.acg12.ui.contract.SettingContract;
import com.acg12.ui.contract.SkinContract;

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
    @ContributesAndroidInjector(modules = AlterPwdContract.AlterPwdModule.class)
    abstract AlterPwdActivity AlterPwdActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = DownloadContract.DownloadModule.class)
    abstract DownloadActivity DownloadActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = RecordContract.RecordModule.class)
    abstract RecordActivity RecordActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = SkinContract.SkinModule.class)
    abstract SkinActivity SkinActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = SettingContract.SettingModule.class)
    abstract SettingActivity SettingActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = FeedbackContract.FeedbackModule.class)
    abstract FeedbackActivity FeedbackActivity();

    @PerActivity
    @ContributesAndroidInjector(modules = AboutContract.AboutModule.class)
    abstract AboutActivity AboutActivity();
}
