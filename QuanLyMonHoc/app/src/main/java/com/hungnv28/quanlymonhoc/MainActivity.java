package com.hungnv28.quanlymonhoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.hungnv28.quanlymonhoc.activity.DepartmentActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton btnDepartment = findViewById(R.id.btnDepartment);
        MaterialButton btnEmployee = findViewById(R.id.btnEmployee);
        MaterialButton btnExit = findViewById(R.id.btnExit);

        btnDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
                startActivity(intent);
            }
        });
    }
}