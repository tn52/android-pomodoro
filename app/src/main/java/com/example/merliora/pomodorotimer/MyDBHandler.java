package com.example.merliora.pomodorotimer;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

/**
 * Created by admin on 9/30/16.
 */

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pomodoro.db";
    public static final String TABLE_POMODORO = "pomodoro";
    public static final String COLUMN_TIMESTAMP = "timestamp";
//    public static final String COLUMN_COMPLETED = "completed";
//    public static final String COLUMN_ENDED = "ended";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_POMODORO + "(" +
                COLUMN_TIMESTAMP + " INTEGER PRIMARY KEY AUTOINCREMENT " +
//                COLUMN_COMPLETED + " TEXT " +
//                COLUMN_ENDED + " TEXT " +
                ");";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POMODORO);
        onCreate(db);
    }

    //Add a new row to the database
    public void addPomodoro(Pomodoro pomodoro){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMESTAMP, pomodoro.getCreationTime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_POMODORO, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deletePomodoro(String pomodoroID){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_POMODORO + " WHERE " + COLUMN_TIMESTAMP + "=\"" + pomodoroID + "\";");
    }

    //Prints out the database as a string
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_POMODORO + " WHERE 1";

        //Cursor to point to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_TIMESTAMP)) != null){
                dbString += c.getString(c.getColumnIndex(COLUMN_TIMESTAMP));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;

    }

    public int getNumPomodoro(){
        SQLiteDatabase db = getReadableDatabase();
        long cnt = DatabaseUtils.queryNumEntries(db, TABLE_POMODORO);
//        String query = "SELECT Count(*) FROM " + TABLE_POMODORO;
        db.close();
        return (int) cnt;
    }

    public void clearPomodoro(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete * from " + TABLE_POMODORO);
        db.close();
    }

}
