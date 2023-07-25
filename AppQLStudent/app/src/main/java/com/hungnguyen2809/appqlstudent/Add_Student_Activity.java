package com.hungnguyen2809.appqlstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_Student_Activity extends AppCompatActivity {
    EditText edtSBD, edtName, edtDToan, edtDLy, edtDHoa;
    Button btAccept, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__student_);
        Mapping();

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String sbd = edtSBD.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                double dToan= Double.parseDouble(edtDToan.getText().toString().trim());
                double dLy = Double.parseDouble(edtDLy.getText().toString().trim());
                double dHoa = Double.parseDouble(edtDHoa.getText().toString().trim());
                Student student = new Student(sbd, name, dToan, dLy, dHoa);

                intent.putExtra("data", student);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void Mapping(){
        edtSBD = (EditText) findViewById(R.id.editTextSBD);
        edtName = (EditText) findViewById(R.id.editTextName);
        edtDToan = (EditText) findViewById(R.id.editTextDiemToan);
        edtDLy = (EditText) findViewById(R.id.editTextDiemLy);
        edtDHoa = (EditText) findViewById(R.id.editTextDiemHoa);
        btAccept = (Button) findViewById(R.id.buttonAcceptAdd);
        btCancel = (Button) findViewById(R.id.buttonCancelAdd);
    }
}
