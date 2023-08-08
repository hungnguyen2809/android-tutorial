package com.hungnv28.quanlymonhoc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.hungnv28.quanlymonhoc.R;
import com.hungnv28.quanlymonhoc.adapter.SpinnerDepAdapter;
import com.hungnv28.quanlymonhoc.model.Department;
import com.hungnv28.quanlymonhoc.model.Employee;

import java.util.ArrayList;

public class AddEmployeeActivity extends AppCompatActivity {
    private MaterialButton btnSave, btnCancel;
    private Spinner spnDep;
    private EditText edtCode, edtName;
    private String selectedDepName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        spnDep = findViewById(R.id.spnDepAdd);
        edtCode = findViewById(R.id.edtCodeDepAdd);
        edtName = findViewById(R.id.edtNameDepAdd);
        btnSave = findViewById(R.id.btnAddEmpAdd);
        btnCancel = findViewById(R.id.btnCancelEmpAdd);

        setEventSpinner();
        setEventBtnSave();
        setEventBtnCancel();
    }

    private void setEventBtnCancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setEventBtnSave() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = edtCode.getText().toString().trim();
                String name = edtName.getText().toString().trim();

                if (code.length() == 0 || name.length() == 0) {
                    Toast.makeText(AddEmployeeActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedDepName == null) {
                    Toast.makeText(AddEmployeeActivity.this, "Vui lòng chọn phòng ban", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("AddEmployee_Save", code + ", " + name + ", " + selectedDepName);
                Employee employee = new Employee(System.currentTimeMillis(), code, name, selectedDepName);
            }
        });
    }

    private void setEventSpinner() {
        ArrayList<Department> departmentList = DepartmentActivity.getListDepartment();
        SpinnerDepAdapter adapter = new SpinnerDepAdapter(AddEmployeeActivity.this, departmentList);

        spnDep.setAdapter(adapter);
        spnDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDepName = departmentList.get(position).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}