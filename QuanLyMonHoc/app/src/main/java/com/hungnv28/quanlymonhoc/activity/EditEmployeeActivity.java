package com.hungnv28.quanlymonhoc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.Objects;

public class EditEmployeeActivity extends AppCompatActivity {
    private MaterialButton btnSave, btnCancel;
    private Spinner spnDep;
    private EditText edtCode, edtName;
    private String selectedDepName = "";

    private Employee employeeRoot;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        spnDep = findViewById(R.id.spnDepEdit);
        edtCode = findViewById(R.id.edtCodeDepEdit);
        edtName = findViewById(R.id.edtNameDepEdit);
        btnSave = findViewById(R.id.btnAddEmpEdit);
        btnCancel = findViewById(R.id.btnCancelEmpEdit);

        setInitData();
        setEventSpinner();
        setEventBtnSave();
        setEventBtnCancel();
    }

    private void setInitData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) return;

        position = bundle.getInt("position_employee");
        employeeRoot = (Employee) bundle.getSerializable("data_employee");

        if (employeeRoot == null) return;

        ArrayList<Department> departmentList = DepartmentActivity.getListDepartment();
        int index = -1;
        for (int i = 0; i <= departmentList.size(); i++) {
            if (Objects.equals(departmentList.get(i).getName(), employeeRoot.getName())) {
                index = i;
                break;
            }
        }

        spnDep.setSelection(index);
        edtCode.setText(employeeRoot.getCode());
        edtName.setText(employeeRoot.getName());
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
                    Toast.makeText(EditEmployeeActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedDepName.isEmpty()) {
                    Toast.makeText(EditEmployeeActivity.this, "Vui lòng chọn phòng ban", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("EditEmployee_Save", code + ", " + name + ", " + selectedDepName);
                Employee employee = new Employee(employeeRoot.getId(), code, name, selectedDepName);

                Bundle bundle = new Bundle();
                bundle.putInt("position_employee", position);
                bundle.putSerializable("data_employee", employee);

                Intent intent = new Intent();
                intent.putExtras(bundle);

                setResult(1002, intent);
            }
        });
    }

    private void setEventSpinner() {
        ArrayList<Department> departmentList = DepartmentActivity.getListDepartment();
        SpinnerDepAdapter adapter = new SpinnerDepAdapter(EditEmployeeActivity.this, departmentList);

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