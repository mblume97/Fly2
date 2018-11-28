package com.example.fly2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Date;
import java.util.GregorianCalendar;


import com.ddd.daterangepicker.DateRangePicker;

// Calender activity allowing users to pick dates
public class DatePicker extends AppCompatActivity {
    Airport airport1;
    Airport airport2;
    Date departureDate;
    Date returnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        // Gets the airports from previous activity
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

    private void initDateRangePicker() {
        final DateRangePicker dateRangePicker = new DateRangePicker(this, new DateRangePicker.OnCalenderClickListener() {
            @Override
            public void onDateSelected(String selectedStartDate, String selectedEndDate) {
                final TextView textView = findViewById(R.id.tvText);
                textView.setText(selectedStartDate + ", " + selectedEndDate);
                // Create date objects from the strings
                departureDate = createDate(selectedStartDate);
                returnDate = createDate(selectedEndDate);
                // Bundle it here
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

    // Takes a string in the form DD-MM-YYYY and created a Date object
    public static Date createDate(String date) {
        String dateArr[] = date.split("-");
        // 0 - day, 1 - month, 2 - year
        int day = Integer.parseInt(dateArr[0]);
        int month = Integer.parseInt(dateArr[1])-1;
        int year = Integer.parseInt(dateArr[2]);
        return new GregorianCalendar(year, month, day).getTime();
    }

    // TODO: Convert the airport inputs into airport codes for the API call
}
