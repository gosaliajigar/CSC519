package edu.itu.csc.earthquakealert.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import edu.itu.csc.earthquakealert.R;
import edu.itu.csc.earthquakealert.activities.MainActivity;

/**
 *
 * BroadcastReceiver to display notification when AIRPLANE_MODE is changed (OFF).
 *
 * @author "Jigar Gosalia"
 */
public class AirplaneModeBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean state = intent.getBooleanExtra("state", false);
        Log.d(MainActivity.APP_TAG, "Airplane Mode ON : " + state);
        if (!state) {
            showNotification(context);
        }
    }

    /**
     * show notification which will eventually take you to MainActivity.
     *
     * @param context
     */
    private void showNotification(Context context) {
        Log.d(MainActivity.APP_TAG, "Displaying Notification");
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setColor(Color.GREEN);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentTitle("EarthQuakeAlert");
        mBuilder.setContentText("It's been a while you have checked out earthquake data!");
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
