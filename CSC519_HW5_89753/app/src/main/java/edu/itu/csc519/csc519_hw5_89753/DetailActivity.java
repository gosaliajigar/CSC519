package edu.itu.csc519.csc519_hw5_89753;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity with ShareActionProvider to share data thru other apps.
 *
 * @author "Jigar Gosalia"
 */
public class DetailActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String details = getIntent().getStringExtra("SELECTED_DAY_WEATHER_DETAILS");
        Log.d(MainActivity.APP_TAG, "DetailActivity Text : " + details);

        TextView textView = (TextView) findViewById(R.id.detail_textview);
        textView.setText(details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // set the shared intent in the ShareActionProvider
        Intent sharedIntent = getSharedIntent();
        setShareIntent(sharedIntent);

        // Return true to display menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_share) {
            Log.d(MainActivity.APP_TAG, "onOptionsItemSelected Share selected");

            // get the shared intent to check if there is any app
            // installed on mobile device to serve it.
            Intent sharedIntent = getSharedIntent();

            // Verify that the intent will resolve to an activity
            // else alert the user by a toast message
            if (sharedIntent != null
                    && sharedIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sharedIntent);
            } else {
                Toast.makeText(getApplicationContext(), "No App installed to perform selected action!", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Prepare the shared intent
     * @return
     */
    private Intent getSharedIntent() {
        StringBuilder details = new StringBuilder();
        // retrieve data from the text view to be shared
        TextView textView = (TextView) findViewById(R.id.detail_textview);
        if (textView != null
                && textView.getText() != null
                && textView.getText().toString().length() > 0) {
            details.append(textView.getText().toString());
        }
        // Create intent with appropriate action and data
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, details.toString());
        intent.setType("text/plain");
        return intent;
    }

    /**
     * Set the shared intent in the ShareActionProvider
     * @param shareIntent
     */
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
