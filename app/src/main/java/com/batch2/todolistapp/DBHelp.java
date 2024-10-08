package com.batch2.todolistapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelp extends SQLiteOpenHelper {
    private static final String DB_NAME = "todolist.db";
    private static String TABLE_NAME;
    private static String[] tb= {"default_list","personal","shopping","wishlist","work","finished"};

    public DBHelp(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        for(String a :tb){
            String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + a + " (tasks TEXT)";
            db.execSQL(CREATE_TABLE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String a :tb) {
            db.execSQL("DROP TABLE IF EXISTS " + a);
            onCreate(db);
        }
    }

    public void insertData(String s,String tname)
    {
        TABLE_NAME = tname;
        SQLiteDatabase sqdb = this.getWritableDatabase();
        sqdb.execSQL("INSERT INTO "+TABLE_NAME+" VALUES('"+s+"')");
        sqdb.close();
    }

    public ArrayList<String> fetchAll(String tname)
    {
        TABLE_NAME = tname;
        ArrayList<String> tasks = new ArrayList<String>();
        SQLiteDatabase sqdb = this.getWritableDatabase();
        onCreate(sqdb);
        Cursor c = sqdb.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                tasks.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        sqdb.close();
        return tasks;
    }


    public void deleteTask(String s,String tname)
    {
        TABLE_NAME = tname;
        SQLiteDatabase sqdb = this.getWritableDatabase();
        sqdb.execSQL("delete from "+TABLE_NAME+" where tasks='"+s+"'");
        sqdb.close();
    }
}

