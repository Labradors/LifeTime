package org.jiangtao.lifetime;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.BitmapUtils;
import org.jiangtao.utils.Code;
import org.jiangtao.utils.ConstantValues;
import org.jiangtao.utils.LogUtils;

import java.io.IOException;

/**
 * 写动态
 */
public class WriteDynamicActivity extends AppCompatActivity {

    private static final String TAG = WriteDynamicActivity.class.getSimpleName();
    private LinearLayout mLinearLayout;
    private EditText mEditText;
    private ImageView mImageView;
    //照片地址
    public String mImageAddress = "";
    //用户所写文章的内容
    public String mArticleContent = "";

    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_dynamic);
        initControl();
    }

    private void initControl() {
        mEditText = (EditText) findViewById(R.id.et_write_dynamic);
        mImageView = (ImageView) findViewById(R.id.gl_write_dynamic);
        ImageButton mImageButton = (ImageButton) findViewById(R.id.ibtn_write_dynamic);
        Button mButton = (Button) findViewById(R.id.btn_send_dynamic);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_write_dynamic);
    }

    /**
     * 两个按钮的监听器
     *
     * @param v
     */
    public void dynamicOnClick(View v) {
        switch (v.getId()) {
            //点击获取图片地址，并且显示到gridview
            case R.id.ibtn_write_dynamic: {
                openGallery();
                break;
            }
            //点击发送
            case R.id.btn_send_dynamic: {
                //上传服务器
                uploadServers();
                break;
            }
        }
    }

    /**
     * 上传服务器
     */
    private void uploadServers() {
        if (LifeApplication.hasNetWork) {
            getArticleContent();
            LogUtils.d(TAG, ">>>" + mArticleContent);
            LogUtils.d(TAG, "<<<" + mImageAddress);
            if (mArticleContent != "" || mImageAddress != "") {
                //上传到服务器
                LogUtils.d(TAG, ">>>" + mArticleContent);
                boolean isTrue = org.jiangtao.networkutils.UploadDynamic.uploadDynamic(ConstantValues.uploadImageArticleUrl
                        , mArticleContent, mImageAddress);
                if (isTrue) {
                    finish();
                } else {
                    Snackbar.make(mLinearLayout, R.string.article_info_error, Snackbar
                            .LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(mLinearLayout, R.string.article_info_error, Snackbar
                        .LENGTH_SHORT).show();
            }
        } else {
            Snackbar.make(mLinearLayout, R.string.article_network_error, Snackbar
                    .LENGTH_SHORT).show();
        }
    }

    /**
     * 打开相册
     */
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, Code.REQUESTCODE_OPEN_GALLERY);
    }

    /**
     * 得到返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Code.REQUESTCODE_OPEN_GALLERY) {
            if (resultCode == RESULT_OK) {
                ContentResolver resolver = getContentResolver();
                imageUri = data.getData();
                String imageName = imageUri.getPath();
                LogUtils.d(TAG, imageName);
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(resolver, imageUri);
                    DisplayMetrics metrics = getResources().getDisplayMetrics();
                    int s_width = metrics.widthPixels;
                    int s_height = metrics.heightPixels;
                    Bitmap zoomBitmap = BitmapUtils.zoomBitmap(imageBitmap, s_width / 3, s_height / 5);
                    mImageView.setImageBitmap(zoomBitmap);
                    //保存到本地或者获取照片的地址
                    mImageAddress = getPath(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {

        }
    }

    /**
     * 根据uri获取文件的真是路径
     *
     * @param uri
     * @return
     */
    public String getPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};

        Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);

        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        actualimagecursor.moveToFirst();

        String img_path = actualimagecursor.getString(actual_image_column_index);
        LogUtils.d(TAG, img_path);
        return img_path;
    }

    public void getArticleContent() {
        mArticleContent = mEditText.getText().toString().trim();
    }
}
