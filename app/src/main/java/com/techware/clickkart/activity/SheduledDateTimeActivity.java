package com.techware.clickkart.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.techware.clickkart.R;
import com.techware.clickkart.app.App;
import com.techware.clickkart.model.orderplaced.OrderPlacedBean;
import com.techware.clickkart.widgets.CustomEditText;
import com.techware.clickkart.widgets.CustomTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SheduledDateTimeActivity extends BaseAppCompatNoDrawerActivity {
    CustomEditText min;
    LinearLayout llNext, llPrevoius, ll_time,ll_Back;
    CustomTextView day, dateEtxt,btnContinue, hour;
    Calendar c;
    ArrayList<String> timeRange = new ArrayList<String>();
    String formattedDate;
    SimpleDateFormat fd, sdf;
    String dayOfTheWeek;
    CustomTextView txtBookedTime,txtAm,txtPm,selectedDay;
    TimePickerDialog picker;
    Calendar calTemp = Calendar.getInstance();
    Calendar calTempBookedTime = Calendar.getInstance();
    SimpleDateFormat sdfSelectedTime = new SimpleDateFormat("hh:mm a", App.getCurrentLocale());
    SimpleDateFormat selectedTime = new SimpleDateFormat("hh:mm ", App.getCurrentLocale());
    SimpleDateFormat sdfBookedTime = new SimpleDateFormat("hh:mm a", App.getCurrentLocale());
    private OrderPlacedBean orderPlacedBean;
    private String resultTime;
    private String resultTimeBookedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheduled_date_time);
        initViews();
        showToolbar(false, "");

        final Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        dayOfTheWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        day.setText(dayOfTheWeek);
        System.out.println("Current time => " + c.getTime());
        fd = new SimpleDateFormat("MMM dd yyyy");
        formattedDate = fd.format(c.getTime());
        dateEtxt.setText(formattedDate);
        selectedDay.setText(day.getText().toString()+","+dateEtxt.getText().toString());
        llPrevoius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE, -1);
                formattedDate = fd.format(c.getTime());
                Date date = c.getTime();
                dayOfTheWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
                day.setText(dayOfTheWeek);
                dateEtxt.setText(formattedDate);
                selectedDay.setText(day.getText().toString()+","+dateEtxt.getText().toString());


            }
        });
        llNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE, 1);
                formattedDate = fd.format(c.getTime());
                dateEtxt.setText(formattedDate);
                Date date = c.getTime();
                dayOfTheWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
                day.setText(dayOfTheWeek);
                selectedDay.setText(day.getText().toString()+","+dateEtxt.getText().toString());
            }
        });
        hour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                showTimePicker();
                return false;
            }
        });
      /*  hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });*/
        ll_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showTimePicker();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hour.getText().equals("00:00")){
                    Toast.makeText(SheduledDateTimeActivity.this,R.string.shedule_time, Toast.LENGTH_SHORT).show();
                }
                else{
                    for (int i=0;i<orderPlacedBean.getData().size();i++){
                        orderPlacedBean.getData().get(i).setScheduledDate(formattedDate);
                        orderPlacedBean.getData().get(i).setScheduledTime(String.format(App.getCurrentLocale(), "Time: %s - %s", resultTime, resultTimeBookedTime));
                    }
                    startActivity(new Intent(SheduledDateTimeActivity.this,PaymentMethodActivity.class)
                            .putExtra("bean",orderPlacedBean));
                }

            }
        });
        ll_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void showTimePicker() {
        final Calendar cldr = Calendar.getInstance();
        int hours = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);

        picker = new TimePickerDialog(SheduledDateTimeActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {

                        calTemp.set(Calendar.HOUR_OF_DAY, sHour);
                        calTemp.set(Calendar.MINUTE, sMinute);
                        calTempBookedTime.set(Calendar.HOUR_OF_DAY, sHour);
                        calTempBookedTime.set(Calendar.MINUTE, sMinute + 15);

                        String time = selectedTime.format(new Date(calTemp.getTimeInMillis()));
                        resultTime = sdfSelectedTime.format(new Date(calTemp.getTimeInMillis()));
                     resultTimeBookedTime = sdfBookedTime.format(new Date(calTempBookedTime.getTimeInMillis()));

                        hour.setText(String.format(App.getCurrentLocale(), "%s", time));
                        txtBookedTime.setText(String.format(App.getCurrentLocale(), "Time: %s - %s", resultTime, resultTimeBookedTime));
                    }
                }, hours, minutes, false);
        picker.show();
    }

    private void initViews()
    {
        if (getIntent().hasExtra("bean")){
        orderPlacedBean= (OrderPlacedBean) getIntent().getSerializableExtra("bean");
    }

        ll_Back=findViewById(R.id.ll_backSheduledDate);
        btnContinue=findViewById(R.id.btn_continue_sheduledDate);
        selectedDay=findViewById(R.id.txt_selected_day);
        ll_time = findViewById(R.id.ll_time);
        hour = findViewById(R.id.txt_hour);
        txtBookedTime = findViewById(R.id.txt_bookedTime);
        /*txtFirstDigit =findViewById(R.id.txt_first_digit_time);
        txtSecondDigit =findViewById(R.id.txt_second_digit_time);
        txtThirdDigit =findViewById(R.id.txt_third_digit_time);
        txtFourthDigit =findViewById(R.id.txt_fourth_digit_time);*/
        /*  txt_shedule_time=findViewById(R.id.txt_shedule_time);*/
        day = findViewById(R.id.dateTextView);
        dateEtxt = findViewById(R.id.dateEtxt);
        llNext = findViewById(R.id.ll_arrowNext);
        llPrevoius = findViewById(R.id.ll_arrowPrevious);
        c = Calendar.getInstance();

    }

    private void setTimeInAM() {
        int sHour = calTemp.get(Calendar.HOUR_OF_DAY);

        if (sHour < 12)
            return;

        sHour -= 12;
        int sMinute = calTemp.get(Calendar.MINUTE);

        calTempBookedTime.set(Calendar.HOUR_OF_DAY, sHour);
        calTempBookedTime.set(Calendar.MINUTE, sMinute + 15);
        String time = selectedTime.format(new Date(calTemp.getTimeInMillis()));
        String resultTime = sdfSelectedTime.format(new Date(calTemp.getTimeInMillis()));
        String resultTimeBookedTime = sdfBookedTime.format(new Date(calTempBookedTime.getTimeInMillis()));

        hour.setText(String.format(App.getCurrentLocale(), "%s", time));
        txtBookedTime.setText(String.format(App.getCurrentLocale(), "Time: %s - %s", resultTime, resultTimeBookedTime));
    }

    private void setTimeInPM() {
        int sHour = calTemp.get(Calendar.HOUR_OF_DAY);

        if (sHour >= 12)
            return;

        int sMinute = calTemp.get(Calendar.MINUTE) + 12;

        calTempBookedTime.set(Calendar.HOUR_OF_DAY, sHour);
        calTempBookedTime.set(Calendar.MINUTE, sMinute + 15);
        String time = selectedTime.format(new Date(calTemp.getTimeInMillis()));
        String resultTime = sdfSelectedTime.format(new Date(calTemp.getTimeInMillis()));
        String resultTimeBookedTime = sdfBookedTime.format(new Date(calTempBookedTime.getTimeInMillis()));

        hour.setText(String.format(App.getCurrentLocale(), "%s", time));
        txtBookedTime.setText(String.format(App.getCurrentLocale(), "Time: %s - %s", resultTime, resultTimeBookedTime));
    }

    public void finsh(View view) {
        finish();
    }
}


