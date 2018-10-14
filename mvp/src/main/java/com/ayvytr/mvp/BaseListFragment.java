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
            //TODO 考虑是否添加这个。按常理应该是没数据不应该上拉加载更多
//            int height = mSmartRefreshLayout.getRefreshFooter().getView().getHeight();
//            mRvList.scrollBy(0, -height);
        } else {
            mAdapter.addList(list);
        }

        //没有数据不应该增长currentPage
        //TODO 审核什么时候不应该增长currentPage
        if(!list.isEmpty()) {
            currentPage++;
        }

        finishRefreshLoadMore();
        mSmartRefreshLayout.finishLoadMore(0, true, list.size() != pageSize);
    }

    public void finishRefreshLoadMore() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    /**
     * 刷新列表/重新从第1页开始时调用
     */
    public void resetPage() {
        currentPage = 1;
    }
}
