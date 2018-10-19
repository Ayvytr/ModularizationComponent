package com.ayvytr.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapperAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
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

    private void resetPage() {
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
            //TODO 考虑是否添加这个。按常理应该是没数据不应该上拉加载更多
//            mRvList.scrollBy(0, -mSmartRefreshLayout.getRefreshFooter().getView().getHeight());
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
