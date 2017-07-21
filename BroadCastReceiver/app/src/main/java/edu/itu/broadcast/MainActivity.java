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
	
	String TAG = "MainActivity";
	
	MyReceiver mReceiver;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
		}
		
		 
		
		
	}

	@Override
	public void onResume() {
		super.onResume();
		
		Log.d(TAG, "Receiver should be registered");
	}
	@Override
	protected void onPause() {  //or onDestroy()
		
		
		Log.d(TAG, "Receiver should be unregistered");
		super.onPause();

	} 

}