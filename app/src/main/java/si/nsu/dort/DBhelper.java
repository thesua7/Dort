package si.nsu.dort;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DB_Name= "Dort.db";
    public DBhelper(Context context) {
        super(context, "Dort.db",null, 1);
    }
    @Override
    public void onUpgrade(SQLiteDatabase Mydb, int i, int i1) {
        Mydb.execSQL("drop table if exists user");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(Mail TEXT primary key,Name TEXT,password TEXT,Sex TEXT,Id INTEGER,Bio TEXT)");

    }




    public Boolean insertData_(String email,String name,String password,String gender,String id,String bio){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Mail",email);
        values.put("Name",name);
        values.put("password",password);
        values.put("Sex",gender);
        values.put("Id",Integer.parseInt(id));
        values.put("Bio",bio);

        long result = db.insert("user",null,values);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkemail_(String email) {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where mail = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean CheckEmailPass(String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where mail=? and password=? ", new String[]{email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Cursor getGenaralInfo()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT Name, Id, Sex, Bio From user WHERE Id= 1722198042",null);
        return cursor;
    }

    public int getAllInfoByMail(String email)
    {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT Name, Id, Sex, Bio From user where mail=?",new String[]{email});
       int ID=0;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                ID = cursor.getInt(1);
                cursor.moveToNext();
                break;
            }
        }

        return ID;
    }
    public Cursor getAllInfo()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT Name, Id, Sex, Bio From user",null);
        Log.d("Test",cursor.toString());
        return cursor;
    }


    public Cursor getResearchInfo()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT interest,name,currentlyworkingon From uresearch WHERE Id= 1722198042",null);
        return cursor;
    }



    public Cursor getResearchInfobyID(int id)
    {
        String temp = String.valueOf(id);
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT interest,name,currentlyworkingon From uresearch WHERE Id=?",new String[]{temp});
        return cursor;
    }


    public String get_research_infos(Cursor cursor){
        StringBuilder temp_string_builder= new StringBuilder();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                temp_string_builder.append("Currently Working on:\n"+cursor.getString(2));                // move to the next row.
                cursor.moveToNext();
            }
        }

        return temp_string_builder.toString();

    }


    public String get_research_interests(Cursor cursor){
        StringBuilder temp_string_builder= new StringBuilder();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                temp_string_builder.append("Research Interest:\n"+cursor.getString(0));               // move to the next row.
                cursor.moveToNext();
            }
        }

        return temp_string_builder.toString();

    }

}
