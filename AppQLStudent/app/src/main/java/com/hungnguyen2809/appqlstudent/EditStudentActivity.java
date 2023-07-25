package com.hungnguyen2809.appqlstudent;

import androidx.annotation.IntRange;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditStudentActivity extends AppCompatActivity {
    EditText edtSBD, edtName, edtDToan, edtDLy, edtDHoa;
    Button btAccept, btCancel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        Mapping();
        GetData();
        edtSBD.setEnabled(false);

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
                intent.putExtra("student", student);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void GetData(){
        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("data");
        edtSBD.setText(student.getSbd());
        edtName.setText(student.getName());
        edtDToan.setText(student.getDiemToan() + "");
        edtDLy.setText(student.getDiemLy() + "");
        edtDHoa.setText(student.getDiemHoa() + "");
    }

    private void Mapping(){
        edtSBD = (EditText) findViewById(R.id.editTextSBDEdit);
        edtName = (EditText) findViewById(R.id.editTextNameEdit);
        edtDToan = (EditText) findViewById(R.id.editTextDiemToanEdit);
        edtDLy = (EditText) findViewById(R.id.editTextDiemLyEdit);
        edtDHoa = (EditText) findViewById(R.id.editTextDiemHoaEdit);
        btAccept = (Button) findViewById(R.id.buttonAcceptAddEdit);
        btCancel = (Button) findViewById(R.id.buttonCancelAddEdit);
    }
}
