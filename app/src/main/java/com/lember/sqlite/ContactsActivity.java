package com.lember.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lember.sqlite.database.Contacts;
import com.lember.sqlite.model.Contact;

public class ContactsActivity extends AppCompatActivity {

    private ArrayAdapter<Contact> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent contactsIntent=getIntent();
        Contacts contacts=(Contacts)contactsIntent.getSerializableExtra("contacts");
        ListView view=(ListView) findViewById(R.id.listContacts);

        if(contacts!=null){
            view.setAdapter(adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,contacts.getContacts()));
            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    showContact(adapter.getItem(position));
                }
            });
        }
    }

    private void showContact(Contact item) {
        Intent intent=new Intent(getApplicationContext(),ContactActivity.class);
        intent.putExtra("contact",item);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}