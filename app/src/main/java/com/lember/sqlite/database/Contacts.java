package com.lember.sqlite.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lember.sqlite.model.Code;
import com.lember.sqlite.model.Contact;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contacts implements Serializable {
    private List<Contact> contacts=new ArrayList<>();

    public List<Contact> getContacts(){return contacts;}

    public  Contacts (SQLiteDatabase sqLiteDatabase, Code code){
        try{
            String[] strings= new String[]{code.getCode()};
            Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM contacts where code=?",strings);
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                int id= cursor.getInt(DatabaseHelper.ID_COLUMN);
                String first =cursor.getString(DatabaseHelper.FIRST_COLUMN);
                String last =cursor.getString(DatabaseHelper.LAST_COLUMN);
                String address =cursor.getString(DatabaseHelper.ADDRESS_COLUMN);
                String phone =cursor.getString(DatabaseHelper.PHONE_COLUMN);
                String email =cursor.getString(DatabaseHelper.EMAIL_COLUMN);
                String date =cursor.getString(DatabaseHelper.DATE_COLUMN);
                contacts.add(new Contact(id, first, last, address, code, phone, email, date));
            }

        }catch (SQLException sqlException){
            contacts.clear();
            Log.e("Contacts class error!",String.valueOf(sqlException));
        }
    }

    public  Contacts (SQLiteDatabase sqLiteDatabase, String firstn, String lastn, String addres, String mail){
        try{
           Cursor cursor=sqLiteDatabase.query(DatabaseHelper.CONTACTS_TABLE,DatabaseHelper.CONTACTS_COLUMNS,"first like ? and last like ? and address like ? and email like ?",new String[]{firstn+"%",lastn+"%","%"+addres+"%","%"+mail+"%"},null,null,null);
            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
                int id= cursor.getInt(DatabaseHelper.ID_COLUMN);
                String first =cursor.getString(DatabaseHelper.FIRST_COLUMN);
                String last =cursor.getString(DatabaseHelper.LAST_COLUMN);
                String address =cursor.getString(DatabaseHelper.ADDRESS_COLUMN);
                String code=cursor.getString(DatabaseHelper.CODE_CONTACT_COLUMN);
                String phone =cursor.getString(DatabaseHelper.PHONE_COLUMN);
                String email =cursor.getString(DatabaseHelper.EMAIL_COLUMN);
                String date =cursor.getString(DatabaseHelper.DATE_COLUMN);
                contacts.add(new Contact(id, first, last,address,getCode(sqLiteDatabase,code),phone,email,date));
            }

        }catch (SQLException sqlException){
            contacts.clear();
            Log.e("Contacts class error!",String.valueOf(sqlException));
        }
    }

    private Code getCode(SQLiteDatabase sqLiteDatabase, String code) {
        Cursor cursor=sqLiteDatabase.query(DatabaseHelper.COUNTRY_TABLE,DatabaseHelper.COUNTRY_COLUMNS,"code=?",new String[]{code},null,null,null);
        cursor.moveToFirst();
        return  new Code(cursor.getString(DatabaseHelper.CODE_COLUMN),cursor.getString(DatabaseHelper.COUNTRY_COLUMN));
    }

}
