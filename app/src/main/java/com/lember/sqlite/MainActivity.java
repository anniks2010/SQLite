package com.lember.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.lember.sqlite.database.Codes;
import com.lember.sqlite.database.Contacts;
import com.lember.sqlite.database.DatabaseHelper;
import com.lember.sqlite.model.Code;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Contacts contacts;
    private SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    private ListView list;
    private EditText etCode, etCountry;
    private Codes codes;
    private ConstraintLayout constraintLayout;
    private ArrayAdapter<Code> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout=findViewById(R.id.mainView);
        list=findViewById(R.id.listViewMain);
        etCode=findViewById(R.id.etCodeMain);
        etCountry=findViewById(R.id.etCountryMain);
        databaseHelper=new DatabaseHelper(this);
        sqLiteDatabase=databaseHelper.getReadableDatabase();
        codes=new Codes(sqLiteDatabase);

        registerForContextMenu(list);
        //method for displaying codes.txt data in ListView
        displayCountries("","");
    }

    private void displayCountries(String code, String country) {
        list.setAdapter(adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,getCodes(code,country)));
    }
    ///method to get and put data.
    private List<Code> getCodes(String code, String country){
        List<Code> codeList=new ArrayList<>();
        for(Code cd: codes.getCodes())
            if(cd.getCode().startsWith(code)&& cd.getCountry().contains(country)) codeList.add(cd);
            return codeList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.searchMenu){
            startActivity(new Intent(getApplicationContext(),SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Code code=adapter.getItem(menuInfo.position);
        switch (item.getItemId()){
            case R.id.showContacts:
                showContact(code);
                return true;
            case R.id.createContact:
                createContact(code);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showContact(Code code) {
        contacts=new Contacts(sqLiteDatabase,code);
        if(contacts.getContacts().size()==0){
            Snackbar.make(constraintLayout,"no contacts found",Snackbar.LENGTH_LONG).show();
        }else if (contacts.getContacts().size()==1){
            Intent oneContact=new Intent(getApplicationContext(),ContactsActivity.class);
            oneContact.putExtra("contact",contacts.getContacts().get(0));
            startActivity(oneContact);
        }else{
            Intent manyContacts = new Intent(getApplicationContext(),ContactsActivity.class);
            manyContacts.putExtra("contacts",contacts);
            startActivity(manyContacts);
        }
    }

    private void createContact(Code code) {
        Intent create = new Intent(getApplicationContext(),ContactActivity.class);
        create.putExtra("code",code);
        startActivity(create);
    }

    public void onSearch(View view) {
    displayCountries(etCode.getText().toString(),etCountry.getText().toString());
    }

    public void onClear(View view) {
        etCode.setText("");
        etCountry.setText("");
    }
}