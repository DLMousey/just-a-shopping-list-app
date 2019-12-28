package com.enderstudy.shoppinglist;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {ListItem.class}, version = 4, exportSchema = false)
public abstract class ShoppingListDatabase extends RoomDatabase {

    public abstract ListItemDao listItemDao();

    private static ShoppingListDatabase INSTANCE;

    public static ShoppingListDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { // If no instance is available
            synchronized (ShoppingListDatabase.class) { // Synchronise with other threads, in case one is living on another thread
                if (INSTANCE == null) { // If we've still got no instance
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), // Create an instance
                            ShoppingListDatabase.class, "list_item_database")
                            .addCallback(roomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    // Callback to populate the database while in development
    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ListItemDao dao;
        String[] names = {"Bacon", "Chicken", "Milk"};
        String[] descriptions = {"Glorious piggu noms", "Glorious cluck cluck noms", "Glorious cow juice"};
        Double[] prices = {1.25, 2.50, 1.00};

        PopulateDbAsync(ShoppingListDatabase db) {
            dao = db.listItemDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            dao.deleteAll();

            for(int i = 0; i <= names.length - 1; i++) {
                ListItem listItem = new ListItem(names[i], descriptions[i], false, prices[i]);
                dao.insert(listItem);
            }

            return null;
        }
    }
}
