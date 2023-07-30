package com.hungnv28.quanlysanpham.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hungnv28.quanlysanpham.database.DbHelper;
import com.hungnv28.quanlysanpham.model.Product;

import java.util.ArrayList;

public class ProductDAO {
    private DbHelper helper;
    private SQLiteDatabase db;

    public ProductDAO(Context context) {
        helper = new DbHelper(context);
    }

    public ArrayList<Product> getAll() {
        ArrayList<Product> list = new ArrayList<>();
        db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCTS", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String code = cursor.getString(1);
            String name = cursor.getString(2);
            long price = cursor.getLong(3);
            int quantity = cursor.getInt(4);
            int cateId = cursor.getInt(5);

            list.add(new Product(id, code, name, price, quantity, cateId));
            cursor.moveToNext();
        }
        db.close();
        return list;
    }

    public boolean insertProduct(Product product) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PRODUCT_CODE", product.getCode());
        values.put("PRODUCT_NAME", product.getName());
        values.put("PRICE", product.getPrice());
        values.put("QUANTITY", product.getQuantity());
        values.put("CATEGORY_ID", product.getCategoryId());
        long row = db.insert("PRODUCTS", null, values);

        db.close();
        return row != -1;
    }

    public boolean updateProduct(Product product) {
        db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PRODUCT_CODE", product.getCode());
        values.put("PRODUCT_NAME", product.getName());
        values.put("PRICE", product.getPrice());
        values.put("QUANTITY", product.getQuantity());
        values.put("CATEGORY_ID", product.getCategoryId());
        int row = db.update("PRODUCTS", values, "PRODUCT_ID = ?", new String[]{String.valueOf(product.getId())});

        db.close();
        return row > 0;
    }

    public boolean deleteProduct(int id) {
        db = helper.getWritableDatabase();
        int row = db.delete("PRODUCTS", "PRODUCT_ID = ? ", new String[]{String.valueOf(id)});
        db.close();
        return row != 0;
    }
}
