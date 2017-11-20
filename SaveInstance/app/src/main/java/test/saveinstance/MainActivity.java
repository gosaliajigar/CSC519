package test.saveinstance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // will save in bundle in onSaveInstanceState
    private int saveMe;

    // will not save in bundle in onSaveInstanceState
    private int saveMeNot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // do some action that generates values for
        // activity specific variables i.e. saveMe
        // and saveMeNot
        Button saveButton = (Button) findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMe = 10;
                saveMeNot = 20;
                Toast.makeText(getApplicationContext(), "SAVED: saveMe: " + saveMe + ";saveMeNot: " + saveMeNot, Toast.LENGTH_LONG).show();
            }
        });

        // will be used to display value of
        // saveMe and saveMeNot after orientation
        // changes.
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "DISPLAY: saveMe: " + saveMe + ";saveMeNot: " + saveMeNot, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // save saveMe in bundle
        outState.putInt("saveMe", saveMe);
        super.onSaveInstanceState(outState);
        Log.d("TEST", "Saving saveMe in bundle during orientation change");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // retrieve saveMe from bundle
        saveMe = savedInstanceState.getInt("saveMe");
        Log.d("TEST", "Retrieving saveMe in bundle during orientation change");
    }
}
