package com.example.today_project;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.today_project.storage.Store;

import java.util.ArrayList;
import java.util.Locale;

//класс активности таймера, должен быть рабочий
public class TimerActivity extends AppCompatActivity {

    public TextView timerText;
    public ProgressBar progressBar;
    public Button stopButton;
    public Button nextItemTimer;
    private Long itemTimeSum = 0L;
    private boolean itemsInQueue = false;
    public Long currentTime;
    ArrayList<String> itemNames;
    ArrayList<Long> itemTimes;
    ItemCountDownTimer newTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerText = findViewById(R.id.timerText);
        progressBar = findViewById(R.id.timerBar);
        nextItemTimer = findViewById(R.id.nextItemTimer);
        stopButton = findViewById(R.id.end_timer);

        //вытаскиваем аргументы из MainActivity
        Bundle arguments = getIntent().getExtras();

        itemNames = (ArrayList<String>) arguments.get("itemsNames");
        itemTimes = (ArrayList<Long>) arguments.get("itemsTime");

        for (int i=0;i< Store.getStore().sizeChecked();i++) {
            Toast.makeText(this, String.format("checkedItems id: %d",Store.getStore().getChecked(i)), Toast.LENGTH_SHORT).show();
        }
        newTimer= new ItemCountDownTimer(itemTimes.get(0), 1000);
        newTimer.start();

        //startTimer(itemNames.get(0), itemTimes.get(0));
        //setNewTimer();
        //создаем общую переменную со временем для рассчитывания progressBar
//        for(Long itemTime: itemTimes){
//            itemTimeSum += itemTime;
//        }

        //рассчет коэффициента для прогрессбара
        //double progressBarCoef = 100 / (double) itemTimeSum * 1.0;
        //itemsInQueue = isItemsLeft(itemNames);
//        currentTime = itemTimes.get(0);

//        newTimer= new ItemCountDownTimer(10000, 1000);
//        ItemCountDownTimer.start();


//        CountDownTimer newTimer = new CountDownTimer(currentTime, 1000){
//            Long anotherItemsTime = (itemTimeSum - currentTime) / 1000;
//
//            @Override
//            public void onTick(long l) {
//                l /= 1000;
//
//                Toast.makeText(TimerActivity.this,
//                        String.format(Locale.getDefault(),"l %d", l),
//                        Toast.LENGTH_SHORT).show();
//                int currentMinute = (int) (l / 60);
//                int currentSecond = (int) (l % 60);
//                timerText.setText(String.format(Locale.getDefault(), "%d:%d", currentMinute,currentSecond));
////                timerText.setText(String.format(Locale.getDefault(), "%s\n" +
////                        "%d:%d", itemNames.get(0), currentMinute, currentSecond));
//                Toast.makeText(TimerActivity.this, String.format(Locale.getDefault(),"timerBeforeBar %d", l), Toast.LENGTH_SHORT).show();
//                progressBar.setProgress((int)((anotherItemsTime + l)*progressBarCoef));
//            }
//
//            @Override
//            public void onFinish() {
//                //afterTimerFinish(itemNames, itemTimes);
//                itemNames.remove(0);
//                itemTimes.remove(0);
//
//                if (isItemsLeft(itemNames)) {
//                    this.start();
//                }
//            }
//        }.start();

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer(newTimer);
            }
        });

        nextItemTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer(newTimer);
                setNewTimer();
            }
        });

    }

    private void removeUsedItem() {
        itemNames.remove(0);
        itemTimes.remove(0);
    }

    private boolean checkItemsLeft() {
        return itemNames.size()>0;
    }

    private void setNewTimer(){
        if (checkItemsLeft()){
            String currentName = itemNames.get(0);
            currentTime = itemTimes.get(0);
            removeUsedItem();
            startTimer(currentName, currentTime);
        }
        if (itemNames.size() < 1){
            //прячет кнопку следующая активность, если их больше нет
            nextItemTimer.setVisibility(View.GONE);
        }
    }

    private void startTimer(String currentName, long currentTime) {
        Toast.makeText(this, "currentTime "+currentTime, Toast.LENGTH_SHORT).show();
        newTimer= new ItemCountDownTimer(currentTime, 1000);
        newTimer.start();
    }

    //при нажатии на кнопку остановки таймера
    private void stopTimer(CountDownTimer timer){
        timer.cancel();
    }

    //класс таймера
    public class ItemCountDownTimer extends CountDownTimer {
        double progressBarCoef = progressBar.getMax() / (double) itemTimeSum;

        public ItemCountDownTimer(long timeLeftInMillies, long countDownInterval) {
            super(timeLeftInMillies, countDownInterval);
        }

        @Override
        public void onTick(long timeLeft) {
            Toast.makeText(TimerActivity.this, String.format("timeleft %d",timeLeft), Toast.LENGTH_SHORT).show();
            timeLeft /= 1000;
            int currentMinute = (int) (timeLeft / 60);
            int currentSecond = (int) (timeLeft % 60);
            timerText.setText(String.format(Locale.getDefault(), "%d:%d", currentMinute,currentSecond));
            int progress = (int) (timeLeft * progressBarCoef);

            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            if (itemNames.size() > 0){
                //может вызвать ошибку
                setNewTimer();
            }

        }
    }

}
