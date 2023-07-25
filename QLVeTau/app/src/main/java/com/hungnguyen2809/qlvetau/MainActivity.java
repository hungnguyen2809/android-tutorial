package com.hungnguyen2809.qlvetau;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    ListView listViewDanhSach;
    EditText edtSearch;
    Button btnAdd;
    AdapterVeTau adapterVeTau;
    ArrayList<VeTau> dsVeTau;
    DatabaseVeTau databaseVeTau;
    private int RESULT_CODE_ADD = 222;
    private int RESULT_CODE_EDIT = 333;
    private int location = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();

        databaseVeTau = new DatabaseVeTau(this, "VeTau", null, 1);
        databaseVeTau.CreateTable();

        dsVeTau = new ArrayList<>();
        adapterVeTau = new AdapterVeTau(this, R.layout.line_custom_vetau, dsVeTau);
        listViewDanhSach.setAdapter(adapterVeTau);
        UpdateData();

        registerForContextMenu(listViewDanhSach);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddVeTauActivity.class);
                startActivityForResult(intent, RESULT_CODE_ADD);
            }
        });

        listViewDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                location = i;
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_CODE_ADD && resultCode == RESULT_OK && data != null){
            VeTau vt = (VeTau) data.getSerializableExtra("data");
            databaseVeTau.InsertData(vt);
            Toast.makeText(this, "Them thanh cong", Toast.LENGTH_SHORT).show();
            UpdateData();
        }
        if (requestCode == RESULT_CODE_EDIT && resultCode == RESULT_OK && data != null){
            VeTau vt = (VeTau) data.getSerializableExtra("data");
            databaseVeTau.UpdateData(vt);
            Toast.makeText(this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
            UpdateData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                Intent intent = new Intent(MainActivity.this, EditVeTauActivity.class);
                VeTau vt = dsVeTau.get(location);
                intent.putExtra("data", vt);
                startActivityForResult(intent, RESULT_CODE_EDIT);
                break;
            case R.id.menu_delete:
                DeleteData();
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteData() {
        AlertDialog.Builder confirmDelete = new AlertDialog.Builder(MainActivity.this);
        confirmDelete.setTitle("Thong bao");
        confirmDelete.setMessage("Ban co chac muon xoa khong ?");
        confirmDelete.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseVeTau.DeleteData(dsVeTau.get(location).getMa());
                Toast.makeText(MainActivity.this, "Xoa thanh cong !", Toast.LENGTH_SHORT).show();
                UpdateData();
            }
        });
        confirmDelete.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        confirmDelete.show();
    }

    private void UpdateData(){
        dsVeTau.clear();
        for (VeTau items : databaseVeTau.GetAllData()){
            dsVeTau.add(items);
        }
        XapXep();
        adapterVeTau.notifyDataSetChanged();
    }

    private void Mapping(){
        listViewDanhSach = (ListView) findViewById(R.id.listViewDanhSach);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        btnAdd = (Button) findViewById(R.id.btnAdd);
    }

    private double GiaTien(VeTau veTau){
        return veTau.isStatus() ? veTau.getGia() * 2 * 0.95 : veTau.getGia();
    }

    private void XapXep(){
        // Anonymous function
        Comparator<VeTau> comparator = new Comparator<VeTau>() {
            @Override
            public int compare(VeTau v1, VeTau v2) {
                //nếu xắp xếp theo là số thì dùng cái này
                return (int) (GiaTien(v1) - GiaTien(v2));
                //Nếu theo ký tự, chữ
                // return <Giá trị 1 đầu tiên>.compareTo(<Giá trị 2 cần so sánh>)
            }
        };
        // Xắp xếp với comparator.reversed() là giảm bỏ đi thì là tăng
        //Collections.sort(dsVeTau, comparator); //Xắp xếp tăng
        Collections.sort(dsVeTau, comparator.reversed()); //Xắp xếp giảm (Đảo ngược lại dãy tăng)
    }
}