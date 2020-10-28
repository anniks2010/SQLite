package com.lember.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.lember.sqlite.R;
import com.lember.sqlite.database.Contacts;
import com.lember.sqlite.database.DatabaseHelper;

public class SearchActivity extends AppCompatActivity {
    private EditText etFirstS, etLastS, etAddressS, etEmailS;
    private ConstraintLayout constraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        constraintLayout= findViewById(R.id.main_view);
        etFirstS=findViewById(R.id.edFirstSearch);
        etLastS=findViewById(R.id.edLastSearch);
        etAddressS=findViewById(R.id.edAddressSearch);
        etEmailS=findViewById(R.id.edEmailSearch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return  true;
    }

    public void onSearch(View view) {
        String first= etFirstS.getText().toString().trim();
        String last= etLastS.getText().toString().trim();
        String address= etAddressS.getText().toString().trim();
        String email= etEmailS.getText().toString().trim();
        ////ühenduse loomine database-iga kaks järgmist rida
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Contacts contacts=new Contacts(sqLiteDatabase,first,last,address,email);
        if(contacts.getContacts().size()==0){
            Snackbar.make(constraintLayout,getResources().getString(R.string.noContacts),Snackbar.LENGTH_LONG).show();
            sqLiteDatabase.close();
        }else if (contacts.getContacts().size()==1){
            Intent oneIntent = new Intent(getApplicationContext(),ContactActivity.class);
            oneIntent.putExtra("contact",contacts.getContacts().get(0));
            sqLiteDatabase.close();
            startActivity(oneIntent);
            finish();
        }else{
            Intent manyIntent = new Intent(getApplicationContext(),ContactsActivity.class);
            manyIntent.putExtra("contacts",contacts);
            sqLiteDatabase.close();
            startActivity(manyIntent);
            finish();
        }
    }

    public void onClear(View view) {
        etFirstS.setText("");
        etLastS.setText("");
        etAddressS.setText("");
        etEmailS.setText("");
    }
}