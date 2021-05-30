package com.example.today_project.storage;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Entity
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "timeAmount")
    private Long timeAmount;//в секундах

    @ColumnInfo(name = "created")
    private Calendar created;

//    @ColumnInfo(name = "done")
//    private boolean done;

    public Item() {

    }

    public Item(String name, String desc, Calendar created) {
        this.name = name;
        this.description = desc;
        this.created = created;
    }

    public Item(String name, String desc, Long timeAmount,Calendar created) {
        this.name = name;
        this.description = desc;
        this.timeAmount = timeAmount;
        this.created = created;
    }

    public int getId() { return id; }
    //ОПАСНО
    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() { return description; }

    public void setDescription(String desc) { this.description = desc; }

    public Long getTimeAmount() { return timeAmount; }

    public void setTimeAmount(Long timeAmount) { this.timeAmount = timeAmount; }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

//    public boolean isDone() {
//        return done;
//    }
//
//    public void setDone(boolean done) {
//        this.done = done;
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
