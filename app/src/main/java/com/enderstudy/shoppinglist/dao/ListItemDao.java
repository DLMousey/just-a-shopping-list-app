package com.enderstudy.shoppinglist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
}
