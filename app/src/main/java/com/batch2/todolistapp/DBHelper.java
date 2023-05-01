package com.batch2.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "todolist.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "todolist";
    private static final String COL_TASK = "task";
    private static final String COL_CATEGORY ="category" ;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TASK + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    public void insertData(ArrayList<String> arr) {
//        SQLiteDatabase sqdb = this.getWritableDatabase();
//        sqdb.delete(TABLE_NAME, null, null);
//
//        for (String s : arr) {
//            ContentValues cv = new ContentValues();
//            cv.put(COL_TASK, s);
//            sqdb.insert(TABLE_NAME, null, cv);
//        }
//        sqdb.close();
//    }

    public void insertData(ArrayList<String> arr) {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        sqdb.delete(TABLE_NAME, null, null);

        for (String s : arr) {
            ContentValues cv = new ContentValues();
            cv.put(COL_TASK, s);
            byte[] category;
            category = new byte[0];
            cv.put("category", category);
            sqdb.insert(TABLE_NAME, null, cv);
        }
        sqdb.close();
    }

//    public ArrayList<String> fetchAll() {
//        ArrayList<String> tasks = new ArrayList<String>();
//        SQLiteDatabase sqdb = this.getWritableDatabase();
//        Cursor c = sqdb.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//
//        if (c.moveToFirst()) {
//            do {
//                tasks.add(c.getString(1));
//            } while (c.moveToNext());
//        }
//        c.close();
//        sqdb.close();
//        return tasks;
//    }

    public List<String> getItemsForCategory(String category) {
        List<String> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_CATEGORY + " = ?", new String[]{category});

            if (cursor.moveToFirst()) {
                do tasks.add(cursor.getString(1));
                while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLiteException e) {
            // Ignore the error and return an empty list
        } finally {
            db.close();
        }

        return tasks;
    }
    public void deleteTask(String s)
    {
        SQLiteDatabase sqdb = this.getWritableDatabase();
        sqdb.execSQL("delete from "+TABLE_NAME+" where task='"+s+"'");
    }


//    public List<String> getItemsForCategory(String category) {
//    }
}

