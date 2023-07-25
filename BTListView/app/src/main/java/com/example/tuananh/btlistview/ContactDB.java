package com.example.tuananh.btlistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ContactDB extends SQLiteOpenHelper {

    public static final String TableName = "ContactTable";
    public static final String Id = "Id";
    public static final String Name = "Fullname";
    public static final String Phone = "Phonenumber";

    public ContactDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Hàm chạy khi tạo mới csdl
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Câu lệnh tạo bảng TableContact
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + TableName + "(" + Id + " INTEGER PRIMARY KEY, "
                            + Name + " TEXT, "
                            + Phone + " TEXT)";
        //Thực thi câu lệnh
        db.execSQL(sqlCreate);
    }

    //Hàm chạy khi cập nhật csdl
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(db);
    }

    //Lấy tất cả các dòng dữ liệu trong bảng TableContact trả về dạng ArrayList
    public ArrayList<Contact> getContactAll(){
        ArrayList<Contact> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TableName ;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery(sql,null);
        if(cursor != null){
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                list.add(new Contact(id,name,phone));
            }
        }
        return list;
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, contact.getId());
        values.put(Name, contact.getName());
        values.put(Phone, contact.getPhone());
        db.insert(TableName,null,values);
        db.close();
    }

    public void updateContact(int id, Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, contact.getId());
        values.put(Name, contact.getName());
        values.put(Phone, contact.getPhone());
        db.update(TableName,values,Id + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TableName + " WHERE " + Id + "=" + id);
//      db.delete(TableName,Id + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
