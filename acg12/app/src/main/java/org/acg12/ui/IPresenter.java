package org.acg12.ui;

import android.os.Bundle;

/**
 * Presenter的根接口<br />
 */
public interface IPresenter<T> {
    /**
     * 获取当前presenter泛型的类型
     * @return
     */
    Class<T> getViewClass();

    /**
     * View初始化之前可以在此方法做一些操作
     */
    void create(Bundle savedInstance);

    /**
     * View初始化完毕后调用
     */
    void created(Bundle savedInstance);
}
