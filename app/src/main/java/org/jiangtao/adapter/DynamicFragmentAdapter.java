package org.jiangtao.adapter;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by mr-jiang on 15-11-28.
 * 创建动态fragment的适配器
 */
public class DynamicFragmentAdapter extends SimpleAdapter {

    public DynamicFragmentAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}
