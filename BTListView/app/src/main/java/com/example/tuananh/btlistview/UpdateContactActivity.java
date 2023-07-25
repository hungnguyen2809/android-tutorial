package com.example.tuananh.btlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateContactActivity extends AppCompatActivity {

    private EditText edtId,edtName,edtPhone;
    private Button btnSave,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        AnhXa();

        ReceiveData();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                if(isStringOnlyDigits(id) == false){
                    Toast.makeText(UpdateContactActivity.this, "Id chỉ gồm số, k đc rỗng", Toast.LENGTH_SHORT).show();
                }
                else if(isStringOnlyAlphabets(name) == false){
                    Toast.makeText(UpdateContactActivity.this, "Name chỉ gồm kí tự alphabets, k đc rỗng", Toast.LENGTH_SHORT).show();
                }
                else if(isStringOnlyDigits(phone) == false || phone.length() != 10){
                    Toast.makeText(UpdateContactActivity.this, "Phone chỉ gồm số , đúng 10 kí tự và k đc rỗng", Toast.LENGTH_SHORT).show();
                }
                else{
                    SendData();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateContactActivity.this, MainActivity.class));
            }
        });
    }

    private void AnhXa() {
        edtId = findViewById(R.id.editTextIdUD);
        edtName = findViewById(R.id.editTextNameUD);
        edtPhone = findViewById(R.id.editTextPhoneUD);
        btnSave =findViewById(R.id.buttonSave);
        btnCancel = findViewById(R.id.buttonCancelUD);
    }

    private void SendData(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("Id", Integer.parseInt(edtId.getText().toString().trim()));
        bundle.putString("Name", edtName.getText().toString().trim());
        bundle.putString("Phone", edtPhone.getText().toString().trim());
        intent.putExtra("Data",bundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    private void ReceiveData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Data");
        int Id = bundle.getInt("Id");
        String name = bundle.getString("Name");
        String Phone = bundle.getString("Phone");
        edtId.setText(Id+"");
        edtName.setText(name);
        edtPhone.setText(Phone);
    }

    private boolean isStringOnlyAlphabets(String str){
        return ((str != null) && (!str.equals("")) && (str.matches("^[a-z A-Z]*$")));
    }

    private  boolean isStringOnlyDigits(String str){
        return ((str != null) && (!str.equals("")) && (str.matches("[0-9]+")));
    }
}
