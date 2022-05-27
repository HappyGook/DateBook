package com.example.organiserempty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EventBuilderActivity extends AppCompatActivity {
    OpenHelper openHelper;
    EditText name,desc;
    Button button;
    TimePicker time;
    SQLiteDatabase db;
    CalendarView calendarView;

    String timet,selectedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_builder);

        openHelper=new OpenHelper(getBaseContext());
        db=openHelper.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        name=findViewById(R.id.header);
        desc=findViewById(R.id.desc);
        time=findViewById(R.id.time);
        button=findViewById(R.id.button);
        calendarView=findViewById(R.id.calendarView);

        Calendar now = Calendar.getInstance();

        time.setIs24HourView(true);

        selectedDate=sdf.format(calendarView.getDate());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month++;
                if(month<10){
                    selectedDate=String.valueOf(dayOfMonth)+"/0"+String.valueOf(month)+"/"+String.valueOf(year);
                }
                else{
                    selectedDate=String.valueOf(dayOfMonth)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                }
            }
        });
        if(time.getMinute()<10) {
            timet = String.valueOf(time.getHour()) + ":0" + String.valueOf(time.getMinute());
        }
        else{
            timet=String.valueOf(time.getHour())+":"+String.valueOf(time.getMinute());
        }

        time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(minute<10) {
                    timet = String.valueOf(hourOfDay) + ":0" + String.valueOf(minute);
                } else{
                    timet = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.equals(null)||desc.equals(null)){
                    Toast.makeText(getApplicationContext(),"Пожалуйста, введите полные данные",Toast.LENGTH_LONG).show();
                }
                else{
                    ContentValues values = new ContentValues();
                    values.put(OpenHelper.COLUMN_HEADER, name.getText().toString());
                    values.put(OpenHelper.COLUMN_DESCRIPTION, desc.getText().toString());
                    values.put(OpenHelper.COLUMN_TIME, timet);
                    values.put(OpenHelper.COLUMN_DATE, selectedDate);
                    db.insert(OpenHelper.TABLE_NAME, null, values);

                    Intent intent = new Intent(EventBuilderActivity.this,EventActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


}