package com.enderstudy.shoppinglist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

@Entity(tableName = "list_item_table")
public class ListItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private Integer id;

    @NonNull
    @ColumnInfo(name="name")
    private String name;

    @Nullable
    @ColumnInfo(name="description")
    private String description;

    @Nullable
    @ColumnInfo(name="price")
    private Double price;

    @NonNull
    @ColumnInfo(name="in_basket")
    private Boolean inBasket;

    public ListItem(@NonNull String name,
                    @Nullable String description,
                    @NonNull Boolean inBasket,
                    @Nullable Double price) {
        this.name = name;
        this.description = description;
        this.inBasket = inBasket;
        this.price = price;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setInBasket(Boolean inBasket) {
        this.inBasket = inBasket;
    }

    public Boolean getInBasket() {
        return this.inBasket;
    }
}
