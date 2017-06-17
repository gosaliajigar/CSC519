package edu.itu.csc519.csc519_hw5_89753;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String APP_TAG = "CSC519_HW5_89753";

    // Do not use Uri.encode here as Uri.Builder.appendQueryParameter
    // takes care of encoding and replacing special characters.
    public static final String CITY = "San Jose,US";

    public static final String FORECAS_DAYS = "7";

    // hard-coded geo location of San Jose,CA
    private static final String GEO_LOCATION_SAN_JOSE_CA = "geo:37.3382082,-121.8863286";

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
            // Build Uri from longitude and latitute of
            // San Jose,CA
            Uri uri = Uri.parse(GEO_LOCATION_SAN_JOSE_CA);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            // Set package as Google Maps
            intent.setPackage("com.google.android.apps.maps");
            // Check if google maps exists on device before starting
            // activity else alert user about Google Map
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Google Map not installed to perform selected action!", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
