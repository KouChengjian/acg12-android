package com.acg12.di.module;


import dagger.Module;

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

//    @PerActivity
//    @ContributesAndroidInjector(modules = SplashContract.SplashModule.class)
//    abstract SplashActivity SplashActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = RegisterContract.RegisterModule.class)
//    abstract RegisterActivity RegisterActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = {LoginContract.LoginModule.class, DaoModule.class})
//    abstract LoginActivity loginActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = LoginWXContract.LoginWXModule.class)
//    abstract LoginWXActivity LoginWXActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = LoginZFBContract.LoginZFBModule.class)
//    abstract LoginZFBActivity LoginZFBActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = ResetPwdContract.ResetPasswordModule.class)
//    abstract ResetPwdActivity ResetPasswordActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = ResetPwdInputContract.ResetPwdInputModule.class)
//    abstract ResetPwdInputActivity ResetPwdInputActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = MainClientContract.ClientMainModule.class)
//    abstract MainClientActivity ClientMainActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = DiningHallInfoContract.DiningHallInfoModule.class)
//    abstract DiningHallInfoActivity DiningHallInfoActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = DiningHallTableContract.DiningHallTableModule.class)
//    abstract DiningHallTableActivity DiningHallTableActivity();
//
//    @PerActivity
//    @ContributesAndroidInjector(modules = SearchCityContract.SearchCityModule.class)
//    abstract SearchCityActivity SearchCityActivity();
}
