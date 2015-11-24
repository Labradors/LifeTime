package org.jiangtao.lifetime;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionActivity extends AppCompatActivity {

    private ListView mCollectionLitView;

    private ImageView mCollectionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        mCollectionLitView= (ListView) findViewById(R.id.collection_listview);


        SimpleAdapter mAdapter=new SimpleAdapter(
                this,
                getData(),
                R.layout.collection_listview_itme,
                new String[]{"userlooks","username","publishtime","content","image"},
                new int[]{R.id.collecton_userlooks,R.id.collection_username,R.id.collection_publishtime,R.id.collection_content,R.id.collection_image}
        );
        mCollectionLitView.setAdapter(mAdapter);
//        setvisiabe();
    }

    private List<Map<String,Object>> getData() {
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        Map<String,Object>map=new HashMap<String , Object>();

        map=new HashMap<String,Object>();
        map.put("userlooks",R.drawable.looks);
        map.put("username","王豪");
        map.put("publishtime", "2015-11-24");
        map.put("content", "人生在世，高潮时享受成就，低谷时享受人生。机制不对，好人受累；流程不对，努力白费。遇顺境，处之淡然；遇逆境，处之泰然。人生如果走错了方向，止步就是进步。");
        map.put("image", R.drawable.aaaaaa);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("userlooks",R.drawable.looks);
        map.put("username","王豪");
        map.put("publishtime", "2015-11-24");
        map.put("content", "人生在世，高潮时享受成就，低谷时享受人生。机制不对，好人受累；流程不对，努力白费。遇顺境，处之淡然；遇逆境，处之泰然。人生如果走错了方向，止步就是进步。");
        map.put("image", null);
        list.add(map);

        map=new HashMap<String,Object>();
        map.put("userlooks",R.drawable.looks);
        map.put("username","王豪");
        map.put("publishtime", "2015-11-24");
        map.put("content", "人生在世，高潮时享受成就，低谷时享受人生。机制不对，好人受累；流程不对，努力白费。遇顺境，处之淡然；遇逆境，处之泰然。人生如果走错了方向，止步就是进步。");
        map.put("image", R.drawable.bbbbbbb);
        list.add(map);

        return list;

    }

    /**
     * 当图片为空是，设置imageview的属性为 invisiable。
     */
//    private void setvisiabe(){
//
//        mCollectionImage= (ImageView) findViewById(R.id.collection_image);
//        Drawable drawable=mCollectionImage.getDrawable();
//
//        if (drawable==null){
//            mCollectionImage.setVisibility(View.INVISIBLE);
//
//        };
//
//    }

}
