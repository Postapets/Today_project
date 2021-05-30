package com.example.today_project.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.today_project.storage.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM item")
    List<Item> getAllItems();

    @Query("SELECT * FROM item WHERE id = :id")
    Item getById(long id);

    @Insert
    void insertAll(Item... items);
//
//    @Insert
//    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);
}
