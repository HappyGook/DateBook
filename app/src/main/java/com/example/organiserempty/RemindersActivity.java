package com.example.organiserempty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RemindersActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    MenuItem event,remind;
    FloatingActionButton fab;
    ROpenHelper rOpenHelper;
    SQLiteDatabase db;
    ArrayList<RObjectItem> maps;

    ArrayList<String> listItem;
    ArrayAdapter adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        event=findViewById(R.id.nav_events);
        remind=findViewById(R.id.nav_reminders);
        fab=findViewById(R.id.fab);
        list=findViewById(R.id.list);
        rOpenHelper=new ROpenHelper(getBaseContext());
        db=rOpenHelper.getReadableDatabase();

        listItem=new ArrayList<>();

        viewData();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewData();
                String selected= (String) list.getItemAtPosition(position);
                String comp=null;
                Cursor csr=db.rawQuery("SELECT completion FROM reminders WHERE header =\"" +selected + "\";",null);
                if(csr!=null) if(csr.moveToFirst()) comp=csr.getString(0);
                Dialog dialog=new Dialog(RemindersActivity.this);
                dialog.setContentView(R.layout.remitemdialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView textHeader = dialog.findViewById(R.id.ItemHeaderR);
                textHeader.setText(selected);
                TextView textComp=dialog.findViewById(R.id.ItemComp);
                textComp.setText(comp);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        viewData();
                    }
                });

                ImageButton addButton=dialog.findViewById(R.id.imgButAdd);
                if (Integer.parseInt(comp)<=0){
                    rOpenHelper.deleteData(selected);
                    dialog.dismiss();
                }
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String clickComp=null;
                        Cursor csr=db.rawQuery("SELECT completion FROM reminders WHERE header =\"" +selected + "\";",null);
                        if(csr!=null) if(csr.moveToFirst()) clickComp=csr.getString(0);
                        if (Integer.parseInt(clickComp)<=0){
                            rOpenHelper.deleteData(selected);
                            dialog.dismiss();
                        }
                        int compInt=Integer.parseInt(clickComp);
                        compInt-=1;
                        ContentValues values=new ContentValues();
                        values.put(ROpenHelper.COLUMN_COMPLETION,String.valueOf(compInt));
                        db.update(ROpenHelper.TABLE_NAME,values,ROpenHelper.COLUMN_HEADER+"=?",new String[]{selected});
                        Cursor csr2=db.rawQuery("SELECT completion FROM reminders WHERE header =\"" +selected + "\";",null);
                        if(csr2!=null)
                            if(csr2.moveToFirst())
                            textComp.setText(csr2.getString(0));
                    }
                });

                ImageButton imageButton=dialog.findViewById(R.id.imgBut);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rOpenHelper.deleteData(selected);
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
                Dialog dialog=new Dialog(RemindersActivity.this);
                dialog.setContentView(R.layout.rembuilderdialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView header=dialog.findViewById(R.id.header);
                TextView comp=dialog.findViewById(R.id.comp);
                Button but=dialog.findViewById(R.id.button);
             dialog.show();
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(comp.getText().toString().equals("")){
                        ContentValues values = new ContentValues();
                        values.put(ROpenHelper.COLUMN_HEADER, header.getText().toString());
                        values.put(ROpenHelper.COLUMN_COMPLETION, "1");
                        db.insert(ROpenHelper.TABLE_NAME,null,values);
                        dialog.dismiss();
                        viewData();
                    }else{
                            ContentValues values = new ContentValues();
                            values.put(ROpenHelper.COLUMN_HEADER, header.getText().toString());
                            values.put(ROpenHelper.COLUMN_COMPLETION, comp.getText().toString());
                            db.insert(ROpenHelper.TABLE_NAME,null,values);
                            dialog.dismiss();
                            viewData();
                        }
                    }
                });
            }
        });
    }

    private void viewData() {
        list.setAdapter(null);
        Cursor cursor=rOpenHelper.viewData();

        maps=new ArrayList<RObjectItem>();

        if(cursor.getCount()==0){
        }else{
            while(cursor.moveToNext()){
                if(Integer.parseInt(cursor.getString(2))<=0){
                    rOpenHelper.deleteData(cursor.getString(1));
                    cursor.moveToNext();
                } else{
                maps.add(new RObjectItem(cursor.getString(1),
                        cursor.getString(2)));
            }}

            adapter=new RMeinAdapter(this,maps);
            list.setAdapter(adapter);
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent=new Intent(RemindersActivity.this,MainActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}