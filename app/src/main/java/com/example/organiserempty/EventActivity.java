package com.example.organiserempty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.spec.ECField;
import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    MenuItem event,remind;
    FloatingActionButton fab;
    FragmentManager fm;
    OpenHelper openHelper;
    SQLiteDatabase db;
    ArrayList<ObjectItem> maps;

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        event=findViewById(R.id.nav_events);
        remind=findViewById(R.id.nav_reminders);
        fab=findViewById(R.id.fab);
        list=findViewById(R.id.list);
        openHelper=new OpenHelper(getBaseContext());
        db=openHelper.getReadableDatabase();



        listItem=new ArrayList<>();

        viewData();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected= (String) list.getItemAtPosition(position);

                String desc = null,time = null,date=null;
                Cursor csr=db.rawQuery("SELECT description FROM event WHERE header =\"" +selected + "\";",null);
                if(csr!=null)
                    if(csr.moveToFirst())
                        desc=csr.getString(0);
                Cursor csr2=db.rawQuery("SELECT time FROM event WHERE header = \"" +selected + "\";",null);
                if(csr2!=null)
                    if(csr2.moveToFirst())
                        time=csr2.getString(0);
                Cursor csr3=db.rawQuery("SELECT date FROM event WHERE header = \"" +selected + "\";",null);
                if(csr3!=null)
                    if(csr3.moveToFirst())
                        date=csr3.getString(0);
                Dialog dialog = new Dialog(EventActivity.this);
                dialog.setContentView(R.layout.itemdialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView textHeader = dialog.findViewById(R.id.ItemHeader);
                textHeader.setText(selected);
                TextView textDesc = dialog.findViewById(R.id.ItemDesc);
                textDesc.setText(desc);
                TextView textTime = dialog.findViewById(R.id.ItemTime);
                textTime.setText(time);
                TextView textDate = dialog.findViewById(R.id.ItemDate);
                textDate.setText(date);


                ImageButton imageButton=dialog.findViewById(R.id.imgBut);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openHelper.deleteData(selected);
                        maps.clear();
                        viewData();
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });



        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EventActivity.this,EventBuilderActivity.class);
                startActivity(intent);

            }
        });
    }

    private void viewData() {
        list.setAdapter(null);
        Cursor cursor=openHelper.viewData();

        maps = new ArrayList<ObjectItem>();

        if(cursor.getCount()==0){
        } else{
            while (cursor.moveToNext()){
                maps.add(new ObjectItem(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),cursor.getString(4)));
            }

            adapter=new MeinAdapter(this, maps);
            list.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intentMain=new Intent(EventActivity.this,MainActivity.class);
        startActivity(intentMain);

        return super.onOptionsItemSelected(item);
    }
}