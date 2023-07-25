package com.hungnguyen2809.qlnhahang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listDanhSach;
    Button btnAdd;
    EditText edtSearch;
    AdapterRestaurant adapterRestaurant;
    ArrayList<Restaurant> dsRestaurant;
    public static DatabaseRestaurant databaseRestaurant;
    private int RESULT_CODE_ADD = 222;
    private int RESULT_CODE_EDIT = 333;
    private int location = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        databaseRestaurant = new DatabaseRestaurant(this, "NhaHang", null, 1);
        databaseRestaurant.CreateTableRestaurant();
        dsRestaurant = new ArrayList<>();
        adapterRestaurant = new AdapterRestaurant(this, R.layout.line_custom_restaurant, dsRestaurant);
        listDanhSach.setAdapter(adapterRestaurant);
        UpdateData();

        registerForContextMenu(listDanhSach);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Add_Restaurant.class);
                startActivityForResult(intent, RESULT_CODE_ADD);
            }
        });

        listDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                location = i;
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapterRestaurant.filterRestaurant().filter(charSequence);
                adapterRestaurant.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULT_CODE_ADD && resultCode == RESULT_OK && data != null){
            Restaurant restaurant = (Restaurant) data.getSerializableExtra("data");
            databaseRestaurant.InsertData(restaurant);
            Toast.makeText(this, "Them thanh cong !", Toast.LENGTH_SHORT).show();
            UpdateData();
        }

        if (requestCode == RESULT_CODE_EDIT && resultCode == RESULT_OK && data != null){
            Restaurant restaurant = (Restaurant) data.getSerializableExtra("data");
            databaseRestaurant.UpdateData(restaurant);
            Toast.makeText(this, "Sua thanh cong", Toast.LENGTH_SHORT).show();
            UpdateData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit:
                Intent intent = new Intent(MainActivity.this, EditRestaurant.class );
                intent.putExtra("data", dsRestaurant.get(location));
                startActivityForResult(intent, RESULT_CODE_EDIT);
                break;
            case R.id.menu_delete:
                DeleteRestaurant();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void DeleteRestaurant(){
        AlertDialog.Builder alertDelete = new AlertDialog.Builder(MainActivity.this);
        alertDelete.setTitle("Thong bao");
        alertDelete.setMessage("Ban co chac muon xoa nha hang nay !");
        alertDelete.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Restaurant restaurant = dsRestaurant.get(location);
                databaseRestaurant.DeleteData(restaurant.getMa());
                Toast.makeText(MainActivity.this, "Xoa thanh cong !", Toast.LENGTH_SHORT).show();
                UpdateData();
            }
        });
        alertDelete.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDelete.show();
    }

    private void Mapping(){
        listDanhSach = (ListView)findViewById(R.id.listViewDanhSach);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
    }

    private void UpdateData(){
        dsRestaurant.clear();
        for (Restaurant items : databaseRestaurant.GetAllData()){
            dsRestaurant.add(items);
        }
        adapterRestaurant.notifyDataSetChanged();
    }

}