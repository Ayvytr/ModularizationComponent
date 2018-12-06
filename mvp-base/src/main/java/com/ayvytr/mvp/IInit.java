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
     * @param savedInstanceState Bundle
     */
    void initView(Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState Bundle
     */
    void initData(@Nullable Bundle savedInstanceState);

    /**
     * 获取布局文件
     *
     * @return Layout id
     */
    @LayoutRes
    int getContentViewRes();

}
