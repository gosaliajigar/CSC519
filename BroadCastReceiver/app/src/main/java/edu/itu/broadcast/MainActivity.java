package edu.itu.broadcast;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/*
 * A simple demo of broadcast receiver and custom intent.
 * The fragment has the code to send the broadcast.
 * 
 * this code will register a dynamic intent-filter for Action2
 * action one is static registered in the manifest file.
 */

public class MainActivity extends AppCompatActivity {

	//declare the intent names here, except if change ACTION1, fix it in AndroidManifest.xml as well.
	public static final String ACTION1 = "edu.itu.broadcast.staticevent";
	public static final String ACTION2 = "edu.itu.broadcast.dynamicevent";

	public static final String TAG = "CSC519_HW8_89753";
	
	MyReceiver mReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
		}
		this.mReceiver = new MyReceiver();
	}

	@Override
	public void onResume() {
		super.onResume();
		registerReceiver(this.mReceiver, new IntentFilter(MainActivity.ACTION2));
		Log.d(TAG, "Receiver should be registered");
	}

	@Override
	protected void onPause() {  //or onDestroy()
		unregisterReceiver(this.mReceiver);
		Log.d(TAG, "Receiver should be unregistered");
		super.onPause();
	}

}