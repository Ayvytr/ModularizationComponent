package com.ayvytr.mvp;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;


/**
 * @author ayvytr
 */
public interface IInit {

    void initExtra();

    /**
     * 初始化 View, 如果 {@link #initView(Bundle)} 返回 0, 框架则不会调用 {@link Activity#setContentView(int)}
     *
     * @param savedInstanceState
     * @return
     */
    void initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(@Nullable Bundle savedInstanceState);

    @LayoutRes
    int getContentViewRes();

    /**
     * 是否使用 {@link com.ayvytr.customview.loading.StatusView}，如果不需要这个接口，直接注释，然后构建，所有重写了这个方法
     * 的地方都会报错.
     *
     * @return {@code true } 使用 {@link com.ayvytr.customview.loading.StatusView}
     */
    boolean useStatusView();
}
