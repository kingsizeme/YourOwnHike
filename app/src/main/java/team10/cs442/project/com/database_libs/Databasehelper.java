package team10.cs442.project.com.database_libs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yukik_000 on 11/7/2015.
 */
public class Databasehelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "LogMenu.db";
    public static final String TABLE_NAME = "Log_Data";
    public static final String number = "Log_Id";
    public static final String ITEM_NAME = "Log_Name";



    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + number
            + " integer primary key autoincrement, " + ITEM_NAME
            + " text not null )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(ITEM_NAME,name);
        long result = db.insert(TABLE_NAME,null,val);
        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor viewAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur=db.rawQuery("select * from " + TABLE_NAME,null);
        return cur;
    }
    public Integer deleteRaw(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"Log_Id = "+id,null);
    }
    public Cursor getSpecificData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where Log_Id = " + id, null);
        return res;
    }

    public Integer updateItem(String id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(number,id);
        val.put(ITEM_NAME,name);
        return db.update(TABLE_NAME, val, "Log_Id = ?", new String[]{id});
    }

}
