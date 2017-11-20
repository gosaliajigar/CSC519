package test.atry;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("TEST", "onCreate");
    }

    @Override
    public void onClick(View view) {
        // handle onClick
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // handle onItemSelected
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // handle onNothingSelected

    }
}
