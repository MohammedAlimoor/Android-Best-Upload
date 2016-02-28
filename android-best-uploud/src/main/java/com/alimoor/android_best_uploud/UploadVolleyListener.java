package com.alimoor.android_best_uploud;

/**
 * Created by Mohammed Alimoor  on 12/29/2015.
 */
public interface UploadVolleyListener {
    public  void onResponse(String response);
    public  void onError(Exception e);
    public  void onProgress(int progress);


}
