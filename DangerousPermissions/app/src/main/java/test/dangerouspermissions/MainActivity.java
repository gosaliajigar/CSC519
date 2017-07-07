package test.dangerouspermissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to READ_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_PHONE_STATE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("GRANTS:", Arrays.toString(grantResults));

        if (grantResults[0] == 0) {
            TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String lineNumber = ((manager.getLine1Number() != null) ? manager.getLine1Number() : "N/A");
            String deviceId = ((manager.getDeviceId() != null) ? manager.getDeviceId() : "N/A");

            TextView lineNumberBox = (TextView)findViewById(R.id.lineNumber);
            TextView deviceIdBox = (TextView)findViewById(R.id.deviceId);

            lineNumberBox.setText(lineNumber);
            deviceIdBox.setText(deviceId);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
