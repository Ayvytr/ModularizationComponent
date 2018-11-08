package com.ayvytr.mvpbaselist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ayvytr.baseadapter.wrapper.EmptyWrapperAdapter;
import com.ayvytr.mvpbase.IPresenter;
import com.ayvytr.mvprxlifecycle.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ayvytr
 */
public abstract class BaseListActivity<P extends IPresenter, T> extends BaseMvpActivity<P>
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
        mRvList = findViewById(R.id.rv_list);
        mSmartRefreshLayout = findViewById(R.id.smart_refresh_layout);
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

    protected void resetPage() {
        currentPage = 1;
    }

    public void updateList(List<T> list) {
        if(mAdapter == null) {
            return;
        }

        if(list == null) {
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

        finishRefreshLoadMore();
        mSmartRefreshLayout.finishLoadMore(0, true, list.size() != pageSize);
    }

    @Override
    public void showLoading() {
        mAdapter.showLoading();
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

    public void finishRefreshLoadMore() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }
}
