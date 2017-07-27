package edu.itu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/*
 * A simple demo of receiving custom intents.
 * action1 is registered statically in the manifest file and action2 is dynamically registered 
 * in the mainActivity code.
 * 
 * The variables ACTION1 and ACTION2 are declared in the MainActivity as well.
 */

public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String message = "";
		if (intent != null
				&& intent.getAction() != null) {
			if (MainActivity.ACTION1.equals(intent.getAction())) {
				message = "Received an intent for Action1";
			} else if (MainActivity.ACTION2.equals(intent.getAction())) {
				message = "Received an intent for Action2";
			} else {
				message = "Received an unknown intent";
			}
		} else {
			message = "Received either null intent or an intent without an action";
		}
		Log.d(MainActivity.TAG, message);
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}
}
