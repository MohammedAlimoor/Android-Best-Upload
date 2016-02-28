package com.alimoor.android_best_uploud;

import android.webkit.MimeTypeMap;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;


import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Mohammed Alimoor  on 29-12-2015.
 * update  28-2-2016
 */
public class MultipartRequest extends Request<String>   {

    long totalSize = 0;

    private HttpEntity mHttpEntity;

    private Response.Listener mListener;

    File file;
    String FilePostName;

    Map<String, String> PostParams;


    // send  file and PostParams to server
    public MultipartRequest(String url, File file, String FilePostName, final Map<String, String> PostParams,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener,final ProgressVollyListener progressVollyListener) {
        super(Method.POST, url, errorListener);
        this.file = file;
        this.FilePostName = FilePostName;
        this.PostParams = PostParams;
        mListener = listener;
        mHttpEntity = buildMultipartEntity(file,progressVollyListener);
    }
    // send  file only to server
    public MultipartRequest(String url, File file, String FilePostName,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener,final ProgressVollyListener progressVollyListener) {
        super(Method.POST, url, errorListener);

        this.file = file;
        this.FilePostName = FilePostName;
        this.PostParams =null;
        mListener = listener;
        mHttpEntity = buildMultipartEntity(file,progressVollyListener);
    }


    //send only  post to server
    public MultipartRequest(String url, final Map<String, String> PostParams,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener,final ProgressVollyListener progressVollyListener) {
        super(Method.POST, url, errorListener);
        this.PostParams = PostParams;
        mListener = listener;
        mHttpEntity = buildMultipartEntity(null,progressVollyListener);
    }
    public MultipartRequest(String url,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener,final ProgressVollyListener progressVollyListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mHttpEntity = buildMultipartEntity(null,progressVollyListener);
    }

    private HttpEntity buildMultipartEntity(File file, final ProgressVollyListener progressVollyListener) {
        AndroidMultiPartEntity builder = new AndroidMultiPartEntity(new AndroidMultiPartEntity.ProgressListener() {
            @Override
            public void transferred(long num) {
                progressVollyListener.onProgress((int) ((num / (float) totalSize) * 100));

            }

        });
        progressVollyListener.onStart();
        Charset chars = Charset.forName("UTF-8");

        if(PostParams!=null)
        {
            for (Map.Entry<String,String> entry : PostParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                try {

                    builder.addPart(""+key, new StringBody(value, chars));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
//        try {
//
//            builder.addPart("username", new StringBody(username, chars));
//            builder.addPart("password", new StringBody(password, chars));
//            builder.addPart("oid", new StringBody(oid, chars));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }


        if (file != null) {
            FileBody fileBody = new FileBody(file,getMimeType(file.getAbsolutePath()));
            builder.addPart(FilePostName, fileBody);

        }
        totalSize = builder.getContentLength();

        return builder;
    }

    @Override
    public String getBodyContentType() {
        return mHttpEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mHttpEntity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        //return Response.success(parsed, parseIgnoreCacheHeaders(response));

        return Response.success(parsed, getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

 public interface  ProgressVollyListener{
     public void onStart();
     public void onProgress(int progress);

 }



}