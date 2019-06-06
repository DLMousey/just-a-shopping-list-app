package com.enderstudy.shoppinglist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicLong;

@Entity(tableName = "list_item_table")
public class ListItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private Integer id;

    @NonNull
    @ColumnInfo(name="name")
    private String name;

    @Nullable
    @ColumnInfo(name="description")
    private String description;

    @NonNull
    @ColumnInfo(name="in_basket")
    private Boolean inBasket;

    public ListItem(@NonNull String name, @Nullable String description, @NonNull Boolean inBasket) {
        this.name = name;
        this.description = description;
        this.inBasket = inBasket;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setInBasket(Boolean inBasket) {
        this.inBasket = inBasket;
    }

    public Boolean getInBasket() {
        return this.inBasket;
    }
}
