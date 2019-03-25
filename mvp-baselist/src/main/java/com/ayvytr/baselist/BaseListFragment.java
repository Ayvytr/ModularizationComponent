package com.ayvytr.baselist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.ayvytr.baseadapter.MultiItemTypeAdapter;
import com.ayvytr.customview.loading.StatusView;
import com.ayvytr.mvp.IPresenter;
import com.ayvytr.rxlifecycle.BaseMvpFragment;
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

    protected int mCurrentPage = 1;
    protected int mPageSize = 10;

    protected RecyclerView mRvList;
    protected SmartRefreshLayout mSmartRefreshLayout;
    protected MultiItemTypeAdapter<T> mAdapter;
    protected StatusView mStatusView;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        findSmartRefreshLayout();
    }

    private void findSmartRefreshLayout() {
        mRvList = mContentView.findViewById(R.id.rv_list);
        mSmartRefreshLayout = mContentView.findViewById(R.id.smart_refresh_layout);
        if(mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
            mSmartRefreshLayout.setEnableAutoLoadMore(false);
        }
        mStatusView = mContentView.findViewById(R.id.statusView);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        resetPage();
    }

    public void finishRefreshLoadMore() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    /**
     * 刷新列表/重新从第1页开始时调用
     */
    public void resetPage() {
        mCurrentPage = 1;
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

    @Override
    public void onDestroyView() {
        mStatusView = null;
        mSmartRefreshLayout = null;
        mAdapter.clear();
        mRvList.setLayoutManager(null);
        mRvList.setAdapter(null);
        mRvList = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }
}
