package com.ayvytr.rxlifecycle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ayvytr.mvp.IInit;
import com.ayvytr.mvp.IPresenter;
import com.ayvytr.mvp.IView;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * @author ayvytr
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends RxFragment
        implements IView, IInit {
    protected View mContentView;
    protected boolean isViewCreated;

    protected P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int contentViewRes = getContentViewRes();
        if(contentViewRes > 0) {
            mContentView = inflater.inflate(contentViewRes, container, false);
        }

        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        mPresenter = getPresenter();
        initExtra();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    protected P getPresenter() {
       return null;
    }

    //ViewPager+Fragment使用时，切换Fragment时，Fragment调用了onDestroyView, onStop, 但是并未调用onDestroy，会有内存泄漏.
    // 所以把Presenter.onDestroy迁移到这里，后续有问题再进行处理
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mContentView = null;
        isViewCreated = false;
        if(mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return 0;
    }
}
