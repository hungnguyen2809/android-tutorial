package com.hungnguyen2809.qlthisinh;

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
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    ListView listViewDS;
    EditText edtSearch;
    Button btnAdd;
    ArrayList<ThiSinh> dsTHiSinh;
    AdpterThiSinh adpterThiSinh;
    DatabaseThiSinh databaseThiSinh;
    private int location = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        dsTHiSinh = new ArrayList<>();

        databaseThiSinh = new DatabaseThiSinh(this, "ThiSinh", null, 1);
        databaseThiSinh.CreateTable();
        //AddData();

        adpterThiSinh = new AdpterThiSinh(this, R.layout.line_custom_thi_sinh, dsTHiSinh);
        listViewDS.setAdapter(adpterThiSinh);
        UpdateData();

        registerForContextMenu(listViewDS);

        listViewDS.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                location = position;
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
            case R.id.menu_xoa:
                ThiSinh ts = dsTHiSinh.get(location);
                DeleteData(ts);
                break;
            case R.id.menu_sua:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteData(ThiSinh ts) {
        double totalPoint = (double) (Math.round(ts.TongDiem() * 100) / 100);
        int count = 0;
        final ArrayList<String> listSBD = new ArrayList<>();
        for (ThiSinh items : dsTHiSinh){
            if ((double) (Math.round(items.TongDiem() * 100) / 100) < totalPoint){
                count++;
                listSBD.add(items.getSBD().trim());
            }
        }

        AlertDialog.Builder confirmDelete = new AlertDialog.Builder(MainActivity.this);
        confirmDelete.setTitle("Thông báo !");
        confirmDelete.setMessage("Bạn có chắc muốn xóa "+ count +" TS dưới "+totalPoint+" điểm ?");
        confirmDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (String items : listSBD){
                    databaseThiSinh.DeleteData(items);
                }
                UpdateData();
            }
        });
        confirmDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        if (count == 0){
            Toast.makeText(this, "Không có thí sinh nào có điểm nhỏ hơn điểm được chọn "+totalPoint+" điểm !", Toast.LENGTH_SHORT).show();
        }
        else {
            confirmDelete.show();
        }
    }

    private void AddData() {
        ArrayList<ThiSinh> list = new ArrayList<>();
        list.add(new ThiSinh("AB01", "Nguyen Van Hung", 8.5, 9.5, 10.0));
        list.add(new ThiSinh("AB02", "Tran Ngoc Vi", 5.5, 4.5, 3.0));
        list.add(new ThiSinh("AB03", "Nguyen Van Hung", 2.5, 6.5, 5.0));
        list.add(new ThiSinh("AB04", "Nguyen Van Trang", 1.5, 5.5, 7.0));
        list.add(new ThiSinh("AB05", "Nguyen Tien Thanh", 0.5, 7.5, 5.0));
        list.add(new ThiSinh("AB06", "Tran Anh Dung", 2.2, 3.5, 6.2));
        list.add(new ThiSinh("AB07", "Nguyen Manh Cuong", 5.5, 5.5, 5.0));
        list.add(new ThiSinh("AB08", "Phung Ngoc Anh", 9.5, 9.5, 10.0));

        for (ThiSinh items : list){
            databaseThiSinh.InsertData(items);
        }
    }

    private void XapXep(){
        Comparator<ThiSinh> comparator = new Comparator<ThiSinh>() {
            @Override
            public int compare(ThiSinh t1, ThiSinh t2) {
                return GetTen(t1).compareTo(GetTen(t2));
            }
        };
        Collections.sort(dsTHiSinh, comparator);
    }

    private String GetTen(ThiSinh ts){
        String[] words = ts.getName().split("\\s");
        return words[words.length-1];
    }

    private void UpdateData() {
        dsTHiSinh.clear();
        for(ThiSinh items : databaseThiSinh.GetAllData()){
            dsTHiSinh.add(items);
        }
        XapXep();
        adpterThiSinh.notifyDataSetChanged();
    }

    private void Mapping(){
        listViewDS = (ListView) findViewById(R.id.listDS);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        btnAdd = (Button) findViewById(R.id.btnAdd);
    }
}