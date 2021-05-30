package com.example.today_project.storage;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

//этот класс для хранения всех параметров одного события?
public class Store extends AppCompatActivity {
    //и подпиши что это за константа
    private static final Store INST = new Store();

    private final List<Item> items = new ArrayList<>();
    private final List<Integer> checkedItems = new ArrayList<>();

    private Store() {
    }

    public static Store getStore() {
        return INST;
    }

    public void add(Item item) {
        this.items.add(item);
    }
    public void addChecked(int itemIndex) { this.checkedItems.add(itemIndex);}

    public void remove(int index) {this.items.remove(index);}
    public void removeChecked(int itemId) {
        int selectedItem = this.checkedItems.indexOf(itemId);
        this.checkedItems.remove(selectedItem);
    }

    public List<Item> getAll() {
        return this.items;
    }
    public List<Integer> getAllChecked() { return this.checkedItems; }

    public int size() {
        return this.items.size();
    }
    public int sizeChecked() { return this.checkedItems.size();}

    public Item get(int index) {
        return this.items.get(index);
    }
    //для единоразового вытаскивания из массива индексов выбранных элементов
    public Integer getChecked(int index) { return this.checkedItems.get(index);}

    public void change(int index, Item item){ this.items.set(index,item); }
}
