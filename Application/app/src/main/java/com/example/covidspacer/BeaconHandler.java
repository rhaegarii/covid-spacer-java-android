package com.example.covidspacer;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import mobi.inthepocket.android.beacons.ibeaconscanner.Beacon;
import mobi.inthepocket.android.beacons.ibeaconscanner.BluetoothScanBroadcastReceiver;


public class BeaconHandler extends JobIntentService {
    private static final String TAG = "BeaconActivityService";

    private static final int NOTIFICATION_ID = 65;


    @Override
    protected void onHandleWork(@NonNull final Intent intent) {
        final Beacon beacon = intent.getParcelableExtra(BluetoothScanBroadcastReceiver.IBEACON_SCAN_BEACON_DETECTION);
        final boolean enteredBeacon = intent.getBooleanExtra(BluetoothScanBroadcastReceiver.IBEACON_SCAN_BEACON_ENTERED, false);
        final boolean exitedBeacon = intent.getBooleanExtra(BluetoothScanBroadcastReceiver.IBEACON_SCAN_BEACON_EXITED, false);

        Log.d(TAG, "Callback ");
        if (beacon != null) {
            String logMessage = "";
            if (enteredBeacon) {
                Log.d(TAG, "entered beacon " + beacon.getUUID());
                //logMessage = getString(R.string.notification_enter, beacon.getUUID(), beacon.getMajor(), beacon.getMinor());
            } else if (exitedBeacon) {
                Log.d(TAG, "exited beacon " + beacon.getUUID());
                //logMessage = getString(R.string.notification_exit, beacon.getUUID(), beacon.getMajor(), beacon.getMinor());
            }

            // Create notification channel if required
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(new NotificationChannel(TAG, "Beacon Activity", NotificationManager.IMPORTANCE_LOW));
            }

            final Notification notification = new NotificationCompat.Builder(this, TAG)
                    .setAutoCancel(true)
                    .setContentText(logMessage)
                    .setContentTitle("Beacon activity")
                    .setGroup(TAG)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(logMessage))
                    .build();

            final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(TAG, NOTIFICATION_ID, notification);
        }
    }
}