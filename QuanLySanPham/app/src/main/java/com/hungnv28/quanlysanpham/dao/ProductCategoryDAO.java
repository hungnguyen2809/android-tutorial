package com.hungnv28.quanlysanpham.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hungnv28.quanlysanpham.database.DbHelper;
import com.hungnv28.quanlysanpham.model.ProductCategory;

import java.util.ArrayList;

public class ProductCategoryDAO {
    SQLiteDatabase db;
    DbHelper helper;

    public ProductCategoryDAO(Context context) {
        helper = new DbHelper(context);
    }


    public ArrayList<ProductCategory> getAll() {
        ArrayList<ProductCategory> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCT_CATEGORIES", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String code = cursor.getString(1);
            String name = cursor.getString(2);
            list.add(new ProductCategory(id, code, name));
            cursor.moveToNext();
        }

        db.close();
        return list;
    }

}
