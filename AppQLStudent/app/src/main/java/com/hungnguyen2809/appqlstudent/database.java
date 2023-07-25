package com.hungnguyen2809.appqlstudent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class database extends SQLiteOpenHelper {
    private String tblName = "Student";

    public database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void SQLNonResultData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor SQLResultData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    public void CreateTable(){
        String sql = "CREATE TABLE IF NOT EXISTS " + tblName + " ( " +
                " SBD NVARCHAR(250) PRIMARY KEY, " +
                " Name NVARCHAR(250), " +
                " Toan DOUBLE, " +
                " Ly DOUBLE, " +
                " Hoa DOUBLE )";
        SQLNonResultData(sql);
    }

    public ArrayList<Student> GetAllData(){
        String sql = "SELECT * FROM " + tblName ;
        Cursor cursor = SQLResultData(sql);
        ArrayList<Student> students = new ArrayList<>();
        while (cursor.moveToNext()){
            String sbd = cursor.getString(0);
            String name = cursor.getString(1);
            Double toan = cursor.getDouble(2);
            Double ly = cursor.getDouble(3);
            Double hoa = cursor.getDouble(4);
            students.add(new Student(sbd, name, toan, ly, hoa));
        }
        return students;
    }

    public void DeleteStudent(String sbd){
        String sql = "DELETE FROM " + tblName + " WHERE SBD = '"+ sbd +"' ";
        SQLNonResultData(sql);
    }

    public void InsertStudent(Student student){
        String sql = "INSERT INTO " + tblName + " VALUES ('"+ student.getSbd() +"', '"+ student.getName() +"'," +
                " "+ student.getDiemToan() +", "+ student.getDiemLy() +", "+ student.getDiemHoa() +" ) ";
        SQLNonResultData(sql);
    }

    public void UpdateStudent(Student student){
        String sql = "UPDATE " + tblName + " SET Name = '"+ student.getName() +"', Toan = "+ student.getDiemToan() +"," +
                " Ly = " + student.getDiemLy() + ", Hoa = "+ student.getDiemHoa() +" WHERE SBD = '"+ student.getSbd() +"' ";
        SQLNonResultData(sql);
    }

    public ArrayList<String> GetSBDStudent(){
        ArrayList<String> listSBD = new ArrayList<>();
        String sql = "SELECT SBD FROM " + tblName;
        Cursor cursor = SQLResultData(sql);
        while (cursor.moveToNext()){
            String sbd = cursor.getString(0);
            listSBD.add(sbd);
        }
        return listSBD;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}
