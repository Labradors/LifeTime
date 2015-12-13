package org.jiangtao.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polites.android.GestureImageView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.lifetime.ImageActivity;
import org.jiangtao.lifetime.R;
import org.jiangtao.utils.ConstantValues;

import java.util.ArrayList;

/**
 * Created by mr-jiang on 15-12-13.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private ArrayList<String> mImageArrayList;
    private Context mContext;
    private LayoutInflater mInflater;

    public GalleryAdapter(ArrayList<String> list, Context context) {
        if (list != null && list.size() != 0) {
            mImageArrayList = list;
        } else {
            mImageArrayList = new ArrayList<>();
        }
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_galley, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        LifeApplication.picasso.load(ConstantValues.getArticleImageUrl +
                mImageArrayList.get(position)).into(holder.mImageView);
        holder.ImageItemOnclistener(new ViewHolder.ImageOnClick() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra("image_address", mImageArrayList.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public GestureImageView mImageView;
        public ImageOnClick onClick;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (GestureImageView) itemView.findViewById(R.id.layout_gallery_imageview);
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClick != null) {
                onClick.onItemClicked(v, getLayoutPosition());
            }
        }

        public interface ImageOnClick {
            void onItemClicked(View view, int position);
        }

        public void ImageItemOnclistener(ImageOnClick mImageOnClick) {
            onClick = mImageOnClick;
        }
    }
}
