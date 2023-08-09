package com.hungnv28.quanlymonhoc.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.hungnv28.quanlymonhoc.R;
import com.hungnv28.quanlymonhoc.adapter.EmployeeAdapter;
import com.hungnv28.quanlymonhoc.model.Employee;

import java.util.ArrayList;

public class EmployeeActivity extends AppCompatActivity {
    //    private MaterialButton btnAddEmployee;
    private MaterialToolbar toolbar;

    private EmployeeAdapter adapter;
    private final ArrayList<Employee> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        ListView lvEmp = findViewById(R.id.lvEmployee);
        toolbar = findViewById(R.id.toolBar);
//        btnAddEmployee = findViewById(R.id.btnAddEmployee);

        listData.add(new Employee(1, "NV01", "Nguyễn Văn Hùng", "Ban giám đốc"));
        listData.add(new Employee(2, "NV02", "Nguyễn Văn B", "Nhân viên"));
        listData.add(new Employee(3, "NV03", "Nguyễn Thị C", "Nhân viên"));

        adapter = new EmployeeAdapter(this, listData, launcherActivity);
        lvEmp.setAdapter(adapter);

        setEventToolbar();
//        setEventBtnAddEmployee();
    }

//    private void setEventBtnAddEmployee() {
//        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
//                launcherActivity.launch(intent);
//            }
//        });
//    }

    private void setEventToolbar() {
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;

        actionBar.setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mBtnThemEmp) {
            Intent intent = new Intent(EmployeeActivity.this, AddEmployeeActivity.class);
            launcherActivity.launch(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private final ActivityResultLauncher<Intent> launcherActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 1001) {
                        Bundle bundle = result.getData().getExtras();
                        Employee employee = (Employee) bundle.getSerializable("data_employee");
                        listData.add(employee);
                        adapter.notifyDataSetChanged();
                    }

                    if (result.getResultCode() == 1002) {
                        Bundle bundle = result.getData().getExtras();

                        int position = bundle.getInt("position_employee");
                        Employee employee = (Employee) bundle.getSerializable("data_employee");

                        listData.set(position, employee);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
    );
}