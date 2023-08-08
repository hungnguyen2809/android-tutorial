package com.hungnv28.quanlymonhoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.hungnv28.quanlymonhoc.activity.DepartmentActivity;
import com.hungnv28.quanlymonhoc.activity.EmployeeActivity;

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

        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Bạn có chắc muốn thoát ?");
                builder.setNegativeButton("Hủy bỏ", null);
                builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });

                builder.show();
            }
        });
    }
}