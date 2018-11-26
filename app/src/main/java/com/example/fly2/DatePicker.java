package com.example.fly2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ddd.daterangepicker.DateRangePicker;

// i'm literally just putting comments so git knows to push changes
// calender allowing users to pick dates
public class DatePicker extends AppCompatActivity {
    String airport1;
    String airport2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        // gets the airport strings from previous activity airport string
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            airport1 = extras.getString("airport1");
            airport2 = extras.getString("airport2");
        }

        final Button btn = findViewById(R.id.btnShowCalendar);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                initDateRangePicker();
            }
        });

    }

    // TODO: Implement date range picker https://github.com/borax12/MaterialDateRangePicker for inspiration
    private void initDateRangePicker() {
        final DateRangePicker dateRangePicker = new DateRangePicker(this, new DateRangePicker.OnCalenderClickListener() {
            @Override
            public void onDateSelected(String selectedStartDate, String selectedEndDate) {
                final TextView textView = findViewById(R.id.tvText);
                textView.setText(selectedStartDate + ", " + selectedEndDate);
            }
        });
        dateRangePicker.show();
        dateRangePicker.setBtnPositiveText("apply");
        dateRangePicker.setBtnNegativeText("nope");
    }

    // TODO: Convert the airport inputs into airport codes for the API call
}
