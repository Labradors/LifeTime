package org.jiangtao.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jiangtao.lifetime.R;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.NoteFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends android.support.v4.app.Fragment {

    private View mView;
    private ListView mNoteListView;
    private TextView mNoteContext;
    private static final String NOTE_PATH = ConstantValues.saveNoteUri;

    ArrayList name;

    public NoteFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_note, container, false);
        controlsInitialize();
        setAdapter();
        return mView;
    }

    private void setAdapter() {
        name = new ArrayList();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File path = new File(ConstantValues.saveNoteUri);
            File[] files = path.listFiles();
            getFileName(files);
        }
        SimpleAdapter adapter = new SimpleAdapter(
                getActivity(),
                name,
                R.layout.listview_note_item,
                new String[]{"name", "context"},
                new int[]{R.id.item_notelistview_title, R.id.item_notelistview_context});
        mNoteListView.setAdapter(adapter);

        mNoteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String content = (String) parent.getItemAtPosition(position).toString();
                Toast toast = Toast.makeText(getActivity(), "你点击了" + content, Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }


    private void getFileName(File[] files) {
        //获取文件名cheng
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
                        if (s.length()>0){
                            Log.i("Rong", "文件名txt：：   " + s);

                            String fileContext = "";//文件内容字符串
                            //打开文件
                            File fileReader = new File(ConstantValues.saveNoteUri + s + ".txt");
                            if (fileReader.isDirectory()) {
                                Log.d("TextFile", "This file doesn't not exist");
                            } else {
                                try {
                                    InputStream instream = new FileInputStream(fileReader);
                                    if (instream != null) {
                                        InputStreamReader inputreader = new InputStreamReader(instream);
                                        BufferedReader buffreader = new BufferedReader(inputreader);
                                        String line;
                                        //分行读取
                                        while ((line = buffreader.readLine()) != null) {
                                            fileContext += line + "\n";
                                        }
                                        instream.close();
                                    }
                                } catch (FileNotFoundException e) {
                                    Log.d("TestFile", "The File doesn't not exist.");
                                } catch (IOException e) {
                                    Log.d("TestFile", e.getMessage());
                                }
                            }
                            File fileTime = new File(ConstantValues.saveNoteUri + s + ".txt");
                            long time = fileTime.lastModified();
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(time);
                            //填充数据
                            map.put("name", fileName.substring(0,
                                    fileName.lastIndexOf(".")));
//                        map.put("context", cal.getTime(), toLocaleString());
                            map.put("context", fileContext.toString());
                            name.add(map);
                            Log.d("===================", ConstantValues.saveNoteUri + s + ".txt");
                        }

                    }
                }
            }
        }
    }

    /**
     * 初始化控件
     */
    private void controlsInitialize() {
        mNoteListView = (ListView) mView.findViewById(R.id.note_listview);
        mNoteContext = (TextView) mView.findViewById(R.id.item_notelistview_context);

    }


}
