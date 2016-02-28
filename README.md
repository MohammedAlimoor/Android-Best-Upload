# Android-Best-Upload   Volley   http://ameralfedf.github.io/Android-Best-Upload
Easy library  use  for  upload file from  android app to server  
* Based On Volley [Website](http://ameralfedf.github.io/Android-Best-Upload/)
* implement useful  method :-  onProgress , onResponse , onError

## How To Use
````java
   VolleyUploadManager volleyManager=new VolleyUploadManager(this);

        File file=new File(" path file  in mobile  or uri :)");

        volleyManager.Upload("url server ",file,"file_post_parameter_name");

        volleyManager.setUploadVolleyListener(new UploadVolleyListener() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
````
  
### Send file with post parameter to server 
````java
        VolleyUploadManager volleyManager=new VolleyUploadManager(this);
        
        Map<String, String> Post_params = new HashMap<String, String>();
        Post_params.put("username","------");
        Post_params.put("password","------");
        File file=new File(" path file  in mobile  or uri :)");
        volleyManager.Upload("url server ",file,"file_post_parameter_name",Post_params);
        volleyManager.setUploadVolleyListener(new UploadVolleyListener() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
````
###(EX) Receive file in Php  
 ````php
 $_FILES['file_post_parameter_name'];
 ````
 
