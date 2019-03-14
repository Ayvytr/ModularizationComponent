package com.ayvytr.baselist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.ayvytr.baseadapter.MultiItemTypeAdapter;
import com.ayvytr.customview.loading.StatusView;
import com.ayvytr.mvp.IPresenter;
import com.ayvytr.rxlifecycle.BaseMvpActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ayvytr
 */
public abstract class BaseListActivity<P extends IPresenter, T> extends BaseMvpActivity<P>
        implements OnRefreshLoadMoreListener {
    protected int currentPage = 1;
    protected int pageSize = 10;

    protected StatusView mStatusView;
    protected RecyclerView mRvList;
    protected SmartRefreshLayout mSmartRefreshLayout;
    protected MultiItemTypeAdapter<T> mAdapter;


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
        mStatusView = findViewById(R.id.statusView);
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

    /**
     * 更新列表，下拉刷新，加载更多都用这个
     *
     * @param list 数据集合，可以为空
     */
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
            //用户自行处理，这里自动处理有问题
//            if(!list.isEmpty()) {
//                currentPage++;
//            }
        }

//        if(mAdapter.isEmpty()) {
//            showEmpty();
//        }

        finishRefreshLoadMore();
        mSmartRefreshLayout.finishLoadMore(0, true, list.size() != pageSize);
    }

    @Override
    public void showLoading() {
//        mAdapter.showLoading();
    }

    @Override
    public void showError(int stringId) {
        finishRefreshLoadMore();
//        if(mAdapter.isEmpty()) {
//            mAdapter.showError(getContext().getString(stringId));
//        } else {
//            super.showError(stringId);
//        }
    }

    @Override
    public void showError(String errorMsg) {
        finishRefreshLoadMore();
//        if(mAdapter.isEmpty()) {
//            mAdapter.showError(errorMsg);
//        } else {
//            super.showError(errorMsg);
//        }
    }

    public void finishRefreshLoadMore() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }
}
