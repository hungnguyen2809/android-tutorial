package com.hungnv28.broadcastandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private final String KEY_SEND_DATA = "KEY_SEND_DATA";

    private final MyBroadcast myBroadcast = new MyBroadcast();
    private final IntentFilter intentFilter = new IntentFilter(KEY_SEND_DATA);

    //
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private final BlueBroadcast blueBroadcast = new BlueBroadcast();
    private final IntentFilter filterBlue = new IntentFilter(BluetoothDevice.ACTION_FOUND);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSend = findViewById(R.id.btnSend);
        Button btnSearchBlue = findViewById(R.id.btnSearchBlue);
        EditText edtData = findViewById(R.id.edtData);

        initBLue();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = edtData.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("data", data);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setAction(KEY_SEND_DATA);

                sendBroadcast(intent);
            }
        });

        btnSearchBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherBlue.launch(Manifest.permission.BLUETOOTH_CONNECT);
            }
        });
    }

    private void initBLue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            bluetoothManager = getSystemService(BluetoothManager.class);
            bluetoothAdapter = bluetoothManager.getAdapter();
        }
    }

    private void getPairedDevices() {
        if (bluetoothAdapter != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress(); // MAC address
                        Log.d("PairedDevices", deviceName + ", " + deviceHardwareAddress);
                    }
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1001);
            }

        }
    }

    private void handleBlue() {
        Intent intent = new Intent();
        intent.setAction(BluetoothDevice.ACTION_FOUND);

        sendBroadcast(intent);
    }

    private final ActivityResultLauncher<String> launcherBlue = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if (result && bluetoothAdapter != null) {
                        if (!bluetoothAdapter.isEnabled()) {
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            launcherEnableBlue.launch(enableBtIntent);
                        } else {
                            handleBlue();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Thiết bị không được hỗ trợ hoặc không có quyền truy cập", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> launcherEnableBlue = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        handleBlue();
                    }
                }
            });

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcast, intentFilter);
        registerReceiver(blueBroadcast, filterBlue);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcast);
        unregisterReceiver(blueBroadcast);
    }
}