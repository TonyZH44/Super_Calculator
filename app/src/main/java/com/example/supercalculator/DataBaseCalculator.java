package com.example.supercalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class DataBaseCalculator extends SQLiteOpenHelper {


    public static final String CALC_TABLE = "Calc_Table";
    public static final String COLUMN_ANSWER = "Answer";
    public static final String COLUMN_EXPRESSION = "Expression";

    public DataBaseCalculator(@Nullable Context context) {
        super(context, "history.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableInfo = "CREATE TABLE " + CALC_TABLE + " (" +
                COLUMN_EXPRESSION + " TEXT PRIMARY KEY," +
                COLUMN_ANSWER + " REAL); ";

        db.execSQL(createTableInfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(String expression, Double answer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EXPRESSION, expression);
        cv.put(COLUMN_ANSWER, answer);

        db.insert(CALC_TABLE, null, cv);

    }

    public void clearAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(CALC_TABLE, null, null);
    }

    public HashMap<String, Double> getHistory() {
        HashMap<String, Double> returnList = new HashMap<>();

        String queryString = "SELECT * FROM " + CALC_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                String expression = cursor.getString(0);
                Double answer = cursor.getDouble(1);

                returnList.put(expression, answer);

            }while (cursor.moveToNext());

        }else {
            //nothing
        }
        cursor.close();
        db.close();

        return returnList;
    }
}
