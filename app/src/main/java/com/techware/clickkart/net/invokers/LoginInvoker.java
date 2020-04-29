package com.techware.clickkart.net.invokers;

import android.util.Log;

import com.techware.clickkart.model.AuthBean;
import com.techware.clickkart.net.ServiceNames;
import com.techware.clickkart.net.WebConnector;
import com.techware.clickkart.net.parsers.LoginParser;
import com.techware.clickkart.net.utils.WSConstants;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Jemsheer K D on 28 April, 2017.
 * Package in.techware.dearest.net.invokers
 * Project Dearest
 */

public class LoginInvoker extends BaseInvoker {

    public LoginInvoker() {
        super();
    }

    public LoginInvoker(HashMap<String, String> urlParams,
                        JSONObject postData) {
        super(urlParams, postData);
    }

    public AuthBean invokeLoginWS() {

        Log.i("API", ">>>>>>>> API POSTDATA : " + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service();
        Log.i("API", ">>>>>>>>>>> API response: " + wsResponseString);
        AuthBean authBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return authBean = null;
        } else {
            authBean = new AuthBean();
            LoginParser loginParser = new LoginParser();
            authBean = loginParser.parseLoginResponse(wsResponseString);
            return authBean;
        }
    }
}


