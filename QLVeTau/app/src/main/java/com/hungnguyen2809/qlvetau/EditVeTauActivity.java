package com.hungnguyen2809.qlvetau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class EditVeTauActivity extends AppCompatActivity {
    EditText edtGaDi;
    EditText edtGaDen;
    EditText edtGia;
    RadioButton rdKH;
    RadioButton rdMC;
    Button btnSave;
    Button btnCancel;
    private int ma = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ve_tau);
        Mapping();
        SetData();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gaDi = edtGaDi.getText().toString().trim();
                String gaDen = edtGaDen.getText().toString().trim();
                long gia = Long.parseLong(edtGia.getText().toString().trim());

                VeTau vt = new VeTau(ma, gaDi, gaDen, gia, isStatus());
                Intent intent = new Intent();
                intent.putExtra("data", vt);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void SetData(){
        Intent intent = getIntent();
        VeTau vt = (VeTau) intent.getSerializableExtra("data");
        edtGaDi.setText(vt.getGaDi());
        edtGaDen.setText(vt.getGaDen());
        edtGia.setText(String.valueOf(vt.getGia()));
        if (vt.isStatus()){
            rdKH.setChecked(true);
        }
        else {
            rdMC.setChecked(true);
        }
        ma = vt.getMa();
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
        edtGaDi = (EditText) findViewById(R.id.edtGaDiEdit);
        edtGaDen = (EditText) findViewById(R.id.edtGaDenEdit);
        edtGia = (EditText) findViewById(R.id.edtGiaEdit);
        rdKH = (RadioButton)findViewById(R.id.radioButtonKHEdit);
        rdMC = (RadioButton)findViewById(R.id.radioButtonMCEdit);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancelEdit);
    }
}