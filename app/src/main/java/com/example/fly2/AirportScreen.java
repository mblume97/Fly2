package com.example.fly2;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ddd.daterangepicker.DateRangePicker;

import java.util.Date;
import java.util.GregorianCalendar;


// First screen allowing users to select the airports then go to next screen
public class AirportScreen extends AppCompatActivity {

    Airport airport1;
    Airport airport2;
    Spinner airportSelection1;
    Spinner airportSelection2;
    Date departureDate;
    Date returnDate;
    private boolean isSpinner1Touched = false;
    private boolean isSpinner2Touched = false;
    Button btnNext;
    Button btnShowCalendar;
    ImageView showAnimation;
    AnimatedVectorDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_screen);
        airportSelection1 = (Spinner) findViewById(R.id.spinnerAirport1);
        airportSelection2 = (Spinner) findViewById(R.id.spinnerAirport2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AirportScreen.this, android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.airport_cities));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        airportSelection1.setAdapter(adapter);
        airportSelection2.setAdapter(adapter);
        final TextView airportPrompt2 = (TextView) findViewById(R.id.tvAirport2Prompt);

        showAnimation = findViewById(R.id.animated);
        // TODO: Do the autocomplete stuff by making an array in resources then connecting it (https://www.tutorialspoint.com/android/android_auto_complete.html)
        btnNext = (Button)findViewById(R.id.btnNext);
        // make so button can't be seen until stuff selected?
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoadingActivity();
            }
        });

        btnShowCalendar = (Button)findViewById(R.id.selectDatesBtn);
        // make so button can't be seen until stuff selected?
        btnShowCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDateRangePicker();
                btnNext.setEnabled(true);
                btnNext.setVisibility(View.VISIBLE);
            }
        });

        airportSelection1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinner1Touched = true;
                return false;
            }
        });
        airportSelection1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View arg1,
                                       int arg2, long arg3) {
                if (!isSpinner1Touched) return;
                // do what you want
                airportSelection2.setVisibility(View.VISIBLE);
                airportPrompt2.setVisibility(View.VISIBLE);
                drawable.start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnShowCalendar.setVisibility(View.GONE);
                btnShowCalendar.setEnabled(false);
                btnNext.setVisibility(View.GONE);
                btnNext.setEnabled(false);
            }
        });

        airportSelection2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinner2Touched = true;
                return false;
            }
        });
        airportSelection2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View arg1,
                                       int arg2, long arg3) {
                if (!isSpinner2Touched) return;
                // do what you want
                btnShowCalendar.setEnabled(true);
                btnShowCalendar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                btnShowCalendar.setVisibility(View.GONE);
                btnShowCalendar.setEnabled(false);
                btnNext.setVisibility(View.GONE);
                btnNext.setEnabled(false);
            }
        });

        airportPrompt2.setVisibility(View.GONE);
        airportSelection2.setVisibility(View.GONE);
        btnShowCalendar.setVisibility(View.GONE);
        btnShowCalendar.setEnabled(false);
        btnNext.setVisibility(View.GONE);
        btnNext.setEnabled(false);

        drawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.planeanimation);
        showAnimation.setImageDrawable(drawable);

    }


    private void initDateRangePicker() {
        final DateRangePicker dateRangePicker = new DateRangePicker(this, new DateRangePicker.OnCalenderClickListener() {
            @Override
            public void onDateSelected(String selectedStartDate, String selectedEndDate) {
                btnShowCalendar.setText(selectedStartDate + ", " + selectedEndDate);
                // Create date objects from the strings
                departureDate = createDate(selectedStartDate);
                returnDate = createDate(selectedEndDate);
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

    // next button to initialize this
    public void startLoadingActivity(){
        airport1 = new Airport(airportSelection1.getSelectedItem().toString());
        airport2 = new Airport(airportSelection2.getSelectedItem().toString());
        Intent intent = new Intent(AirportScreen.this, LoadingActivity.class);
        intent.putExtra("airport1", airport1);
        intent.putExtra("airport2", airport2);
        intent.putExtra("departureDate", departureDate);
        intent.putExtra("returnDate", returnDate);
        startActivity(intent);
    }


    // this is where the screen to select the two airports appear
    // needs 2 insert text boxes
    // next button to bundle and pass in to the date picker
}
