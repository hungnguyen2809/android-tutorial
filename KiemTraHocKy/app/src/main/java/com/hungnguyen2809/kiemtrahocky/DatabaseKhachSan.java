package com.hungnguyen2809.kiemtrahocky;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseKhachSan extends SQLiteOpenHelper {
    private String tblName = "KhachSan";
    private SQLiteDatabase dbWrite = getWritableDatabase();
    private SQLiteDatabase dbRead = getReadableDatabase();

    public DatabaseKhachSan(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tblName + " ( " +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name NVARCHAR(250)," +
                " SoPhong INT," +
                " DonGia DOUBLE," +
                " SoNgayO INT )";
        dbWrite.execSQL(sql);
    }

    public void InsertData(HoaDon hd){
        String sql = "INSERT INTO " + tblName + " VALUES ( null, '"+hd.getName()+"', "+hd.getSoPhong()+", "+hd.getDonGia()+", "+hd.getSoNgayO()+" ) ";
        dbWrite.execSQL(sql);
    }

    public void DeleteData(int ma){
        String sql = "DELETE FROM " + tblName + " WHERE ID = "+ma+" ";
        dbWrite.execSQL(sql);
    }

    public ArrayList<HoaDon> GetAllData(){
        String sql = "SELECT * FROM " + tblName;
        Cursor cursor = dbRead.rawQuery(sql, null);
        ArrayList<HoaDon> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int ma = cursor.getInt(0);
            String name = cursor.getString(1);
            int soP = cursor.getInt(2);
            double donGia = cursor.getDouble(3);
            int soNgayO = cursor.getInt(4);
            list.add(new HoaDon(ma, name, soP, donGia, soNgayO));
        }
        return list;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
