package com.hungnv28.quanlysanpham.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "QuanLySanPham", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS USERS(" +
                "USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_NAME TEXT NOT NULL, " +
                "PASS_WORD TEXT NOT NULL, " +
                "FULL_NAME TEXT)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS PRODUCT_CATEGORIES(" +
                "CATEGORY_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CATEGORY_CODE TEXT NOT NULL, " +
                "CATEGORY_NAME TEXT NOT NULL)";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS PRODUCTS(" +
                "PRODUCT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "PRODUCT_CODE TEXT NOT NULL," +
                "PRODUCT_NAME TEXT NOT NULL, " +
                "PRICE NUMBER, " +
                "QUANTITY INTEGER, " +
                "IMAGE_URL TEXT, " +
                "CATEGORY_ID INTEGER REFERENCES PRODUCT_CATEGORIES(CATEGORY_ID))";
        db.execSQL(sql);

        sql = "INSERT INTO USERS VALUES(NULL, 'admin', 'admin', 'Nguyễn Văn Hùng')";
        db.execSQL(sql);

        sql = "INSERT INTO PRODUCT_CATEGORIES VALUES(1, 'BANH', 'Bánh')";
        db.execSQL(sql);
        sql = "INSERT INTO PRODUCT_CATEGORIES VALUES(2, 'KEO', 'Kẹo')";
        db.execSQL(sql);

        sql = "INSERT INTO PRODUCTS VALUES(NULL, 'CHOCOLATE', 'Kẹo socola', 20000, 100, null, 2)";
        db.execSQL(sql);
        sql = "INSERT INTO PRODUCTS VALUES(NULL, 'CHCOPIPE', 'Bánh Choco Pipe', 45000, 50, null, 1)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USERS");
        db.execSQL("DROP TABLE IF EXISTS PRODUCTS");
        db.execSQL("DROP TABLE IF EXISTS PRODUCT_CATEGORIES");
        onCreate(db);
    }
}
