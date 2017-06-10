package edu.itu.csc519.csc519_hw4_89753;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author "Jigar Gosalia"
 */
public class MainActivity extends AppCompatActivity {

    public static final String APP_TAG = "CSC519_HW4_89753";

    // Do not use Uri.encode here as Uri.Builder.appendQueryParameter
    // takes care of encoding and replacing special characters.
    public static final String CITY = "San Jose,US";

    public static final String FORECAS_DAYS = "7";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            Log.d(APP_TAG, "onOptionsItemSelected Refresh selected");
            new MainActivityFragment.FetchWeatherTask().execute(CITY, FORECAS_DAYS);
            return true;
        } else if (id == R.id.action_map) {
            Log.d(APP_TAG, "onOptionsItemSelected Map selected");
            Intent intent = new Intent(this, MapsMarkerActivity.class);
            intent.putExtra("CITY_NAME", CITY);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
