package com.hungnguyen2809.kiemtrahocky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ListView listDanhSach;
    Button btnAdd;
    EditText edtSearch;
    DatabaseKhachSan databaseKhachSan;
    ArrayList<HoaDon> dsHoaDon;
    AdapterHoaDon adapterHoaDon;
    private int location = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();

        dsHoaDon = new ArrayList<>();
        databaseKhachSan = new DatabaseKhachSan(this, "HoaDonKS", null, 1);
        databaseKhachSan.CreateTable();
//        AddData();

        adapterHoaDon = new AdapterHoaDon(this, R.layout.custom_hoa_don, dsHoaDon);
        listDanhSach.setAdapter(adapterHoaDon);

        registerForContextMenu(listDanhSach);
        UpdateData();

        listDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                location = i;
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sua:
                break;

            case R.id.menu_xoa:
                HoaDon hd = dsHoaDon.get(location);
                Delete(hd);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void Delete(HoaDon hd) {
        double price = hd.TongTien();
        int count = 0;
        final ArrayList<Integer> listMa = new ArrayList<>();
        for (HoaDon items : dsHoaDon){
            if (items.TongTien() > price){
                count++;
                listMa.add(items.getMa());
            }
        }

        AlertDialog.Builder confirmDelete = new AlertDialog.Builder(MainActivity.this);
        confirmDelete.setTitle("Xác Nhận");
        confirmDelete.setMessage("Bạn muốn xóa "+count+" hóa đơn > "+price+" ?");
        confirmDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int ma : listMa){
                    databaseKhachSan.DeleteData(ma);
                }
                UpdateData();
            }
        });
        confirmDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        confirmDelete.show();
    }

    private void AddData(){
        ArrayList<HoaDon> list = new ArrayList<>();
        list.add(new HoaDon("Vũ Trường An", 405, 200000, 5));
        list.add(new HoaDon("Lê Hải Hà", 501, 150000, 1));
        list.add(new HoaDon("Lê Đình Đức", 754, 250000, 5));
        list.add(new HoaDon("Mai Văn Đức", 405, 100000, 3));
        list.add(new HoaDon("Hà Thị Thu", 417, 300000, 5));
        list.add(new HoaDon("Mạc Văn Minh", 802, 100000, 10));

        for (HoaDon items : list){
            databaseKhachSan.InsertData(items);
        }
    }

    private void XapXep(){
        Comparator<HoaDon> comparator = new Comparator<HoaDon>() {
            @Override
            public int compare(HoaDon hd1, HoaDon hd2) {
                return (int)(hd2.getDonGia() - hd1.getDonGia());
            }
        };
        Collections.sort(dsHoaDon, comparator);
    }


    private void UpdateData() {
        dsHoaDon.clear();
        for (HoaDon items : databaseKhachSan.GetAllData()){
            dsHoaDon.add(items);
        }
        XapXep();
        adapterHoaDon.notifyDataSetChanged();
    }


    private void Mapping(){
        listDanhSach = (ListView)findViewById(R.id.listViewDanhSach);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
    }
}