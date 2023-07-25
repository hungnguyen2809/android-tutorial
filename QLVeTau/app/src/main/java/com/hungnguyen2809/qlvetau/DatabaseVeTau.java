package com.hungnguyen2809.qlvetau;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseVeTau extends SQLiteOpenHelper {
    private String tblName = "VeTau";
    private SQLiteDatabase dbWrite = getWritableDatabase();
    private SQLiteDatabase dbRead = getReadableDatabase();

    public DatabaseVeTau(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tblName + " ( " +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " GaDi NVARCHAR(250)," +
                " GaDen VARCHAR(250)," +
                " Gia LONG ," +
                " Status BOOL )";
        dbWrite.execSQL(sql);
    }

    public void InsertData(VeTau vt){
        int status = vt.isStatus() ? 1 : 0;
        String sql = "INSERT INTO "+tblName+" VALUES(null, '"+vt.getGaDi()+"', '"+vt.getGaDen()+"', "+vt.getGia()+", "+status+" )";
        dbWrite.execSQL(sql);
    }

    public void UpdateData(VeTau vt){
        int status = vt.isStatus() ? 1 : 0;
        String sql = "UPDATE " +tblName+ " SET GaDi = '"+vt.getGaDi()+"', GaDen = '"+vt.getGaDen()+"', Gia = "+vt.getGia()+", Status = "+status+" WHERE ID = "+vt.getMa()+" ";
        dbWrite.execSQL(sql);
    }

    public void DeleteData(int ma){
        String sql = "DELETE FROM " + tblName + " WHERE ID = "+ma+" ";
        dbWrite.execSQL(sql);
    }

    public ArrayList<VeTau> GetAllData(){
        String sql = "SELECT * FROM " +tblName; //xắp xếp giảm dần , DESC tăng dần
        Cursor cursor = dbRead.rawQuery(sql, null);
        ArrayList<VeTau> list = new ArrayList<>();
        while (cursor.moveToNext()){
            int ma = cursor.getInt(0);
            String gaDi = cursor.getString(1);
            String gaDen = cursor.getString(2);
            long gia = cursor.getLong(3);
            boolean status = (cursor.getInt(4) == 1 ? true : false);
            list.add(new VeTau(ma, gaDi, gaDen, gia, status));
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
