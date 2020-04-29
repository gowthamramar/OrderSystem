package com.techware.clickkart.listeners;


import com.techware.clickkart.model.help.HelpBean;
import com.techware.clickkart.model.reportproblem.ReportBean;

public interface ReportResponseListener {

    void onLoadCompleted(ReportBean reportBean);

    void onLoadFailed(String error);
}
