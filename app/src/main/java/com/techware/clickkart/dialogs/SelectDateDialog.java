package com.techware.clickkart.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.techware.clickkart.R;
import com.techware.clickkart.app.App;
import com.techware.clickkart.config.TypefaceCache;

import java.util.Calendar;


/**
 * Created by Jemsheer K D on 11 January, 2017.
 * Package com.wakilishwa.dialogs
 * Project Wakilishwa
 */

public class SelectDateDialog {

    private final Activity mContext;
    private Typeface typeface;
    //    private final Vibrator mVibrator;
    private SelectDateDialogActionListener selectDateDialogActionListener;
    private Dialog dialogSelectDate;
    private DatePicker datePicker;
    private String currentDate;
    private Calendar currentCal;

    public SelectDateDialog(Activity mContext) {
        this.mContext = mContext;

        try {
            typeface = TypefaceCache.getInstance().getTypeface(mContext.getApplicationContext(), "Roboto-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        setSelectDateDialog();
    }

    public void show() {
        dialogSelectDate.show();
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
        Calendar cal = App.getUserTime(currentDate);
        datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
    }

    public Calendar getCurrentCal() {
        return currentCal;
    }

    public void setCurrentCal(Calendar currentCal) {
        this.currentCal = currentCal;
        datePicker.updateDate(currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH), currentCal.get(Calendar.DAY_OF_MONTH));
    }

    private void setSelectDateDialog() {

        dialogSelectDate = new Dialog(mContext, R.style.ThemeDialogCustom_NoBackground);
        dialogSelectDate.setContentView(R.layout.dialog_select_date);
        dialogSelectDate.setTitle(R.string.label_select_date);

        datePicker = (DatePicker) dialogSelectDate.findViewById(R.id.datePicker_dialog_select_date);

        final Button btnSubmit = (Button) dialogSelectDate.findViewById(R.id.btn_dialog_select_date);
        btnSubmit.setTypeface(typeface);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, datePicker.getYear());
                cal.set(Calendar.MONTH, datePicker.getMonth());
                cal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);

                selectDateDialogActionListener.actionCompletedSuccessfully(cal);
                dialogSelectDate.dismiss();

            }
        });
    }

    public interface SelectDateDialogActionListener {
        void actionCompletedSuccessfully(Calendar cal);

        void actionFailed(String errorMsg);
    }

    public SelectDateDialogActionListener getSelectDateDialogActionListener() {
        return selectDateDialogActionListener;
    }


    public void setSelectDateDialogActionListener(SelectDateDialogActionListener selectDateDialogActionListener) {
        this.selectDateDialogActionListener = selectDateDialogActionListener;
    }
}
