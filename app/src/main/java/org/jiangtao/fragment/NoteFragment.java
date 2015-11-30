package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jiangtao.lifetime.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends android.support.v4.app.Fragment {

    private View mView;
    private ListView mNoteListView;

    public NoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.fragment_note, container, false);

        mNoteListView= (ListView) mView.findViewById(R.id.note_listview);

        SimpleAdapter myNoteAdapter=new SimpleAdapter(
                getActivity(),
                getData(),
                R.layout.listview_note_item,
                new String[]{"time","content"},
                new int[]{R.id.item_notelistview_time,R.id.item_notelistview_content}
        );
        mNoteListView.setAdapter(myNoteAdapter);
        return mView;
    }
    private List<Map<String,Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();

        map=new HashMap<String,Object>();
        map.put("time","2015-11-30 22:10");
        map.put("content","This is a test context ,and the style haven't finashed!");
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("time","2015-11-30 22:00");
        map.put("content","今天晚上去中海吃了火锅，新开了一家名为“聚满楼”的火锅店，招牌菜是“翘角子牛肉”，非常辣！");
        list.add(map);

        return list;
    }

}
