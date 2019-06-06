package com.enderstudy.shoppinglist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "list_item_table")
public class ListItem {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="name")
    private String name;

    public ListItem(@NonNull String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
