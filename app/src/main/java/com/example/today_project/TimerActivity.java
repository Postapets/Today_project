package com.example.today_project;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.today_project.database.AppDatabase;
import com.example.today_project.storage.Item;
import com.example.today_project.storage.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//класс активности таймера, должен быть рабочий
public class TimerActivity extends AppCompatActivity {

    public TextView timerText;
    public ProgressBar progressBar;
    public Button stopButton;
    private CountDownTimer myTimer;
    private Item item = null;
    private String itemName = null;
    private long itemTime = 0L;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerText = findViewById(R.id.timerText);
        progressBar = findViewById(R.id.timerBar);
        stopButton = findViewById(R.id.end_timer);

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        item = db.itemDao().getById(Store.getStore().getChecked(0));
        itemName = item.getName();
        itemTime = item.getTimeAmount() * 1000;

        if (isItemsLeft()){
            removeUsedItem();
            startTimer();
        }

    }

    private void nextItemTimer(){
        Intent intent = new Intent(this.getApplicationContext(), TimerInterruptActivity.class);
        startActivity(intent);
        finish();
    }

    private void endItemTimer(){
        Intent intent = new Intent(this.getApplicationContext(), TimerEndActivity.class);
        startActivity(intent);
        finish();
    }

    private void removeUsedItem() {
        Store.getStore().removeByIndex(0);
    }

    private boolean isItemsLeft() {
        return Store.getStore().sizeChecked()>0;
    }


    private void startTimer() {
        myTimer = new CountDownTimer(itemTime, 1000) {
            double progressBarCoef = progressBar.getMax() / ((double) itemTime / 1000);
            @Override
            public void onTick(long millisUntilFinished) {
                itemTime = millisUntilFinished;

                updateCountDownTimer();
                int progress = (int) ((millisUntilFinished / 1000) * progressBarCoef);

                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                endItemTimer();
                finish();
            }
        }.start();
    }

    private void updateCountDownTimer() {
        int hours = (int) ((itemTime / 1000)/ 3600);
        int minutes = (int) (((itemTime / 1000) / 60) % 60);
        int seconds = (int) ((itemTime / 1000) % 60);
        String timeString = "";

        if (hours > 0){
            timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0){
            timeString = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        } else{
            timeString = String.format(Locale.getDefault(), "%02d", seconds);
        }

        timerText.setText(timeString);
    }

    //при нажатии на кнопку остановки таймера
    public void stopTimer(View view){
        myTimer.cancel();
        nextItemTimer();
        finish();
    }

}
