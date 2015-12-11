package org.jiangtao.lifetime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.polites.android.GestureImageView;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.ConstantValues;

import java.io.IOException;

public class ImageActivity extends AppCompatActivity {
    GestureImageView imageView;
    Bitmap bitmap;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x111) {
                imageView.setImageBitmap(bitmap);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_image);
        imageView = (GestureImageView) findViewById(R.id.image);
        Intent intent = getIntent();
        String url = intent.getStringExtra("image_address");
        new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {
                String url = params[0];
                try {
                    bitmap = LifeApplication.picasso.load(ConstantValues.getArticleImageUrl
                            + url).get();
                    mHandler.sendEmptyMessage(0x111);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
