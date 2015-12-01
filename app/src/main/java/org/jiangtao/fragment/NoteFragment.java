package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jiangtao.lifetime.R;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.NoteFilter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends android.support.v4.app.Fragment{

    private View mView;
    private ListView mNoteListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final String NOTE_PATH= ConstantValues.saveNoteUri;

    ArrayList name;

    public NoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView= inflater.inflate(R.layout.fragment_note, container, false);

        controlsInitialize();

        name=new ArrayList();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File path=new File(ConstantValues.saveNoteUri);
            File[] files=path.listFiles();
            getFileName(files);
        }
        SimpleAdapter adapter=new SimpleAdapter(
                getActivity(),
                name,
                R.layout.listview_note_item,
                new String[]{"name"},
                new int[]{R.id.item_notelistview_title});
        mNoteListView.setAdapter(adapter);

        return mView;

//        SimpleAdapter myNoteAdapter=new SimpleAdapter(
//                getActivity(),
//                getData(),
//                R.layout.listview_note_item,
//                new String[]{"time","content"},
//                new int[]{R.id.item_notelistview_time,R.id.item_notelistview_content}
//        );
//        mNoteListView.setAdapter(myNoteAdapter);

    }

//    private List<Map<String,Object>> getData() {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> map = new HashMap<String, Object>();

//        map=new HashMap<String,Object>();
//        map.put("time","2015-11-30 22:10");
//        map.put("content","This is a test context ,and the style haven't finashed!");
//        list.add(map);
//
//        map=new HashMap<String,Object>();
//        map.put("time","2015-11-30 22:00");
//        map.put("content","今天晚上去中海吃了火锅，新开了一家名为“聚满楼”的火锅店，招牌菜是“翘角子牛肉”，非常辣！");
//        list.add(map);

//        return list;
//    }

    private void getFileName(File[] files){
        if (files != null) {// 先判断目录是否为空，否则会报空指针
            for (File file : files) {
                if (file.isDirectory()) {
                    Log.i("Rong", "若是文件目录。继续读1" + file.getName().toString()
                            + file.getPath().toString());

                    getFileName(file.listFiles());
                    Log.i("Rong", "若是文件目录。继续读2" + file.getName().toString()
                            + file.getPath().toString());
                } else {
                    String fileName = file.getName();
                    if (fileName.endsWith(".txt")) {
                        HashMap map = new HashMap();
                        String s = fileName.substring(0,
                                fileName.lastIndexOf(".")).toString();
                        Log.i("Rong", "文件名txt：：   " + s);
                        map.put("name", fileName.substring(0,
                                fileName.lastIndexOf(".")));
                        name.add(map);
                    }
                }
            }
        }
    }
    /**
     * 初始化控件
     */
    private void controlsInitialize(){
        mNoteListView= (ListView) mView.findViewById(R.id.note_listview);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(
//                R.id.refresh_fragment_note);
    }
//    /**
//     * 设置刷新界面的颜色
//     */
//    private void swipeColorListener() {
//        mSwipeRefreshLayout.setEnabled(true);
//        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_red_light,
//                android.R.color.holo_green_light,
//                android.R.color.holo_blue_light,
//                android.R.color.holo_red_light);
//
//    }
//    /**
//     * 实现刷新操作
//     */
//    @Override
//    public void onRefresh() {
//        swipeColorListener();
//    }

}
