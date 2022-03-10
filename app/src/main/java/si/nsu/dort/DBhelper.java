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
        super(context, DB_Name,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(email TEXT primary key,name TEXT,password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
    }

    public boolean insertData(String email,String name,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email",email);
        values.put("password",password);
        values.put("name",name);

        long result = db.insert("users",null,values);
        if(result==-1)
            return false;
        else
            return true;
    }

    public boolean CheckEmail(String e_mail){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email=? ", new String[]{e_mail});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean CheckEmailPass(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where email=? and password=? ", new String[]{email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }



}
