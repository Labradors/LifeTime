package org.jiangtao.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mr-jiang on 15-12-7.
 * 根据文章中用户的id号查询用户界面
 */
public class SeeOtherAdapter extends RecyclerView.Adapter<SeeOtherAdapter.ViewHolder> {
    @Override
    public SeeOtherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SeeOtherAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
