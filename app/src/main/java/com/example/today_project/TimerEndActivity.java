package com.example.today_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.today_project.storage.Store;

public class TimerEndActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_timer);

        if (Store.getStore().sizeChecked() <= 0) {
            Button nextTimer = findViewById(R.id.go_to_next_task_end_timer);
            nextTimer.setVisibility(View.GONE);
        }
    }

    public void returnMainActivity(View view){
        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void nextTimerStart(View view){
        Intent intent = new Intent(this.getApplicationContext(), TimerActivity.class);
        startActivity(intent);
        finish();
    }
}
