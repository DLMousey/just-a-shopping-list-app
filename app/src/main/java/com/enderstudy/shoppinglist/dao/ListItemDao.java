package com.enderstudy.shoppinglist.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.enderstudy.shoppinglist.entities.ListItem;

import java.util.List;

@Dao
public interface ListItemDao {

    @Insert
    void insert(ListItem listItem);

    @Update
    void update(ListItem listItem);

    @Query("DELETE FROM list_item_table")
    void deleteAll();

    @Delete
    void delete(ListItem listItem);

    @Query("SELECT * FROM list_item_table ORDER BY name ASC")
    LiveData<List<ListItem>> getAllListItems();

    @Query("UPDATE list_item_table SET in_basket = 1 WHERE in_basket = 0")
    void addAllToCart();
}
