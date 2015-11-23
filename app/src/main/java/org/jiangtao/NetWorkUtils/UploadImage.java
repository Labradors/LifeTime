package org.jiangtao.NetWorkUtils;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.jiangtao.application.LifeApplication;
import org.jiangtao.utils.ConstantValues;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by mr-jiang on 15-11-23.
 * 上传图片
 * 明天的任务
 */
public class UploadImage {
    private String infromation;
    private File file;

    public UploadImage() {
    }

    public UploadImage(String infromation, File file) {
        this.infromation = infromation;
        this.file = file;
    }

    MultipartRequest multipartRequest = new MultipartRequest(Request.Method.GET,
            ConstantValues.registerImageUrl, new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    });
    MultipartEntity multi = multipartRequest.getEntity();
    LifeApplication application = LifeApplication.getInstance();

    /**
     * 设置字符集，但是http无法使用使用
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public boolean uploadImageFuction() throws UnsupportedEncodingException {
        multi.addPart("UserName",
                new StringBody(infromation));

        multi.addPart("FileImage",
                new FileBody(this.file, this.file.getName(),
                        "multipart/form-data"));
        application.getRequestQueue().add(multipartRequest);
        return true;
    }

}
