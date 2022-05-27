package com.example.organiserempty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "event.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "event";
    public static final String COLUMN_HEADER = "header";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE = "date";


    public OpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME +
                "( _id integer PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_HEADER + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_DATE + " TEXT );";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public Cursor viewData(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);

        return cursor;
    }

    public Integer deleteData(String header){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"header = ?",new String[] {header});
    }

}

