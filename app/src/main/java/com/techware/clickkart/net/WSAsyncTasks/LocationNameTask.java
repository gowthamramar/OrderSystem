package com.techware.clickkart.net.WSAsyncTasks;

import android.os.AsyncTask;

import com.techware.clickkart.model.BasicBean;
import com.techware.clickkart.net.invokers.LocationNameInvoker;

import java.util.HashMap;



public class LocationNameTask extends AsyncTask<String, Integer, BasicBean> {

    private LocationNameTaskListener locationNameTaskListener;


    private HashMap<String, String> urlParams = new HashMap<>();

    public LocationNameTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }


    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        LocationNameInvoker locationNameInvoker = new LocationNameInvoker(urlParams, null);
        return locationNameInvoker.invokeLocationNameWS();
    }


    @Override
    protected void onPostExecute(BasicBean result) {

        super.onPostExecute(result);
        if (result != null)
            locationNameTaskListener.dataDownloadedSuccessfully(result);
        else
            locationNameTaskListener.dataDownloadFailed();
    }

    public interface LocationNameTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public LocationNameTaskListener getLocationNameTaskListener() {
        return locationNameTaskListener;
    }


    public void setLocationNameTaskListener(LocationNameTaskListener locationNameTaskListener) {
        this.locationNameTaskListener = locationNameTaskListener;
    }

}

