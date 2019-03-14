package com.ayvytr.baselist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
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
    protected int mCurrentPage = 1;
    protected int mPageSize = 10;

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
            mSmartRefreshLayout.setEnableAutoLoadMore(false);
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
        mCurrentPage = 1;
    }

    public void finishRefreshLoadMore() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    /**
     * 更新列表，下拉刷新，加载更多都用这个，callback最好不为空，避免刷新闪烁
     *
     * @param list 数据集合，可以为空
     * @param callback {@link DiffUtil.Callback}
     */
    public void updateList(@Nullable List<T> list, @Nullable DiffUtil.Callback callback) {

        if(mAdapter == null) {
            return;
        }

        if(list == null) {
            list = new ArrayList<>();
        }

        if(mCurrentPage == 1) {
            //下拉刷新使用DiffUtil不会出问题，上拉加载会自动到列表顶部，并且有闪烁
            if(callback != null) {
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);
                mAdapter.updateList(list);
                diffResult.dispatchUpdatesTo(mAdapter);
            } else {
                mAdapter.updateList(list);
                mAdapter.notifyDataSetChanged();
            }
        } else {
            int oldCount = mAdapter.getItemCount();
            mAdapter.addList(list);
            if(oldCount != mAdapter.getItemCount()) {
                mAdapter.notifyItemRangeInserted(oldCount, mAdapter.getItemCount());
            }
        }


        if(mAdapter.getItemCount() == 0) {
            showEmpty();
        } else {
            mStatusView.showContent();
        }

        finishRefreshLoadMore();
        mSmartRefreshLayout.finishLoadMore(0, true, list.size() != mPageSize);
    }

    @Override
    public void showError(int stringId) {
        finishRefreshLoadMore();
        if(mAdapter.getItemCount() == 0) {
            mStatusView.showError(getString(stringId));
        } else {
            super.showError(stringId);
        }
    }

    @Override
    public void showError(String errorMsg) {
        finishRefreshLoadMore();
        if(mAdapter.getItemCount() == 0) {
            mStatusView.showError(errorMsg);
        } else {
            super.showError(errorMsg);
        }
    }

    @Override
    public void showEmpty() {
        super.showEmpty();
        mStatusView.showEmpty();
    }

}
