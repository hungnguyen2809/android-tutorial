package com.hungnguyen2809.qlnhahang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Restaurant extends AppCompatActivity {
    EditText edtMa;
    EditText edtName;
    EditText edtAddress;
    EditText edtPoint;
    Button btnAdd;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__restaurant);
        Mapping();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Add_Restaurant.this);
                dialog.setTitle("Thong bao");
                dialog.setMessage("Ban co chac muon them nha hang nay khong ?");
                dialog.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String ma = edtMa.getText().toString().trim();
                        String name = edtName.getText().toString().trim();
                        String address = edtAddress.getText().toString().trim();
                        double point = Double.parseDouble(edtPoint.getText().toString().trim());

                        if(MainActivity.databaseRestaurant.CheckMa(ma)){
                            Restaurant restaurant = new Restaurant(ma, name, point, address);
                            Intent intent = new Intent();
                            intent.putExtra("data", restaurant);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        else {
                            Toast.makeText(Add_Restaurant.this, "Ma nha hang da ton tai !", Toast.LENGTH_SHORT).show();
                        }
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

    private void Mapping(){
        edtMa = (EditText) findViewById(R.id.edtMa);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtPoint = (EditText) findViewById(R.id.edtPoint);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);
    }
}