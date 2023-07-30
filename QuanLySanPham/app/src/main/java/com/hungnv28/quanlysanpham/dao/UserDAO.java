package com.hungnv28.quanlysanpham.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.hungnv28.quanlysanpham.database.DbHelper;
import com.hungnv28.quanlysanpham.model.User;

public class UserDAO {
    private DbHelper helper;
    private SQLiteDatabase db;

    public UserDAO(Context context) {
        helper = new DbHelper(context);
    }

    public User checkLogin(String username, String password) {
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ? AND PASS_WORD = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        cursor.moveToFirst();
        int count = cursor.getCount();
        db.close();
        if (count > 0) {
            int id = cursor.getInt(0);
            String us = cursor.getString(1);
            String pw = cursor.getString(2);
            String fn = cursor.getString(3);
            return new User(id, us, pw, fn);
        }
        return null;
    }

    public boolean checkUserName(String username) {
        db = helper.getReadableDatabase();
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        int count = cursor.getCount();
        db.close();
        return !(count > 0);
    }

    public void registerUser(@NonNull User user) throws Exception {
        boolean checkUser = checkUserName(user.getUsername());
        if (!checkUser) {
            throw new Exception("Tải khoản đã tồn tại");
        }

        db = helper.getWritableDatabase();
//        String sql = "INSERT INTO USERS VALUES (NULL, ?, ?, ?)";
//        db.execSQL(sql, new String[]{user.getUsername(), user.getPassword(), user.getFullName()});
        ContentValues values = new ContentValues();
        values.put("USER_NAME", user.getUsername());
        values.put("PASS_WORD", user.getPassword());
        values.put("FULL_NAME", user.getFullName());
        long row = db.insert("USERS", null, values);
        db.close();
        if (row == -1) {
            throw new Exception("Đăng ký thất bại");
        }
    }

    public void forgetPassword(String us, String pw) throws Exception {
        boolean checkUser = checkUserName(us);
        if (checkUser) {
            throw new Exception("Tài khoản không tồn tại");
        }

        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PASS_WORD", pw);
        int row = db.update("USERS", values, "USER_ID = ?", new String[]{us});
        db.close();

        if (!(row > 0)) {
            throw new Exception("Cập nhật không thành công");
        }
    }
}
