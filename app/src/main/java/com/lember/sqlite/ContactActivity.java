package com.lember.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lember.sqlite.database.DatabaseHelper;
import com.lember.sqlite.model.Code;
import com.lember.sqlite.model.Contact;

import java.util.Calendar;
import java.util.Locale;

public class ContactActivity extends AppCompatActivity {

    private Code code=null;
    private Contact contact=null;
    private EditText etFirst, etLast, etAddress, etPhone, etEmail, etdate, etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ///adding back arrow button on actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ///initialising varaibles
        etFirst=findViewById(R.id.edFirstSearch);
        etLast=findViewById(R.id.edLastSearch);
        etAddress=findViewById(R.id.edAddressSearch);
        etPhone=findViewById(R.id.edPhoneContact);
        etEmail=findViewById(R.id.edEmailSearch);
        etdate=findViewById(R.id.edDateContact);
        etCode=findViewById(R.id.edCodeContact);
        ///want the focus to be on first field when activity opens
        etFirst.requestFocus();

        ///getting code from mainactivity for contarct
        Intent intentCode= getIntent();
        Object object=intentCode.getSerializableExtra("code");
        if (object !=null){
            code = (Code) object;
            Calendar calendar=Calendar.getInstance();
            etdate.setText(String.format(Locale.getDefault(),"%02d-%02d-%d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR)));
        }else{
            contact=(Contact) intentCode.getSerializableExtra("contact");
            if(contact!=null){
                etFirst.setText(contact.getFirst());
                etLast.setText(contact.getLast());
                etAddress.setText(contact.getAddress());
                etPhone.setText(contact.getPhone());
                etEmail.setText(contact.getEmail());
                etdate.setText(contact.getDate());
                code=contact.getCode();

            }
        }
        etCode.setText(code.toString());
    }
    /////kui tagasinuppu vajutada, siis navigatorup kontrollib, et kõik tegevused on lõppenud.
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onAdd(View view) {
        String first=etFirst.getText().toString().trim();
        if (first.length()>0){
            String last = etLast.getText().toString().trim();
            String address = etAddress.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email=etEmail.getText().toString().trim();
            String date = etdate.getText().toString().trim();

            DatabaseHelper databaseHelper=new DatabaseHelper(this);
            SQLiteDatabase sqLiteDatabase=databaseHelper.getWritableDatabase();

            ContentValues values=new ContentValues();
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.FIRST_COLUMN],first);
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.LAST_COLUMN],last);
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.ADDRESS_COLUMN],address);
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.CODE_CONTACT_COLUMN],code.getCode());
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.PHONE_COLUMN],phone);
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.EMAIL_COLUMN],email);


            if (contact==null){
                values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.DATE_COLUMN],date);
                sqLiteDatabase.insert(DatabaseHelper.CONTACTS_TABLE,null,values);
            }else{
                Calendar calendar=Calendar.getInstance();
                etdate.setText(String.format(Locale.getDefault(),"%02d-%02d-%d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.YEAR)));
                String[] params={""+contact.getId()};
                sqLiteDatabase.update(DatabaseHelper.CONTACTS_TABLE,values,"id=?",params);
            }
            sqLiteDatabase.close(); ////paneb andmebaasi kinni
            onSupportNavigateUp();
        }else{
            etFirst.setError(getResources().getString(R.string.inputEntry)); /// This field needs to be filled! ->string value
            etFirst.requestFocus();
        }

    }

    public void onDelete(View view) {
        if(contact!=null){
            DatabaseHelper databaseHelper=new DatabaseHelper(this);
            SQLiteDatabase sqLiteDatabase=databaseHelper.getWritableDatabase();

            String[] params={""+contact.getId()};
            sqLiteDatabase.delete(DatabaseHelper.CONTACTS_TABLE,"id=?",params);
            sqLiteDatabase.close();
            onSupportNavigateUp();
        }

    }


}