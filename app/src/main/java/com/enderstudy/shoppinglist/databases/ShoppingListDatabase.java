package com.enderstudy.shoppinglist.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.enderstudy.shoppinglist.dao.ListItemDao;
import com.enderstudy.shoppinglist.entities.ListItem;

@Database(entities = {ListItem.class}, version = 4, exportSchema = false)
public abstract class ShoppingListDatabase extends RoomDatabase {

    private static final String TAG = ShoppingListDatabase.class.getSimpleName();

    public abstract ListItemDao listItemDao();

    private static ShoppingListDatabase INSTANCE;

    public static ShoppingListDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { // If no instance is available
            synchronized (ShoppingListDatabase.class) { // Synchronise with other threads, in case one is living on another thread
                if (INSTANCE == null) { // If we've still got no instance
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), // Create an instance
                            ShoppingListDatabase.class, "list_item_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
