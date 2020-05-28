package com.example.covidspacer;

import androidx.appcompat.app.AppCompatActivity;
import mobi.inthepocket.android.beacons.ibeaconscanner.Beacon;
import mobi.inthepocket.android.beacons.ibeaconscanner.IBeaconScanner;



import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IBeaconScanner.initialize(IBeaconScanner.newInitializer(this)
                .setTargetService(BeaconHandler.class)
                .build());

        final Beacon beacon = Beacon.newBuilder()
                .setUUID("DDDD98FF-2900-441A-802F-9C398FC1DDDD")
                .setMajor(1)
                .setMinor(2)
                .build();
    }
}
