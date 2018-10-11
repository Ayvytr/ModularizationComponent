package com.zhy.adapter.recyclerview.wrapper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.utils.WrapperUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhy on 16/6/23.
 */
public abstract class EmptyWrapperAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

    //    private View mEmptyView;
    private int mEmptyLayoutId;

    protected CommonAdapter<T> mInnerAdapter;
    protected Context mContext;


//    public EmptyWrapperAdapter(RecyclerView.Adapter adapter) {
//        mInnerAdapter = adapter;
//    }

    public EmptyWrapperAdapter(Context context, @LayoutRes int layoutResId) {
        this(context, layoutResId, new ArrayList<T>(0));
    }

    public EmptyWrapperAdapter(Context context, @LayoutRes int layoutResId, List<T> list) {
        mContext = context;
        mInnerAdapter = new CommonAdapter<T>(context, layoutResId, list) {
            @Override
            protected void convert(ViewHolder holder, T t, int position) {
                EmptyWrapperAdapter.this.convert(holder, t, position);
            }
        };
    }

    public abstract void convert(ViewHolder holder, T t, int position);

    public RecyclerView.Adapter getAdapter() {
        return mInnerAdapter;
    }

    public boolean isEmpty() {
//        return (mEmptyView != null || mEmptyLayoutId != 0) && mInnerAdapter.getItemCount() == 0;
        return mInnerAdapter.getItemCount() == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(isEmpty()) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);

            //TODO 修改优化
            //如果View为空提供空View
            if(holder.itemView == null) {
                holder = ViewHolder.createViewHolder(parent.getContext(), new View(mContext));
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup,
                                   int position) {
                if(isEmpty()) {
                    return gridLayoutManager.getSpanCount();
                }
                if(oldLookup != null) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow((ViewHolder) holder);
        if(isEmpty()) {
            WrapperUtils.setFullSpan(holder);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(isEmpty()) {
            return ITEM_TYPE_EMPTY;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isEmpty()) {
            return;
        }
        mInnerAdapter.onBindViewHolder((ViewHolder) holder, position);
    }

    /**
     * 如果 {@link #mInnerAdapter} 为空，获取的数量为1，需要配合 {@link #isEmpty()} 判断 {@link #mInnerAdapter} 的数据数量.
     * 必须这样写，不然空视图无法加载.
     * <p>
     * 可以使用如下方法直接获取数量:
     *
     * @see #getItemCountOuter()
     * @see #mInnerAdapter#getItemCount()
     */
    @Override
    public int getItemCount() {
        if(isEmpty()) {
            return 1;
        }
        return mInnerAdapter.getItemCount();
    }

    public int getItemCountOuter() {
        return mInnerAdapter.getItemCount();
    }

//    public void setEmptyView(View emptyView) {
//        mEmptyView = emptyView;
//    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }

    public void updateList(List<T> list) {
        //TODO 解决闪烁问题
        notifyDataSetChanged();
        mInnerAdapter.updateList(list == null ? new ArrayList<T>(0) : list);
    }

    public void addList(List<T> list) {
//        notifyDataSetChanged();
        mInnerAdapter.addList(list);
//        notifyItemRangeChanged(0, getItemCount());
    }

    public void addList(int index, List<T> list) {
//        notifyDataSetChanged();
        mInnerAdapter.addList(index, list);
//        notifyItemRangeChanged(0, getItemCount());
    }

    public void remove(T t) {
//        notifyDataSetChanged();
        mInnerAdapter.remove(t);

        if(isEmpty()) {
            notifyDataSetChanged();
        }
//        } else {
//            notifyItemRangeChanged(0, getItemCount());
//        }
    }

    public void remove(int index) {
//        notifyDataSetChanged();
        mInnerAdapter.remove(index);

//        if(isEmpty()) {
//            notifyDataSetChanged();
//        } else {
//            notifyItemRangeChanged(0, getItemCount());
//        }
    }

    public void clear() {
//        notifyDataSetChanged();
        mInnerAdapter.clear();
        notifyDataSetChanged();
    }

    public CommonAdapter<T> getInnerAdapter() {
        return mInnerAdapter;
    }

    public void setOnItemClickListener(MultiItemTypeAdapter.OnItemClickListener l) {
        mInnerAdapter.setOnItemClickListener(l);
    }

    public T getItemAt(int position) {
        return mInnerAdapter.getItemAt(position);
    }
}