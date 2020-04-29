package com.techware.clickkart.net.WSAsyncTasks;

import android.os.AsyncTask;

import com.techware.clickkart.model.AuthBean;
import com.techware.clickkart.net.invokers.RegistrationInvoker;

import org.json.JSONObject;


/**
 * Created by Jemsheer K D on 24 April, 2017.
 * Package in.techware.dearest.net.WSAsyncTasks
 * Project Dearest
 */

public class RegistrationTask extends AsyncTask<String, Integer, AuthBean> {

    private RegistrationTaskListener registrationTaskListener;

    private JSONObject postData;

    public RegistrationTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected AuthBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        RegistrationInvoker registrationInvoker = new RegistrationInvoker(null, postData);
        return registrationInvoker.invokeRegistrationWS();
    }

    @Override
    protected void onPostExecute(AuthBean result) {
        super.onPostExecute(result);
        if (result != null)
            registrationTaskListener.dataDownloadedSuccessfully(result);
        else
            registrationTaskListener.dataDownloadFailed();
    }

    public static interface RegistrationTaskListener {
        void dataDownloadedSuccessfully(AuthBean authBean);

        void dataDownloadFailed();
    }

    public RegistrationTaskListener getRegistrationTaskListener() {
        return registrationTaskListener;
    }

    public void setRegistrationTaskListener(RegistrationTaskListener registrationTaskListener) {
        this.registrationTaskListener = registrationTaskListener;
    }
}
