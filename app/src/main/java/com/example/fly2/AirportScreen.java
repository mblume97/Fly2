package com.example.fly2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

// First screen allowing users to select the airports then go to next screen
public class AirportScreen extends AppCompatActivity {

    EditText airport1Input;
    EditText airport2Input;
    Airport airport1;
    Airport airport2;
    Spinner airportSelection1;
    Spinner airportSelection2;

    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    }; // TODO: Make this a res file for cleanliness

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


        // TODO: Do the autocomplete stuff by making an array in resources then connecting it (https://www.tutorialspoint.com/android/android_auto_complete.html)
        final Button btn = (Button)findViewById(R.id.btnToDatePicker);
        // make so button can't be seen until stuff selected?
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDates();
            }
        });
    }

    public void pickDates(){
        airport1 = new Airport(airportSelection1.getSelectedItem().toString());
        airport2 = new Airport(airportSelection2.getSelectedItem().toString());

        if(airport1 == null || airport2 == null){
            System.out.print("airport is null don't do this fam");
            return;
        }
        // TODO: Make it so this button cannot be pressed unless both airports are picked. We can have it hidden until then
        Intent intent = new Intent(AirportScreen.this, DatePicker.class);
        intent.putExtra("airport1", airport1);
        intent.putExtra("airport2", airport2);
        startActivity(intent);
    }
    // this is where the screen to select the two airports appear
    // needs 2 insert text boxes
    // next button to bundle and pass in to the date picker
}
