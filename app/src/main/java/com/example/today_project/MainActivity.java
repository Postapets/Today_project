package com.example.today_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.today_project.database.AppDatabase;
import com.example.today_project.storage.Item;
import com.example.today_project.storage.Store;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(this);
        recycler.setAdapter(adapter);

        loadItemList();
    }

    @Override
    public void onResume(){
        super.onResume();
        loadItemList();

    }

    private void loadItemList() {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<Item> itemList = db.itemDao().getAllItems();
        adapter.setItemList(itemList);
    }

    //используется в main_activity.xml при нажатии кнопки добавить
    public void add(View view) {
        Intent intent = new Intent(this.getApplicationContext(), AddActivity.class);
        startActivity(intent);
    }

    public void delete(View view){
        if (Store.getStore().sizeChecked() > 0){
            List<Integer> checkedItems = Store.getStore().getAllChecked();
            AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

            for (int i=0;i<checkedItems.size();i++){
                Item item = db.itemDao().getById(checkedItems.get(i));
                db.itemDao().delete(item);
            }

            Store.getStore().removeAllChecked();
            loadItemList();
        } else{
            Toast.makeText(this, "Выберите элементы для удаления!", Toast.LENGTH_SHORT).show();
        }
    }

    //используется в main_activity.xml при нажатии кнопки начать
    public void startTimer(View view){
        if (Store.getStore().sizeChecked() > 0) {
            Intent intent = new Intent(this.getApplicationContext(), TimerActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(this, "Задачи не отмечены", Toast.LENGTH_SHORT).show();
        }
    }

}
