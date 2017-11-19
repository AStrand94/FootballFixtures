package com.example.astrand.footballfixtures.rest_service;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

class RestClient {
    //FOR POST: client.post(_,_,_);

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.addHeader("X-Auth-Token","d386564b86bd4c9386dc3d92f73a06fd");
        client.setEnableRedirects(true,true,true);
    }

    static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }
}
