package com.lember.sqlite.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lember.sqlite.model.Code;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Codes implements Serializable {
    private List<Code> codes=new ArrayList<>();
    public List<Code> getCodes(){return codes;}

    public Codes(SQLiteDatabase sqLiteDatabase){
        try{
            Cursor cursor=sqLiteDatabase.query(DatabaseHelper.COUNTRY_TABLE,DatabaseHelper.COUNTRY_COLUMNS,null,null,null,null,null);

            for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                String code= cursor.getString(DatabaseHelper.CODE_COLUMN);
                String country = cursor.getString(DatabaseHelper.COUNTRY_COLUMN);
                codes.add(new Code(code,country));
            }

        }catch (SQLException sqlException){
            codes.clear();
            Log.e("Codes class error!",String.valueOf(sqlException));

        }
    }
}
