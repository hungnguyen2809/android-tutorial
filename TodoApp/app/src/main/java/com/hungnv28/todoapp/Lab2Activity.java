package com.hungnv28.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hungnv28.todoapp.adapter.TodoRecyclerAdapter;
import com.hungnv28.todoapp.dao.TodoDAO;
import com.hungnv28.todoapp.model.Todo;

import java.util.ArrayList;

public class Lab2Activity extends AppCompatActivity {

    RecyclerView rcv;
    FloatingActionButton btnFAdd;

    TodoDAO todoDAO;
    ArrayList<Todo> listTodo;
    TodoRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2);

        rcv = findViewById(R.id.rcvTodo);
        btnFAdd = findViewById(R.id.btnFAdd);

        todoDAO = new TodoDAO(Lab2Activity.this);
        listTodo = todoDAO.getAll();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Lab2Activity.this, LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(layoutManager);

        adapter = new TodoRecyclerAdapter(Lab2Activity.this, listTodo);
        rcv.setAdapter(adapter);

        btnFAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTodoDialog();
            }
        });
    }

    private void showTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Lab2Activity.this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_todo, null);
        builder.setView(view);

        Dialog dialog = builder.create();
        dialog.show();

        Button btnSave = view.findViewById(R.id.btnDLogSave);
        Button btnCancel = view.findViewById(R.id.btnDLogCancel);
        EditText edtTitle = view.findViewById(R.id.edtDLogTitle);
        EditText edtContent = view.findViewById(R.id.edtDLogContent);
        EditText edtDate = view.findViewById(R.id.edtDLogDate);
        EditText edtType = view.findViewById(R.id.edtDLogType);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
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
                    dialog.dismiss();
                    listTodo = todoDAO.getAll();
                    adapter.setNotifyDataChange(listTodo);
                    Toast.makeText(Lab2Activity.this, "Insert successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Lab2Activity.this, "Insert failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}