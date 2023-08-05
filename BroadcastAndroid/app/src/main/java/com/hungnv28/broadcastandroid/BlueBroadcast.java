package com.hungnv28.broadcastandroid;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BlueBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            @SuppressLint("MissingPermission") String deviceName = device.getName();
            String deviceHardwareAddress = device.getAddress(); // MAC address

            Toast.makeText(context, deviceName + ", " + deviceHardwareAddress, Toast.LENGTH_SHORT).show();
        }
    }
}
