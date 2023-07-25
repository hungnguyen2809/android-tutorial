package com.example.tuananh.btlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    private EditText edtId, edtName, edtPhone;
    private Button btnAdd, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        AnhXa();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                if (isStringOnlyDigits(id) == false) {
                    Toast.makeText(AddContactActivity.this, "Id chỉ gồm số, k đc rỗng", Toast.LENGTH_SHORT).show();
                } else if (isStringOnlyAlphabets(name) == false) {
                    Toast.makeText(AddContactActivity.this, "Name chỉ gồm kí tự alphabets, k đc rỗng", Toast.LENGTH_SHORT).show();
                } else if (isStringOnlyDigits(phone) == false || phone.length() != 10) {
                    Toast.makeText(AddContactActivity.this, "Phone chỉ gồm số , đúng 10 kí tự và k đc rỗng", Toast.LENGTH_SHORT).show();
                } else {
                    SendData();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddContactActivity.this, MainActivity.class));
            }
        });
    }

    private void AnhXa() {
        edtId = findViewById(R.id.editTextId);
        edtName = findViewById(R.id.editTextName);
        edtPhone = findViewById(R.id.editTextPhone);
        btnAdd = findViewById(R.id.buttonAdd);
        btnCancel = findViewById(R.id.buttonCancel);
    }

    private void SendData() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("Id", Integer.parseInt(edtId.getText().toString().trim()));
        bundle.putString("Name", edtName.getText().toString().trim());
        bundle.putString("Phone", edtPhone.getText().toString().trim());
        intent.putExtra("Data", bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean isStringOnlyAlphabets(String str) {
        return ((str != null) && (!str.equals("")) && (str.matches("^[a-z A-Z]*$")));
    }

    private boolean isStringOnlyDigits(String str) {
        return ((str != null) && (!str.equals("")) && (str.matches("[0-9]+")));
    }
}
