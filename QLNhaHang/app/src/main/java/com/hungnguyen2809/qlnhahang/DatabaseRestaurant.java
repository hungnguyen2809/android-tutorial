package com.hungnguyen2809.qlnhahang;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DatabaseRestaurant extends SQLiteOpenHelper {
    private String tblName = "Restaurant";
    private SQLiteDatabase dbWrite = getWritableDatabase();
    private SQLiteDatabase dbRead = getReadableDatabase();

    public DatabaseRestaurant(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void CreateTableRestaurant(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tblName + " ( " +
                " Ma NVARCHAR(20) PRIMARY KEY," +
                " Name NVARCHAR(250)," +
                " Address NVARCHAR(250)," +
                " Point DOUBLE )";
        dbWrite.execSQL(sql);
    }

    public void InsertData(Restaurant res){
        String sql = "INSERT INTO " + tblName + " VALUES ( '"+res.getMa().toLowerCase()+"', '"+res.getName()+"', '"+res.getAddress()+"', "+res.getPoint()+" ) ";
        dbWrite.execSQL(sql);
    }

    public void DeleteData(String ma){
        String sql = "DELETE FROM " + tblName + " WHERE Ma = '"+ma+"' ";
        dbWrite.execSQL(sql);
    }

    public void UpdateData(Restaurant res){
        String sql = "UPDATE "+tblName+" SET Name = '"+res.getName()+"', Address = '"+res.getAddress()+"', Point = "+res.getPoint()+" WHERE Ma = '"+res.getMa()+"' ";
        dbWrite.execSQL(sql);
    }

    public ArrayList<Restaurant> GetAllData(){
        String sql = "SELECT * FROM " + tblName;
        Cursor cursor = dbRead.rawQuery(sql, null);
        ArrayList<Restaurant> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String ma = cursor.getString(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            Double point = cursor.getDouble(3);
            list.add(new Restaurant(ma, name, point, address));
        }
        return list;
    }

    public boolean CheckMa(String ma){
        String sql = "SELECT * FROM " + tblName + " WHERE Ma = '"+ma.toLowerCase()+"' ";
        Cursor cursor = dbRead.rawQuery(sql, null);
        ArrayList<String> listMa = new ArrayList<>();
        while (cursor.moveToNext()){
            String a = cursor.getString(0);
            listMa.add(a);
        }
        if (listMa.size() == 0){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
