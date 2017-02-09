package org.acg12.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * View层的根接口 
 */
public interface IView {
    /**
     * 根据 getLayoutId 方法生成生成setContentView需要的根布局
     */
    View create(LayoutInflater inflater , ViewGroup container);

    /**
     * 当Activity的onCreate完毕后调用
     */
    void created();

    /**
     * 返回当前视图需要的layout的id
     */
    int getLayoutId();

    /**
     * 根据id获取view
     */
    <V extends View> V findViewById(int id);

    /**
     * 绑定Presenter
     */
    void bindPresenter(IPresenter presenter);

    /**
     * created 后调用，可以调用click
     * 等方法为控件设置点击事件，一般推荐使用click(IPresenter presenter, View ...views
     * 方法并且让你的Presenter实现相应接口。
     */
    void bindEvent();

//    void destroy();
}
