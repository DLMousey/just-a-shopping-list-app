package com.enderstudy.shoppinglist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.enderstudy.shoppinglist.listeners.CheckboxItemClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CheckboxItemClickListener.OnRecyclerClickListener {

    private ListItemViewModel listItemViewModel;
    private RecyclerView recyclerView;
    private ListItemAdapter adapter;

    private TextView emptyLeadText;
    private TextView emptySubText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        emptyLeadText = findViewById(R.id.empty_lead_textview);
        emptySubText = findViewById(R.id.empty_sub_textview);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        adapter = new ListItemAdapter(this);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new CheckboxItemClickListener(this, recyclerView, this));

        // Retrieve the existing instance of the ListItemViewModel, this will allow us
        // to keep our data that's already in there after a config change (eg. rotating the phone)
        // without running the expensive queries again
        listItemViewModel = ViewModelProviders.of(this).get(ListItemViewModel.class);

        // Observe when the collection of items held in the view model changes (eg. when a query is
        // run that modifies the contents of ListItemViewModel.listItems) and update the items held
        // in the ListItemAdapter to whatever the new set is in the view model
        listItemViewModel.getAllListItems().observe(this, new Observer<List<ListItem>>() {
            @Override
            public void onChanged(@Nullable List<ListItem> listItems) {
                adapter.setListItems(listItems);

                if (listItems.size() == 0) {
                    emptyLeadText.setVisibility(View.VISIBLE);
                    emptySubText.setVisibility(View.VISIBLE);
                } else {
                    emptyLeadText.setVisibility(View.INVISIBLE);
                    emptySubText.setVisibility(View.INVISIBLE);
                }
            }
        });

        ItemTouchHelper deleteHelper = new ItemTouchHelper(
            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                    int position = viewHolder.getAdapterPosition();
                    ListItem listItem = adapter.getListItemAtPosition(position);
                    Snackbar.make(
                            viewHolder.itemView,
                            String.format("Deleting %s from list", listItem.getName()),
                            Snackbar.LENGTH_LONG
                    ).show();

                    listItemViewModel.delete(listItem);
                }
            }
        );

        deleteHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void OnItemClick(View view, int position) {
        ListItem item = adapter.markInBasket(position);
        Boolean inBasket = item.getInBasket();
        String itemName = item.getName();
        String message;

        if(inBasket) {
            message = String.format("Added %s to basket", itemName);
        } else {
            message = String.format("Removed %s from basket", itemName);
        }

        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Add the options menu to the top right of the activity
     * @param menu - Android studio wouldn't stop whining until i added something here
     * @return - Android studio wouldn't stop whining until i added something here
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_privacy:
                Intent privacyIntent = new Intent(this, PrivacyActivity.class);
                startActivity(privacyIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
