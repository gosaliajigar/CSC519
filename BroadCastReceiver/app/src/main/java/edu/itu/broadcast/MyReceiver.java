package edu.itu.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
		
		
	}
}
