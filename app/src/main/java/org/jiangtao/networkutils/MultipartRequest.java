package org.jiangtao.networkutils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.mime.MultipartEntity;
import org.jiangtao.utils.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mr-jiang on 15-11-23.
 * volley本身不具备上传文件的类
 * 需要导入httpmine包，并且在volley中使用
 * 重写Request<String>，上传图片
 */
public class MultipartRequest extends Request<String> {
    private Response.Listener<String> mListener;
    Map<String, String> mHeaders = new HashMap<String, String>();
    MultipartEntity entity = new MultipartEntity();

    public MultipartRequest(String url, Response.ErrorListener listener) {
        super(url, listener);
    }

    public MultipartRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    public MultipartRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    public MultipartEntity getEntity() {
        return entity;
    }

    @Override
    public String getBodyContentType() {
        return super.getBodyContentType();
    }

    public void addHeader(String key, String value) {
        mHeaders.put(key, value);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // multipart body
            entity.writeTo(bos);
        } catch (IOException e) {
            LogUtils.e("", "ByteArrayOutputStream IO异常");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed = "";
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }
}
