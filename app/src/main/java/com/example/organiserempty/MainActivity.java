package com.example.organiserempty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    MenuItem event,remind;
    ImageButton tutBut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        event=findViewById(R.id.nav_events);
        remind=findViewById(R.id.nav_reminders);
        tutBut=findViewById(R.id.tutBut);

        tutBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.tutdialog);
                dialog.show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        if(item.getItemId()==R.id.nav_events){
            Intent intentEv=new Intent(MainActivity.this,EventActivity.class);
            startActivity(intentEv);
        }
        else if(item.getItemId()==R.id.nav_reminders){
            Intent intentRem=new Intent(MainActivity.this,RemindersActivity.class);
            startActivity(intentRem);
        }

        return super.onOptionsItemSelected(item);
    }
}