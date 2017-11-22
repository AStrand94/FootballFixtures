package com.example.astrand.footballfixtures.rest_service;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class HttpErrorHandler {

    private static final String TAG = "NO CONNECTION:";

    public static void handle(Activity activity, Throwable throwable, String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (message != null && !message.isEmpty()) builder.setMessage(message);
        else builder.setMessage("An error occured, please try again");

        builder.setTitle("Error");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

        if (throwable != null) Log.d(TAG, "handle: " + throwable.getMessage());

    }

    public static void handle(Activity activity, Throwable throwable){
        handle(activity,throwable,null);
    }

    public static void handle(Activity activity, String message){
        handle(activity,null,message);
    }

    public static void handle(Activity activity){
        handle(activity,null,null);
    }
}
