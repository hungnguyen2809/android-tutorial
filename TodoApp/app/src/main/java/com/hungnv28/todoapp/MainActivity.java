package com.hungnv28.todoapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hungnv28.todoapp.adapter.TodoAdapter;
import com.hungnv28.todoapp.dao.TodoDAO;
import com.hungnv28.todoapp.model.Todo;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    TodoDAO todoDAO;

    Button btnAdd, btnUpdate, btnDelete;
    EditText edtTitle, edtContent, edtDate, edtType;
    ListView listView;

    ArrayList<Todo> listTodo;
    TodoAdapter todoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onMapView();
        todoDAO = new TodoDAO(MainActivity.this);

//        listTodo = new ArrayList<>();
//        listTodo.add(new Todo(1, "Học bài", "Làm bài tập toán", "2023-07-22", "edu"));
//        listTodo.add(new Todo(2, "Học bài", "Làm bài tập hóa", "2023-07-22", "edu"));
//        listTodo.add(new Todo(3, "Làm việc nhà", "Quét nhà", "2023-07-22", "home"));
//        listTodo.add(new Todo(4, "Làm việc nhà", "Rửa bát", "2023-07-22", "home"));
//        listTodo.add(new Todo(5, "Giải trí", "Chơi game", "2023-07-22", "entertainment"));

        listTodo = todoDAO.getAll();
        todoAdapter = new TodoAdapter(MainActivity.this, listTodo);
        listView.setAdapter(todoAdapter);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String creDate = edtDate.getText().toString();
                String type = edtType.getText().toString();

                Todo todo = new Todo(title, content, creDate, type);
                Log.d("INSERT_TODO", todo.toString());

                boolean result = todoDAO.insert(todo);
                if (result) {
                    onClearForm();
                    listTodo = todoDAO.getAll();
                    todoAdapter.setNotifyDataChange(listTodo);
                    Toast.makeText(MainActivity.this, "Insert successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = listTodo.get(position);
                setFieldForm(todo);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Lab2Activity.class);
                startActivity(intent);
//                activityResultLauncher.launch(intent);
            }
        });
    }

//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        // There are no request codes
//                        Intent data = result.getData();
//
//                    }
//                }
//            });


    private void onMapView() {
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtDate = findViewById(R.id.edtDate);
        edtType = findViewById(R.id.edtType);
        listView = findViewById(R.id.lvTodo);
    }

    private void onClearForm() {
        edtTitle.setText("");
        edtContent.setText("");
        edtDate.setText("");
        edtType.setText("");
    }

    private void setFieldForm(Todo todo) {
        edtTitle.setText(todo.getTitle());
        edtContent.setText(todo.getContent());
        edtDate.setText(todo.getCreDate());
        edtType.setText(todo.getType());
    }
}