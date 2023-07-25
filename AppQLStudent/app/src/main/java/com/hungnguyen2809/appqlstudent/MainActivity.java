package com.hungnguyen2809.appqlstudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    database db;
    ListView listStudent;
    EditText edtSearch;
    Button btAdd;
    AdapterStudent adapterStudent;
    ArrayList<Student> dsStudent;
    final int REQUEST_CODE_ADD_STUDENT = 666;
    final int REQUEST_CODE_EDIT = 888;
    int location = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        dsStudent = new ArrayList<>();
        adapterStudent = new AdapterStudent(this, R.layout.line_layout_student, dsStudent);
        listStudent.setAdapter(adapterStudent);

        registerForContextMenu(listStudent);

        db = new database(this,"QLStudent", null, 1);
        db.CreateTable();
        UpdateData();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(MainActivity.this, Add_Student_Activity.class);
                startActivityForResult(intentAdd, REQUEST_CODE_ADD_STUDENT);
            }
        });

        listStudent.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                location = position;
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapterStudent.getFilter().filter(s);
                adapterStudent.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_student, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_delete:
                db.DeleteStudent(dsStudent.get(location).getSbd());
                UpdateData();
                Toast.makeText(this, "Xoá thành công !", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_edit:
                Intent intent = new Intent(MainActivity.this, EditStudentActivity.class);
                intent.putExtra("data", dsStudent.get(location));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_ADD_STUDENT && resultCode == RESULT_OK && data != null){
            Student student = (Student) data.getSerializableExtra("data");
            if (checkSDB(student.getSbd())){
                db.InsertStudent(student);
                UpdateData();
                Toast.makeText(this, "Thêm thành công !", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Đã tồn tại SBD này !", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null){
            Student student = (Student) data.getSerializableExtra("student");
            db.UpdateStudent(student);
            UpdateData();
            Toast.makeText(this, "Sửa thành công!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Mapping(){
        listStudent = (ListView) findViewById(R.id.listViewStudent);
        edtSearch = (EditText) findViewById(R.id.editTextSearch);
        btAdd = (Button) findViewById(R.id.buttonAdd);
    }

    private boolean checkSDB(String sbd){
        for (String vl : db.GetSBDStudent()){
            if (vl.equals(sbd))
                return false;
        }
        return true;
    }

    private void UpdateData(){
        dsStudent.clear();
        for(Student student : db.GetAllData()){
            dsStudent.add(student);
        }
        adapterStudent.notifyDataSetChanged();
    }
}
