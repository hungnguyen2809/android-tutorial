package com.hungnguyen2809.qlnhahang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditRestaurant extends AppCompatActivity {
    EditText edtName;
    EditText edtAddress;
    EditText edtPoint;
    Button btnSua;
    Button btnHuy;
    String ma = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);
        Mapping();
        SetData();

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditRestaurant.this);
                dialog.setTitle("Thong bao");
                dialog.setMessage("Ban co chac muon sua nha hang nay khong ?");
                dialog.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = edtName.getText().toString().trim();
                        String address = edtAddress.getText().toString().trim();
                        double point = Double.parseDouble(edtPoint.getText().toString().trim());

                        Restaurant restaurant = new Restaurant(ma, name, point, address);
                        Intent intent = new Intent();
                        intent.putExtra("data", restaurant);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                dialog.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

    private void SetData(){
        Intent intent = getIntent();
        Restaurant restaurant = (Restaurant) intent.getSerializableExtra("data");
        edtName.setText(restaurant.getName());
        edtAddress.setText(restaurant.getAddress());
        edtPoint.setText(String.valueOf(restaurant.getPoint()));
        ma = restaurant.getMa();
    }

    private void Mapping(){
        edtName = (EditText) findViewById(R.id.edtNameEdit);
        edtAddress = (EditText) findViewById(R.id.edtAddressEdit);
        edtPoint = (EditText) findViewById(R.id.edtPointEdit);
        btnSua = (Button) findViewById(R.id.btnAddEdit);
        btnHuy = (Button) findViewById(R.id.btnCancelEdit);
    }
}