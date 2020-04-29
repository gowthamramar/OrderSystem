package com.techware.clickkart.net.invokers;

import android.util.Log;

import com.techware.clickkart.model.BasicBean;
import com.techware.clickkart.net.ServiceNames;
import com.techware.clickkart.net.WebConnector;
import com.techware.clickkart.net.parsers.LocationNameParser;
import com.techware.clickkart.net.utils.WSConstants;

import org.json.JSONObject;

import java.util.HashMap;


public class LocationNameInvoker extends BaseInvoker {

    public LocationNameInvoker() {
        super();
    }

    public LocationNameInvoker(HashMap<String, String> urlParams,
                               JSONObject postData) {
        super(urlParams, postData);
    }

    public BasicBean invokeLocationNameWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.LOCATION_NAME),
                WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service(true);
        Log.i("API", ">>>>>>>>>>> API response: " + wsResponseString);
        BasicBean basicBean = null;
        String address = null;
        if (wsResponseString.equals("")) {
			/*registerBean=new RegisterBean();
			registerBean.setWebError(true);*/
            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            LocationNameParser dummyParser = new LocationNameParser();
            address = dummyParser.parseLocationNameResponse(wsResponseString);
            basicBean.setStatus("Success");
            basicBean.setAddress(address);
            return basicBean;
        }
    }
}

