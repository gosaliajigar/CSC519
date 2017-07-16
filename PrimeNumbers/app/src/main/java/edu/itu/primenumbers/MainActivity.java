package edu.itu.primenumbers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String APP_TAG = "CSC519_HW3_89753";

    private String numberOne;

    private String numberTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.calculate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText numberOneET = (EditText) findViewById(R.id.numberOne);
                EditText numberTwoET = (EditText) findViewById(R.id.numberTwo);
                numberOne = numberOneET.getText().toString();
                numberTwo = numberTwoET.getText().toString();
                new MainActivityFragment.PrimeNumberCalculationTask().execute(numberOne, numberTwo);
            }
        });
    }
}
