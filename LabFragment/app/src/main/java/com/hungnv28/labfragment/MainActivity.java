package com.hungnv28.labfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.hungnv28.labfragment.tab.Tab1;
import com.hungnv28.labfragment.tab.Tab2;

public class MainActivity extends AppCompatActivity {
    Tab1 tab1;
    Tab2 tab2;

    MaterialButton btnTab1, btnTab2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab1 = new Tab1();
        tab2 = new Tab2();

        btnTab1 = findViewById(R.id.btnTab1);
        btnTab2 = findViewById(R.id.btnTab2);

        //nạp gán fragment vào activity
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerFrame, tab1)
                    .commit();
        }

        btnTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerFrame, tab1)
                        .commit();
            }
        });

        btnTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerFrame, tab2)
                        .commit();
            }
        });
    }


}