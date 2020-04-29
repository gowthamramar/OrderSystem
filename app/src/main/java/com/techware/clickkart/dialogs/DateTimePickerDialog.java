package com.techware.clickkart.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.ViewFlipper;

import com.techware.clickkart.R;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * Created by Jemsheer K D on 06/01/2016.
 * Package com.abstractbc.wayay.dialogs
 * Project Wayay
 */
public class DateTimePickerDialog {

    private final Activity mContext;
    private DateTimePickerDialogActionListener dateTimePickerDialogActionListener;
    private Dialog dialogDate;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Calendar currentDate;
    private Calendar cal;
    private Calendar tempCal;
    private ViewFlipper viewFlipper;
    private String dateTimeSelected;

    public DateTimePickerDialog(Activity mContext) {
        this.mContext = mContext;

        setDateTimePickerDialog();
    }

    public void show() {
        tempCal = Calendar.getInstance();
        dialogDate.show();
    }

    public void setCurrentDate(Calendar currentDate) {
        this.currentDate = currentDate;
        datePicker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        /*timePicker.setHour(cal.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(cal.get(Calendar.MINUTE));*/
    }



    private void setDateTimePickerDialog() {

        dialogDate = new Dialog(mContext, R.style.ThemeDialogCustom_NoBackground);
        dialogDate.setContentView(R.layout.dialog_date_time_picker);
        dialogDate.setTitle("Select Start Date and Time : ");

        Button btnSubmit = (Button) dialogDate.findViewById(R.id.btn_dialog_date_time_picker_submit);
        Button btnNext = (Button) dialogDate.findViewById(R.id.btn_dialog_date_time_picker_next);

        datePicker = (DatePicker) dialogDate.findViewById(R.id.datePicker_dialog_date_time_picker);
        timePicker = (TimePicker) dialogDate.findViewById(R.id.timePicker_dialog_date_time_picker);

        viewFlipper = (ViewFlipper) dialogDate.findViewById(R.id.viewflipper_dialog_date_time_picker);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                viewFlipper.setDisplayedChild(1);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                Calendar now = Calendar.getInstance();
                boolean flag = true;


                tempCal.set(Calendar.YEAR, datePicker.getYear());
                tempCal.set(Calendar.MONTH, datePicker.getMonth());
                tempCal.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                tempCal.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                tempCal.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                cal.setTimeZone(TimeZone.getDefault());
                cal = tempCal;
                String str = "";
                str += cal.get(Calendar.YEAR);
                str += "-" + (cal.get(Calendar.MONTH) + 1);
                str += "-" + cal.get(Calendar.DAY_OF_MONTH);
                str += " " + cal.get(Calendar.HOUR_OF_DAY);
                str += ":" + cal.get(Calendar.MINUTE) + ":00";
                // txtDateStart.setText(str);

                System.out.println(str);
                //	dateStart=""+dateFormatLocal.parse(dateFormatGmt.format(new Date(cal.getTimeInMillis())));
                cal.setTimeZone(TimeZone.getTimeZone("GMT"));
                str = "";
                str += cal.get(Calendar.YEAR);
                if (("" + (cal.get(Calendar.MONTH) + 1)).length() == 1)
                    str += "-0" + (cal.get(Calendar.MONTH) + 1);
                else
                    str += "-" + (cal.get(Calendar.MONTH) + 1);
                if (("" + (cal.get(Calendar.DAY_OF_MONTH))).length() == 1)
                    str += "-0" + cal.get(Calendar.DAY_OF_MONTH);
                else
                    str += "-" + cal.get(Calendar.DAY_OF_MONTH);
                if (("" + (cal.get(Calendar.HOUR_OF_DAY))).length() == 1)
                    str += " 0" + cal.get(Calendar.HOUR_OF_DAY);
                else
                    str += " " + cal.get(Calendar.HOUR_OF_DAY);
                if (("" + (cal.get(Calendar.MINUTE))).length() == 1)
                    str += ":0" + cal.get(Calendar.MINUTE) + ":00";
                else
                    str += ":" + cal.get(Calendar.MINUTE) + ":00";
                dateTimeSelected = str;
                System.out.println(str);
                flag = true;

                if (flag) {
//					llRepeat.setVisibility(View.GONE);
                    dateTimePickerDialogActionListener.actionCompletedSuccessfully(dateTimeSelected, cal);
                    viewFlipper.setDisplayedChild(0);
                    dialogDate.dismiss();
                } else {
                    dateTimeSelected = "";
                }
            }
        });
    }

    public interface DateTimePickerDialogActionListener {
        void actionCompletedSuccessfully(String date,
                                         Calendar cal);

        void actionFailed(String errorMsg);

        void onSnackBarShow(String message);

        void onSwipeRefreshChange(boolean isSwipeRefreshing);
    }

    public DateTimePickerDialogActionListener getDateTimePickerDialogActionListener() {
        return dateTimePickerDialogActionListener;
    }


    public void setDateTimePickerDialogActionListener(DateTimePickerDialogActionListener dateTimePickerDialogActionListener) {
        this.dateTimePickerDialogActionListener = dateTimePickerDialogActionListener;
    }

}
