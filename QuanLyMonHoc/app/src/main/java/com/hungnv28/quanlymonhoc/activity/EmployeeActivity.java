package com.hungnv28.quanlymonhoc.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.button.MaterialButton;
import com.hungnv28.quanlymonhoc.R;
import com.hungnv28.quanlymonhoc.adapter.EmployeeAdapter;
import com.hungnv28.quanlymonhoc.model.Employee;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {
    private ListView lvEmp;
    private MaterialButton btnAddEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        lvEmp = findViewById(R.id.lvEmployee);
        btnAddEmployee = findViewById(R.id.btnAddEmployee);

        setEventBtnAddEmployee();

        ArrayList<Employee> listData = new ArrayList<>();
        listData.add(new Employee(1, "NV01", "Nguyễn Văn Hùng", "Ban giám đốc"));
        listData.add(new Employee(2, "NV02", "Nguyễn Văn B", "Nhân viên"));
        listData.add(new Employee(3, "NV03", "Nguyễn Thị C", "Nhân viên"));

        EmployeeAdapter adapter = new EmployeeAdapter(this, listData);
        lvEmp.setAdapter(adapter);

    }

    private void setEventBtnAddEmployee() {
        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
                launcherAddEmp.launch(intent);
            }
        });
    }

    private final ActivityResultLauncher<Intent> launcherAddEmp = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );
}