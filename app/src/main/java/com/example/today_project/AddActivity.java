package com.example.today_project;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.today_project.database.AppDatabase;
import com.example.today_project.storage.Item;

import org.jetbrains.annotations.Nullable;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private int itemHour;
    private int itemMinute;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_add_item);

        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true); // 24H Mode.
        timePicker.setHour(0);
        timePicker.setMinute(30);
        itemHour = 0;
        itemMinute = 30;

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                itemHour = hourOfDay;
                itemMinute = minute;
            }
        });

    }

    public void save(View view) {
        EditText edit = this.findViewById(R.id.name);
        EditText editDesc = this.findViewById(R.id.desc);
        Long editSeconds = (long) (itemHour * 3600 + itemMinute * 60);
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.itemDao().insertAll(new Item(edit.getText().toString(),
                editDesc.getText().toString() ,
                editSeconds,
                Calendar.getInstance()));

        finish();
    }
}
