package com.example.fly2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// First screen allowing users to select the airports then go to next screen
public class AirportScreen extends AppCompatActivity {

    EditText airport1Input;
    EditText airport2Input;
    String airport1;
    String airport2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_screen);
        airport1Input = (EditText) findViewById(R.id.airport1input);
        airport2Input = (EditText) findViewById(R.id.airport2input);
        // TODO: Make it so when selecting a city an airport is selected

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
        airport1 = String.valueOf(airport1Input.getText());
        airport2 = String.valueOf(airport2Input.getText());
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
