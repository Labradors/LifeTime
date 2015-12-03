package org.jiangtao.layoutmanager;

import android.support.v7.widget.RecyclerView;

/**
 * Created by mr-jiang
 * on 15-12-3.
 * 自定义布局管理器
 */
public class DynamicLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
    }
}
