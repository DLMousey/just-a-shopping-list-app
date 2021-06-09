package dev.dlmousey.shoppinglist.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import dev.dlmousey.shoppinglist.entities.ListItem;
import dev.dlmousey.shoppinglist.repositories.ListItemRepository;

import java.util.List;

/*
 * ViewModel class designed to be used by a RecyclerView so changes in configuration (eg. rotating the phone)
 * will not destroy the data, the newly created horizontal/vertical activity will simply grab the existing
 * instance of this class from memory and drop it back in again, eliminating the need to perform all those
 * expensive queries again.
 */
public class ListItemViewModel extends AndroidViewModel {

    private static final String TAG = ListItemViewModel.class.getSimpleName();

    private ListItemRepository repository;
    private LiveData<List<ListItem>> allListItems;

    public ListItemViewModel(Application application) {
        super(application);
        repository = new ListItemRepository(application);
        allListItems = repository.getAllListItems();
    }

    public LiveData<List<ListItem>> getAllListItems() {
        return allListItems;
    }

    // It might seen like we've declared this method in a ton of places, but in reality
    // this is just so we can do away with the implementation detail and focus on the business logic.
    // The repository's job is to figure out the nitty gritty of where this data will end up be it
    // a SQLite database, in memory database, external API etc etc.
    // We give zero F's at this point WHERE or HOW the data is going to be stored, the only
    // thing we care about is that whatever we throw at the repo gets saved SOMEWHERE, SOMEHOW.
    public void insert(ListItem listItem) {
        repository.insert(listItem);
    }

    public void delete(ListItem listItem) {
        repository.delete(listItem);
    }

    public void update(ListItem listItem) {
        repository.update(listItem);
    }

    public void clear() {
        repository.purge();
    }

    public void addAllToCart() {
        repository.addAllToCart();
    }

}
