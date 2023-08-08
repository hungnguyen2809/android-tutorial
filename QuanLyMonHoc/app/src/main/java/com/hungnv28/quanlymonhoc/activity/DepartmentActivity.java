package com.hungnv28.quanlymonhoc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.widget.ListView;

import com.hungnv28.quanlymonhoc.R;
import com.hungnv28.quanlymonhoc.adapter.DepartmentAdapter;
import com.hungnv28.quanlymonhoc.model.Department;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity {
    private ListView lvDepartment;
    private SearchView searchView;
    private ArrayList<Department> listDepart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        lvDepartment = findViewById(R.id.lvDepartment);
        searchView = findViewById(R.id.svDepartment);

        listDepart = getListDepartment();
        loadData(listDepart);

        setEventSearchView();
    }

    private void loadData(ArrayList<Department> data) {
        DepartmentAdapter adapter = new DepartmentAdapter(DepartmentActivity.this, data);
        lvDepartment.setAdapter(adapter);
    }

    private void setEventSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Department> listFilter = new ArrayList<>();

                if (newText.trim().length() > 0) {
                    for (Department department : listDepart) {
                        if (department.getName().toLowerCase().contains(newText.trim().toLowerCase()) || department.getCode().toLowerCase().contains(newText.trim().toLowerCase())) {
                            listFilter.add(department);
                        }
                    }
                } else {
                    listFilter = (ArrayList<Department>) listDepart.clone();
                }

                loadData(listFilter);
                return false;
            }
        });
    }

    public static ArrayList<Department> getListDepartment() {
        ArrayList<Department> list = new ArrayList<>();
        list.add(new Department(1, "TKSP", "Phòng thiết kế sản phẩm"));
        list.add(new Department(2, "CSKH", "Phòng chăm sóc khách hàng"));
        list.add(new Department(3, "CNTT", "Phòng công nghệ thông tin"));
        return list;
    }
}