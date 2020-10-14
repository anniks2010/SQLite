package com.lember.sqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lember.sqlite.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    ////declaring database file
    private static final String FILENAME="contactsData.db";
    ///database version
    private static final int DB_VERSION=1;
    ///country table staff
    public static final String COUNTRY_TABLE="countries";
    public static final int CODE_COLUMN=0;
    public static final int COUNTRY_COLUMN=1;
    public static final String[] COUNTRY_COLUMNS= new String[]{"code","country"};

    ///contracts table stuff
    public static final String CONTACTS_TABLE="contacts";
    public static final int ID_COLUMN=0;
    public static final int FIRST_COLUMN=1;
    public static final int LAST_COLUMN=2;
    public static final int ADDRESS_COLUMN=3;
    public static final int CODE_CONTACT_COLUMN=4;
    public static final int PHONE_COLUMN=5;
    public static final int EMAIL_COLUMN=6;
    public static final int DATE_COLUMN=7;
    public static final String [] CONTACTS_COLUMNS=new String[]{"id","first","last","address","code","phone","email","date"};

    //schema
    private static final String COUNTRY_SCHEMA = "CREATE TABLE countries (code char PRIMARY KEY, country varchar NOT NULL)";
    private static final String CONTACTS_SCHEMA="CREATE TABLE contacts (id integer PRIMARY KEY AUTOINCREMENT, first varchar NOT NULL, last varchar, address varchar, code char NOT NULL, phone varchar, email varchar, date varchar, FOREIGN KEY (code) REFERENCES countries(code))";

    private Context context;



    public DatabaseHelper(@Nullable Context context) {
        super(context, FILENAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(COUNTRY_SCHEMA);
        sqLiteDatabase.execSQL(CONTACTS_SCHEMA);
        sqLiteDatabase.execSQL(insertCodes());

    }

    private String insertCodes() {
        InputStream inputStream = context.getResources().openRawResource(R.raw.codes);
        StringBuilder builder=new StringBuilder("INSERT INTO countries (code,country) VALUES");
        try (BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream))){
            addRows(builder,reader.readLine());
            for(String line =reader.readLine(); line !=null; line= reader.readLine()){
                if(line.length()>0){
                    builder.append(",");
                    addRows(builder,line);
                }
            }

        }catch (IOException ex){
            Log.e("DatabaseHelper IO error: ",String.valueOf(ex));

        }
        return builder.toString();
    }

    private void addRows(StringBuilder builder, String line) {
        String[] items = line.split(",");
        builder.append("('");
        builder.append(items[0]);
        builder.append("','");
        builder.append(items[1]);
        builder.append("')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
