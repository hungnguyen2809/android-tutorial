package com.hungnv28.quanlymonhoc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.hungnv28.quanlymonhoc.R;
import com.hungnv28.quanlymonhoc.adapter.EmployeeAdapter;
import com.hungnv28.quanlymonhoc.model.Employee;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        ListView lvEmp = findViewById(R.id.lvEmployee);

        ArrayList<Employee> listData = new ArrayList<>();
        listData.add(new Employee(1, "NV01", "Nguyễn Văn Hùng", "Ban giám đốc"));
        listData.add(new Employee(2, "NV02", "Nguyễn Văn B", "Nhân viên"));
        listData.add(new Employee(3, "NV03", "Nguyễn Thị C", "Nhân viên"));

        EmployeeAdapter adapter = new EmployeeAdapter(this, listData);

        lvEmp.setAdapter(adapter);

    }
}