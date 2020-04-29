package com.techware.clickkart.net.invokers;

import android.util.Log;

import com.techware.clickkart.model.BasicBean;
import com.techware.clickkart.net.ServiceNames;
import com.techware.clickkart.net.WebConnector;
import com.techware.clickkart.net.parsers.BasicParser;
import com.techware.clickkart.net.utils.WSConstants;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by Jemsheer K D on 04 May, 2017.
 * Package in.techware.dearest.net.invokers
 * Project Dearest
 */

public class UpdateFCMTokenInvoker extends BaseInvoker {

    public UpdateFCMTokenInvoker() {
        super();
    }

    public UpdateFCMTokenInvoker(HashMap<String, String> urlParams,
                                 JSONObject postData) {
        super(urlParams, postData);
    }

    public BasicBean invokeUpdateFCMTokenWS() {

        Log.i("API", ">>>>>>>> API POSTDATA : " + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.FCM_UPDATE), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service();
        Log.i("API", ">>>>>>>>>>> API response: " + wsResponseString);
        BasicBean basicBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            BasicParser basicParser = new BasicParser();
            basicBean = basicParser.parseBasicResponse(wsResponseString);
            return basicBean;
        }
    }
}
