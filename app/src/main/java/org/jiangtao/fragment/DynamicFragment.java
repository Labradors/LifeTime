package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.jiangtao.lifetime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends android.support.v4.app.Fragment {

    private View mView;
    private ImageView mImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_dynamic, container, false);
        hideImageView();
        return mView;
    }
    private void hideImageView(){
        mImageView = (ImageView) mView.findViewById(R.id.imageview_fragment_dynamic);
        mImageView.setVisibility(View.GONE);
    }
    private void showImageView(){
        mImageView.setVisibility(View.VISIBLE);
    }


}
