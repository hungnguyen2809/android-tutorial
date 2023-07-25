package com.hungnguyen2809.qlvetau;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class AddVeTauActivity extends AppCompatActivity {
    EditText edtGaDi;
    EditText edtGaDen;
    EditText edtGia;
    RadioButton rdKH;
    RadioButton rdMC;
    Button btnAdd;
    Button btnCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ve_tau);
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
                AlertDialog.Builder confirm = new AlertDialog.Builder(AddVeTauActivity.this);
                confirm.setMessage("Ban co chac muon them ?");
                confirm.setPositiveButton("Dong y", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String gaDi = edtGaDi.getText().toString().trim();
                        String gaDen = edtGaDen.getText().toString().trim();
                        long gia = Long.parseLong(edtGia.getText().toString().trim());

                        VeTau vt = new VeTau(gaDi, gaDen, gia, isStatus());
                        Intent intent = new Intent();
                        intent.putExtra("data", vt);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                confirm.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                confirm.show();
            }
        });
    }

    private boolean isStatus(){
        boolean flag = false;
        if (rdKH.isChecked()){
            flag = true;
        }
        if (rdMC.isChecked()){
            flag = false;
        }
        return flag;
    }

    private void Mapping(){
        edtGaDi = (EditText) findViewById(R.id.edtGaDiAdd);
        edtGaDen = (EditText) findViewById(R.id.edtGaDenAdd);
        edtGia = (EditText) findViewById(R.id.edtGiaAdd);
        rdKH = (RadioButton)findViewById(R.id.radioButtonKH);
        rdMC = (RadioButton)findViewById(R.id.radioButtonMc);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancelAdd);
    }
}