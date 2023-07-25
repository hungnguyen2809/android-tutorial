package com.hungnv28.todoapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.hungnv28.todoapp.database.DbHelper;
import com.hungnv28.todoapp.model.Todo;

import java.util.ArrayList;

public class TodoDAO {
    private final DbHelper dbHelper;
    private SQLiteDatabase db;

    public TodoDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public boolean insert(Todo todo) {
        if (todo.getTitle().isEmpty() || todo.getContent().isEmpty() || todo.getCreDate().isEmpty() || todo.getType().isEmpty()) {
            return false;
        }
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", todo.getTitle());
        values.put("CONTENT", todo.getContent());
        values.put("CREATE_DATE", todo.getCreDate());
        values.put("TYPE", todo.getType().toUpperCase());
        long row = db.insert("TODO_APP", null, values);

        db.close();
        return row != -1;
    }

    public boolean upgrade(Todo todo) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", todo.getTitle());
        values.put("CONTENT", todo.getContent());
        values.put("CREATE_DATE", todo.getCreDate());
        values.put("TYPE", todo.getType().toUpperCase());
        int row = db.update("TODO_APP", values, "ID = ?", new String[]{String.valueOf(todo.getId())});

        db.close();
        return row > 0;
    }

    public boolean delete(int id) {
        db = dbHelper.getWritableDatabase();
        int row = db.delete("TODO_APP", "ID = ?", new String[]{String.valueOf(id)});
        db.close();
        return row > 0;
    }

    public ArrayList<Todo> getAll() {
        ArrayList<Todo> listTodo = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String sql = "SELECT * FROM TODO_APP;";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String creDate = cursor.getString(3);
            String type = cursor.getString(4);

            Todo todo = new Todo(id, title, content, creDate, type);
            listTodo.add(todo);
            cursor.moveToNext();
        }

        db.close();
        return listTodo;
    }

    @Nullable
    public Todo getById(int id) {
        ArrayList<Todo> listTodo = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        String sql = "SELECT * FROM TODO_APP WHERE ID = " + id + ";";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int idTodo = cursor.getInt(0);
            String title = cursor.getString(1);
            String content = cursor.getString(2);
            String creDate = cursor.getString(3);
            String type = cursor.getString(4);

            Todo todo = new Todo(idTodo, title, content, creDate, type);
            listTodo.add(todo);
            cursor.moveToNext();
        }

        db.close();
        return listTodo.get(0);
    }


}
