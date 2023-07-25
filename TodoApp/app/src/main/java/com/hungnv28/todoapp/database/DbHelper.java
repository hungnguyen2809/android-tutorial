package com.hungnv28.todoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "TodoApp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS TODO_APP (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TITLE TEXT, " +
                "CONTENT TEXT, " +
                "CREATE_DATE DATE, " +
                "TYPE TEXT)";
        db.execSQL(sql);

        sql = "INSERT INTO TODO_APP VALUES (NULL, 'Học bài', 'Lập trình hướng đối tượng', '2023-07-22', 'EDU')";
        db.execSQL(sql);
        sql = "INSERT INTO TODO_APP VALUES (NULL, 'Giải trí', 'Chơi game', '2023-07-22', 'ENT')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TODO_APP");
        onCreate(db);
    }
}
