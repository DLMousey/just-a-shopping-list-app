package com.enderstudy.shoppinglist.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.enderstudy.shoppinglist.entities.ListItem;
import com.enderstudy.shoppinglist.databases.ShoppingListDatabase;
import com.enderstudy.shoppinglist.dao.ListItemDao;

import java.util.List;

public class ListItemRepository {

    private static final String TAG = ListItemRepository.class.getSimpleName();

    private ListItemDao listItemDao;
    private LiveData<List<ListItem>> allListItems;

    public ListItemRepository(Application application) {
        ShoppingListDatabase db = ShoppingListDatabase.getDatabase(application); // Get/create the database class
        listItemDao = db.listItemDao(); // Fetch the data access object for the list item
        allListItems = listItemDao.getAllListItems(); // Get all list items and store a copy of them in this repository as a cache
    }

    public LiveData<List<ListItem>> getAllListItems() {
        return allListItems;
    }

    public void insert(ListItem listItem) {
        new insertAsyncTask(listItemDao).execute(listItem);
    }

    public void update(ListItem listItem) {
        new updateAsyncTask(listItemDao).execute(listItem);
    }

    public void delete(ListItem listItem) {
        new deleteAsyncTask(listItemDao).execute(listItem);
    }

    // Inner class that contains the logic for inserting a new ListItem into the database.
    // Because this is an async task it'll be executed on another thread
    private static class insertAsyncTask extends AsyncTask<ListItem, Void, Void> {

        private ListItemDao asyncListItemDao;

        // Take the data access object that was passed in and store it, we'll need an existing reference
        // to this class because instantiating a bunch of these on separate threads will murder performance
        insertAsyncTask(ListItemDao dao) {
            asyncListItemDao = dao;
        }

        // Override the async task's default doInBackground logic with our own (override annotation helps
        // compiler figure out wtf's going on). Not quite sure about the tuple into params, is this some java magic?
        @Override
        protected Void doInBackground(final ListItem... params) {
            asyncListItemDao.insert(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<ListItem, Void, Void> {

        private ListItemDao asyncListItemDao;

        updateAsyncTask(ListItemDao dao) {
            asyncListItemDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItem... params) {
            asyncListItemDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<ListItem, Void, Void> {

        private ListItemDao asyncListItemDao;

        deleteAsyncTask(ListItemDao dao) {
            asyncListItemDao = dao;
        }

        @Override
        protected Void doInBackground(final ListItem... params) {
            asyncListItemDao.delete(params[0]);
            return null;
        }
    }
}
