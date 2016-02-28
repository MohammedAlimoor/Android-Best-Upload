package com.alimoor.android_best_uploud;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.Map;

/**
 * Created by Mohammed Alimoor  on 12/29/2015.
 */

public class VolleyUploadManager implements Response.Listener<String>,Response.ErrorListener, MultipartRequest.ProgressVollyListener {

    Context context;
    RequestQueue requestQueue;
    UploadVolleyListener uploadVolleyListener;

    public void setUploadVolleyListener(UploadVolleyListener uploadVolleyListener) {
        this.uploadVolleyListener = uploadVolleyListener;
    }

    public VolleyUploadManager(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }
    public void Upload(String Url, File file, String FilePostName , final Map<String, String> PostParams){
        MultipartRequest multipartRequest=   new MultipartRequest(Url, file, FilePostName, PostParams, this,this,this);

        requestQueue.add(multipartRequest);
    }  public void Upload(String Url, File file, String FilePostName ){
        MultipartRequest multipartRequest=   new MultipartRequest(Url, file, FilePostName, this,this,this);

        requestQueue.add(multipartRequest);
    }
    public void Upload(String url, final Map<String, String> PostParams){
        MultipartRequest multipartRequest=   new MultipartRequest(url,PostParams,this,this,this);
        requestQueue.add(multipartRequest);
    }
    public void Upload(String url){
        MultipartRequest multipartRequest=   new MultipartRequest(url,this,this,this);
        if(uploadVolleyListener !=null)
        requestQueue.add(multipartRequest);
    }
    @Override
    public void onResponse(String response) {
        if(uploadVolleyListener !=null)
         uploadVolleyListener.onResponse(response);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(uploadVolleyListener !=null)
            uploadVolleyListener.onError(error);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onProgress(int progress) {
        if(uploadVolleyListener !=null)
            uploadVolleyListener.onProgress(progress);
    }

}
