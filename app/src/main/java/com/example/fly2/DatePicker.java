package com.example.fly2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import com.ddd.daterangepicker.DateRangePicker;

// i'm literally just putting comments so git knows to push changes
// calender allowing users to pick dates
public class DatePicker extends AppCompatActivity {
    Airport airport1;
    Airport airport2;
    Date departureDate;
    Date returnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        // gets the airport strings from previous activity airport string
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            airport1 = extras.getParcelable("airport1");
            airport2 = extras.getParcelable("airport2");
        }

        final Button btn = findViewById(R.id.btnShowCalendar);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                initDateRangePicker();
            }
        });

    }

    // TODO: YYYY-MM-DD In a string, currently in DD-MM-YYYY. Convert to a string
    private void initDateRangePicker() {
        final DateRangePicker dateRangePicker = new DateRangePicker(this, new DateRangePicker.OnCalenderClickListener() {
            @Override
            public void onDateSelected(String selectedStartDate, String selectedEndDate) {
                final TextView textView = findViewById(R.id.tvText);
                textView.setText(selectedStartDate + ", " + selectedEndDate);
                // code to get out the dates
                // first let's so the start date
                String departDateArr[] = selectedStartDate.split("-");
                // 0 - day, 1 - month, 2 - year
                int dayD = Integer.parseInt(departDateArr[0]);
                int monthD = Integer.parseInt(departDateArr[1])-1;
                int yearD = Integer.parseInt(departDateArr[2]);
                departureDate = new GregorianCalendar(yearD, monthD, dayD).getTime();

                String returnDateArr[] = selectedEndDate.split("-");
                // 0 - day, 1 - month, 2 - year
                int dayR = Integer.parseInt(returnDateArr[0]);
                int monthR = Integer.parseInt(returnDateArr[1])-1;
                int yearR = Integer.parseInt(returnDateArr[2]);
                returnDate = new GregorianCalendar(yearR, monthR, dayD).getTime();
                // bundle it here
                Intent intent = new Intent(DatePicker.this, LoadingActivity.class);
                intent.putExtra("airport1", airport1);
                intent.putExtra("airport2", airport2);
                intent.putExtra("departureDate", departureDate);
                intent.putExtra("returnDate", returnDate);
                startActivity(intent);
            }
        });
        dateRangePicker.show();
        dateRangePicker.setBtnPositiveText("apply");
        dateRangePicker.setBtnNegativeText("nope");
    }

    // TODO: Convert the airport inputs into airport codes for the API call
}
