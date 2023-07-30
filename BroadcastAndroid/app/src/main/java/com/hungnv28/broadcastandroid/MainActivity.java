package com.hungnv28.broadcastandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private final String KEY_SEND_DATA = "KEY_SEND_DATA";

    MyBroadcast myBroadcast = new MyBroadcast();
    IntentFilter intentFilter = new IntentFilter(KEY_SEND_DATA);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSend = findViewById(R.id.btnSend);
        EditText edtData = findViewById(R.id.edtData);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadcast, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadcast);
    }
}