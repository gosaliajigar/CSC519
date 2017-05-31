package edu.itu.csc519.csc519_hw3_89753;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * @author "Jigar Gosalia"
 */
public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String details = getIntent().getStringExtra("SELECTED_DAY_WEATHER_DETAILS");
        Log.d(MainActivity.APP_TAG, "DetailActivity Text : " + details);

        TextView textView = (TextView) findViewById(R.id.detail_textview);
        textView.setText(details);
    }
}
