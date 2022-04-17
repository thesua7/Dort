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

    public void insertData_to_Research(String present,String past,String id,String topics){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("currentlyworkingon",present);
        values.put("name",past);
        values.put("interest",topics);
        values.put("id",String.valueOf(id));


        long result = db.insert("uresearch",null,values);

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

    public Boolean insertData_Match_Status(String CurrentId,String MatchId,String Matchstatus){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("CurrentId",CurrentId);
        values.put("MatchId",MatchId);
        values.put("Matchstatus",Matchstatus);


        long result = db.insert("matchStat",null,values);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean insertData_Report(String reported_by,String reported_to,String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("reportedBy",reported_by);
        values.put("reportedTo  ",reported_to);
        values.put("reportText",msg);


        long result = db.insert("reportusers",null,values);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean CheckMatch(String currentId,String matchId,String tokken){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from matchStat where CurrentId=? and MatchId=? and Matchstatus=?", new String[]{currentId,matchId,tokken});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public Boolean checkemail_(String email) {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where mail = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }


    public Boolean checkId_(String id) {

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where Id = ?", new String[]{id});
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

    public  Boolean CheckResearchInfo(String  id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from uresearch where Id=? ",new String[]{id});
        if(cursor.getCount()>0){
            String whereClause = "Id=?";
            String whereArgs[] = {id};
            db.delete("uresearch", whereClause, whereArgs);
            return true;
        }
        else {
            return false;
        }
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

    public Cursor getAllInfobyId(String id)
    {

        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT Name, Id, Sex, Bio From user WHERE Id=?",new String[]{id});
        return cursor;
    }

    public Cursor getAllInfoExceptUserbyId(String id)
    {

        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT Name, Id, Sex, Bio From user WHERE Id!=?",new String[]{id});
        return cursor;
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

   public Cursor getAllId(String id){
       SQLiteDatabase db= this.getReadableDatabase();
       Cursor cursor= db.rawQuery(" SELECT Id From user where Id !=?",new String[]{id});
        return cursor;
   }

    public Cursor getNameById(String id){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor= db.rawQuery(" SELECT Id From user where Id =?",new String[]{id});
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
