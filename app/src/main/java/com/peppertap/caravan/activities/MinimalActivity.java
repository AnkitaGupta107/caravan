package com.peppertap.caravan.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.peppertap.caravan.CaravanApp;
import com.peppertap.caravan.utils.ConfirmAction;
import com.peppertap.caravan.utils.PauseHandler;


/**
 * Created by samvedana on 05/11/15.
 */
public abstract class MinimalActivity extends AppCompatActivity {

    protected CaravanApp globalApplication;
    private AlertDialog alertDialog;

    private ActivityPauseHandler activityPauseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalApplication = (CaravanApp) getApplication();

        activityPauseHandler = new ActivityPauseHandler();
    }

    public void showAlertDialog(String title, String message) {
        if(!isFinishing()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message).setTitle(title);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //do nothing
                }
            });
            alertDialog = builder.create();
            sendBaseHandlerMessage(alertDialog, ActivityPauseHandler.SHOW_ALERT_DIALOG);
        }
    }

    public void showConfirmationDialog(String title, String message, final ConfirmAction confirmAction, String okText, String cancelText) {
        /*
        example :
        title : Confirm Action
        message : Product will be marked In Stock
         */
        if (!isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message).setTitle(title);
            builder.setPositiveButton(okText, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (confirmAction != null) {
                        confirmAction.onConfirm();
                    }
                }
            });
            builder.setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //do nothing
                }
            });
            alertDialog = builder.create();
            sendBaseHandlerMessage(alertDialog, ActivityPauseHandler.SHOW_ALERT_DIALOG);
        }
    }

    public void showConfirmationDialog(String title, String message, final ConfirmAction confirmAction) {

        showConfirmationDialog(title,message,confirmAction,"Ok","Cancel");
    }

    public void sendBaseHandlerMessage(Object messageObj, int what){
        Message message = new Message();
        message.what = what;
        message.obj = messageObj;
        activityPauseHandler.sendMessage(message);
    }

    @Override
    protected void onResume(){
        super.onResume();
        activityPauseHandler.resume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityPauseHandler.pause();
    }


    /*
    * Handler class for preventing UI modification (adding fragments, dialogs) when the activity
    * is paused
    * */
    protected static class ActivityPauseHandler extends PauseHandler {

        public final static int SHOW_ALERT_DIALOG = 1;

        public void processMessage(Object activity, Message message){
            switch(message.what){
                case SHOW_ALERT_DIALOG:
                    ((AlertDialog) message.obj).show();
                    break;
            }
        }
    }
}
