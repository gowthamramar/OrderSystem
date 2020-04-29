package com.techware.clickkart.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.techware.clickkart.R;
import com.techware.clickkart.widgets.CustomTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SheduledTimeActivity extends BaseAppCompatNoDrawerActivity {
CustomTextView day,dateEtxt,previous,next;
    Calendar c;
    ArrayList<String> timeRange = new ArrayList<String>();
    String formattedDate;
    SimpleDateFormat fd,sdf;
    String dayOfTheWeek;
    Date d;
    int TIME_PICKER_INTERVAL = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheduled_time);
        day= findViewById(R.id.dateTextView);
        dateEtxt= findViewById(R.id.dateEtxt);
        previous=findViewById(R.id.previous);
        next=findViewById(R.id.next);
         c = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        dayOfTheWeek=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        day.setText(dayOfTheWeek);
        System.out.println("Current time => " + c.getTime());





        fd = new SimpleDateFormat("MMM dd yyyy");
        formattedDate = fd.format(c.getTime());
        dateEtxt.setText(formattedDate);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE, -1);
                formattedDate = fd.format(c.getTime());
                Date date = c.getTime();
                dayOfTheWeek=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
                day.setText(dayOfTheWeek);
                dateEtxt.setText(formattedDate);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE, 1);
                formattedDate = fd.format(c.getTime());
                dateEtxt.setText(formattedDate);
                Date date = c.getTime();
                dayOfTheWeek=new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
                day.setText(dayOfTheWeek);
            }
        });

    }
}
