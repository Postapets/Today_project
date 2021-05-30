package com.example.today_project.storage;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//этот класс для хранения всех параметров одного события?
public class Store extends AppCompatActivity {
    //и подпиши что это за константа
    private static final Store INST = new Store();

    private final List<Integer> checkedItems = new ArrayList<>();

    private Store() {
    }

    public static Store getStore() {
        return INST;
    }

    public void addChecked(int itemIndex) { this.checkedItems.add(itemIndex);}

    public void removeByIndex(int index){ this.checkedItems.remove(index); }

    public void removeChecked(int itemId) { this.checkedItems.remove((Integer) itemId); }

    public void removeAllChecked(){  this.checkedItems.clear();  }

    public List<Integer> getAllChecked() { return this.checkedItems; }

    public int sizeChecked() { return this.checkedItems.size();}

    //для единоразового вытаскивания из массива индексов выбранных элементов
    public Integer getChecked(int index) { return this.checkedItems.get(index);}

}
