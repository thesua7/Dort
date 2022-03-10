package si.nsu.dort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DB_Name= "Dort.db";
    public DBhelper(Context context) {
        super(context, "Dort.db",null, 1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase Mydb, int i, int i1) {
        Mydb.execSQL("drop table if exists users");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(email TEXT primary key,name TEXT,password TEXT)");

    }



    public Boolean insertData(String email,String name,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email",email);
        values.put("password",password);
        values.put("name",name);

        long result = db.insert("users",null,values);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkemail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean CheckEmailPass(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email=? and password=? ", new String[]{email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }



}
