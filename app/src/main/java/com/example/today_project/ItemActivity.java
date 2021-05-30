package com.example.today_project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.today_project.database.AppDatabase;
import com.example.today_project.storage.Item;

//класс для отображения данных о конкретном нажатом iteme
public class ItemActivity extends AppCompatActivity {

    private int itemHour;
    private int itemMinute;
    EditText name;
    EditText desc;
    TimePicker timePicker;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        Item item = (Item) getIntent().getSerializableExtra("item");

        name = findViewById(R.id.name);
        name.setText(item.getName());

        desc = findViewById(R.id.desc);
        desc.setText(item.getDescription());

        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        itemHour = (int) (item.getTimeAmount()/3600);
        itemMinute = (int) ((item.getTimeAmount()%3600)/60);

        timePicker.setHour(itemHour);
        timePicker.setMinute(itemMinute);

        Button saveButton = findViewById(R.id.save);
        saveButton.setVisibility(View.GONE);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay != itemHour|minute != itemMinute){
                    saveButton.setVisibility(View.VISIBLE);
                }
                itemHour = hourOfDay;
                itemMinute = minute;
            }
        });

        Button deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(item);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem(item);
            }
        });

    }

    private void saveItem(Item item) {
        item.setName(name.getText().toString());
        item.setDescription(desc.getText().toString());
        Long editSeconds = (long) (itemHour * 3600 + itemMinute * 60);
        item.setTimeAmount(editSeconds);
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.itemDao().update(item);
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void deleteItem(Item item) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.itemDao().delete(item);
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
