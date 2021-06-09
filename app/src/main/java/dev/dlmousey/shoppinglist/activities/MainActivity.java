package dev.dlmousey.shoppinglist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dev.dlmousey.shoppinglist.R;
import dev.dlmousey.shoppinglist.adapters.ListItemAdapter;
import dev.dlmousey.shoppinglist.entities.ListItem;
import dev.dlmousey.shoppinglist.listeners.CheckboxItemClickListener;
import dev.dlmousey.shoppinglist.viewmodels.ListItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CheckboxItemClickListener.OnRecyclerClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

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

        listItemViewModel.update(item);

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
            case R.id.action_add_all:
                if (listItemViewModel.getCount() == 0) {
                    Snackbar.make(getWindow().getDecorView(), "No items on list!", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                listItemViewModel.addAllToCart();
                Snackbar.make(getWindow().getDecorView(), "Added all items to cart", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.action_clear:
                if (listItemViewModel.getCount() == 0) {
                    Snackbar.make(getWindow().getDecorView(), "No items on list!", Snackbar.LENGTH_SHORT).show();
                    break;
                }

                listItemViewModel.clear();
                Snackbar.make(getWindow().getDecorView(), "List cleared", Snackbar.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
