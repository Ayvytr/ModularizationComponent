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
public abstract class BaseListFragment<P extends IPresenter> extends BaseMvpFragment<P> implements OnRefreshLoadMoreListener {

    protected int currentPage = 1;
    protected int pageSize = 10;

    protected RecyclerView mRvList;
    protected SmartRefreshLayout mSmartRefreshLayout;
    protected EmptyWrapperAdapter mAdapter;

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
    }

    public void updateList(List list) {
        if(mAdapter == null) {
            return;
        }

        if(list == null) {
            list = new ArrayList();
        }

        if(currentPage == 1) {
            mAdapter.updateList(list);
        } else {
            mAdapter.addList(list);
        }

        currentPage++;

        mSmartRefreshLayout.setEnableLoadMore(list.size() == pageSize);
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }
}
