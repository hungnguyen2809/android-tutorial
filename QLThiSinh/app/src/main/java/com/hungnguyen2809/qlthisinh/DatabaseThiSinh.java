package com.hungnguyen2809.qlthisinh;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseThiSinh extends SQLiteOpenHelper {
    private String tblName = "ThiSinh";
    private SQLiteDatabase dbWrite = getWritableDatabase();
    private SQLiteDatabase dbRead = getReadableDatabase();

    public DatabaseThiSinh(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tblName + " ( " +
                " SBD NVARCAHR(50) PRIMARY KEY, " +
                " HoTen NVARCHAR(250)," +
                " Toan DOUBlE," +
                " Ly DOUBlE ," +
                " Hoa DOUBlE )";
        dbWrite.execSQL(sql);
    }

    public void InsertData(ThiSinh thiSinh){
        String sql = "INSERT INTO "+tblName+" VALUES('"+thiSinh.getSBD()+"', '"+thiSinh.getName()+"', "+thiSinh.getToan()+", "+thiSinh.getLy()+", "+thiSinh.getHoa()+" )";
        dbWrite.execSQL(sql);
    }

    public void UpdateData(ThiSinh thiSinh){
        String sql = "UPDATE " +tblName+ " SET HoTen = "+thiSinh.getName()+", Toan = "+thiSinh.getToan()+", Ly = "+thiSinh.getLy()+", Hoa = "+thiSinh.getHoa()+" WHERE SBD = "+thiSinh.getSBD()+" ";
        dbWrite.execSQL(sql);
    }

    public void DeleteData(String sbd){
        String sql = "DELETE FROM " + tblName + " WHERE SBD = '"+sbd+"' ";
        dbWrite.execSQL(sql);
    }

    public ArrayList<ThiSinh> GetAllData(){
        String sql = "SELECT * FROM " +tblName; //xắp xếp giảm dần , DESC tăng dần
        Cursor cursor = dbRead.rawQuery(sql, null);
        ArrayList<ThiSinh> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String sbd = cursor.getString(0);
            String name = cursor.getString(1);
            double toan =cursor.getFloat(2);
            double ly = cursor.getFloat(3);
            double hoa = cursor.getFloat(4);
            list.add(new ThiSinh(sbd, name, toan, ly, hoa));
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
