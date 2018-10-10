package com.ayvytr.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author admin
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends RxFragment
        implements IView, IInit, OnRefreshLoadMoreListener {

    protected P mPresenter;
    private Unbinder mUnbinder;

    protected int currentPage = 1;
    protected int pageSize = 10;

    protected RecyclerView mRvList;
    protected SmartRefreshLayout mSmartRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int contentViewRes = getContentViewRes();
        if(contentViewRes > 0) {
            return inflater.inflate(contentViewRes, container, false);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = getPresenter();
        mUnbinder = ButterKnife.bind(this, view);
        findSmartRefreshLayout(view);
        initExtra();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * //TODO 后续考虑是否使用BaseListActivity专门实现数据刷新和加载更多功能
     *
     * @param view
     */
    private void findSmartRefreshLayout(View view) {
        mRvList = view.findViewById(R.id.rv_list);
        mSmartRefreshLayout = view.findViewById(R.id.smart_refresh_layout);
        if(mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        }
    }

    protected abstract P getPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
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
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    public void finishRefresh() {
        if(mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
        }
    }
}
