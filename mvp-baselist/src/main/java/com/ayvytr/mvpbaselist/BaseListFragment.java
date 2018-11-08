package com.ayvytr.mvpbaselist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.ayvytr.baseadapter.wrapper.EmptyWrapperAdapter;
import com.ayvytr.mvpbase.IPresenter;
import com.ayvytr.mvprxlifecycle.BaseMvpFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ayvytr
 */
public abstract class BaseListFragment<P extends IPresenter, T> extends BaseMvpFragment<P>
        implements OnRefreshLoadMoreListener {

    protected int currentPage = 1;
    protected int pageSize = 10;

    protected RecyclerView mRvList;
    protected SmartRefreshLayout mSmartRefreshLayout;
    protected EmptyWrapperAdapter<T> mAdapter;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        findSmartRefreshLayout();
    }

    private void findSmartRefreshLayout() {
        mRvList = mContentView.findViewById(R.id.rv_list);
        mSmartRefreshLayout = mContentView.findViewById(R.id.smart_refresh_layout);
        if(mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        resetPage();
    }

    public void updateList(List<T> list) {

        if(mAdapter == null) {
            return;
        }

        if(list == null || list.isEmpty()) {
            list = new ArrayList<>();
        }

        if(currentPage == 1) {
            mAdapter.updateList(list);
        } else {
            mAdapter.addList(list);
        }

        if(!list.isEmpty()) {
            currentPage++;
        }

        if(mAdapter.isEmpty()) {
            showEmpty();
        }

        finishRefreshLoadMore();
        mSmartRefreshLayout.finishLoadMore(0, true, list.size() != pageSize);
    }

    public void finishRefreshLoadMore() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void showError(int stringId) {
        finishRefreshLoadMore();
        if(mAdapter.isEmpty()) {
            mAdapter.showError(getContext().getString(stringId));
        } else {
            super.showError(stringId);
        }
    }

    @Override
    public void showError(String errorMsg) {
        finishRefreshLoadMore();
        if(mAdapter.isEmpty()) {
            mAdapter.showError(errorMsg);
        } else {
            super.showError(errorMsg);
        }
    }

    /**
     * 刷新列表/重新从第1页开始时调用
     */
    public void resetPage() {
        currentPage = 1;
    }
}
