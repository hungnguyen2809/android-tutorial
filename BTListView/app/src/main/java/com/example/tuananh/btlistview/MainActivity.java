package com.example.tuananh.btlistview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ContactDB sqliteContactDB;
    ListView lvContact;
    EditText edtSearch;
    ArrayList<Contact> contacts;
    ContactAdapter adapter;
    int SelectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvContact = findViewById(R.id.listViewContact);
        edtSearch = findViewById(R.id.editTextSearch);
        registerForContextMenu(lvContact);

        sqliteContactDB = new ContactDB(this,"SQLContactDB",null,1);

        contacts = sqliteContactDB.getContactAll();

        adapter = new ContactAdapter(this,R.layout.row_contact,contacts);
        lvContact.setAdapter(adapter);

        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SelectedId = position;
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.Add_Contact){
            startActivityForResult(new Intent(MainActivity.this,AddContactActivity.class),200);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Update_Contact:
                Intent intent = new Intent(MainActivity.this, UpdateContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id",contacts.get(SelectedId).getId());
                bundle.putString("Name",contacts.get(SelectedId).getName());
                bundle.putString("Phone",contacts.get(SelectedId).getPhone());
                intent.putExtra("Data",bundle);
                startActivityForResult(intent,300);
                break;
            case R.id.Delete_Contact:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Delete Contact");
                dialog.setMessage("Bạn có thật sự muốn xóa ko?");
                dialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        contacts.remove(contacts.get(SelectedId));
//                        adapter.notifyDataSetChanged();
                        sqliteContactDB.deleteContact(contacts.get(SelectedId).getId());
                        contacts = sqliteContactDB.getContactAll();
                        adapter.setData(contacts);
                        adapter.notifyDataSetChanged();
                    }
                });
                dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                break;
            case R.id.Sort_Ascending:
                Collections.sort(contacts, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return o1.getId() - o2.getId();
                    }
                });
                adapter.notifyDataSetChanged();
                break;
            case R.id.Sort_Descending:
                Collections.sort(contacts, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact o1, Contact o2) {
                        return o2.getId() - o1.getId();
                    }
                });
                adapter.notifyDataSetChanged();
                break;
            case R.id.call:
                Intent intentCall = new Intent(Intent.ACTION_DIAL);
                intentCall.setData(Uri.parse("tel:" + contacts.get(SelectedId).getPhone()));
                startActivity(intentCall);
                break;

            case  R.id.sendMessage:
                Intent intentSend = new Intent(Intent.ACTION_SENDTO);
                intentSend.putExtra("sms_body", "");
                intentSend.setData(Uri.parse("sms:" + contacts.get(SelectedId).getPhone()));
                startActivity(intentSend);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK && data != null){
            Bundle bundle = data.getBundleExtra("Data");
            int id = bundle.getInt("Id");
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
//            contacts.add(new Contact(id,name,phone));
//              adapter.notifyDataSetChanged();
            sqliteContactDB.addContact(new Contact(id,name,phone));
            contacts = sqliteContactDB.getContactAll();
            adapter.setData(contacts);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
        }
        if(requestCode == 300 && resultCode == RESULT_OK && data != null){
            Bundle bundle = data.getBundleExtra("Data");
            int id = bundle.getInt("Id");
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
//            contacts.get(SelectedId).setId(id);
//            contacts.get(SelectedId).setName(name);
//            contacts.get(SelectedId).setPhone(phone);
            sqliteContactDB.updateContact(id,new Contact(id,name,phone));
            contacts = sqliteContactDB.getContactAll();
            adapter.setData(contacts);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Sửa thành công", Toast.LENGTH_SHORT).show();
        }
    }
}
