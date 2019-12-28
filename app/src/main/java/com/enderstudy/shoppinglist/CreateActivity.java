package com.enderstudy.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.NumberFormat;

public class CreateActivity extends AppCompatActivity {

    private final ListItemRepository listItemRepository = new ListItemRepository(getApplication());

    private TextView nameInput;
    private TextView descriptionInput;
    private TextView priceInput;

    private ListItem editingItem;
    private boolean editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        nameInput = findViewById(R.id.input_name);
        descriptionInput = findViewById(R.id.input_description);
        priceInput = findViewById(R.id.input_price);

        if (getIntent().hasExtra("EditItem")) {
            editing = true;
            NumberFormat format = NumberFormat.getCurrencyInstance();

            ListItem item = (ListItem) getIntent().getSerializableExtra("EditItem");
            nameInput.setText(item.getName());
            descriptionInput.setText(item.getDescription());
            priceInput.setText(item.getPrice().toString());

            editingItem = item;
        } else {
            editing = false;
        }
    }

    public void saveItem(View view) {
        String name = nameInput.getText().toString();
        String description = descriptionInput.getText().toString();
        Double price;

        try {
            price = Double.parseDouble(priceInput.getText().toString());
        } catch (NumberFormatException e) {
            price = null;
        }

        if (!editing) {
            this.createItem(name, description, price);
            return;
        }

        this.updateItem(name, description, price);
    }

    private void createItem(String name, String description, Double price) {
        if (name.isEmpty()) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_LONG);
            return;
        }

        Log.d("CREATEITEM", "createItem: name is not empty");

        Boolean inBasket = false;
        price = (price != null) ? price : 0D;
        ListItem item = new ListItem(name, description, inBasket, price);
        listItemRepository.insert(item);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateItem(String name, String description, Double price) {
        editingItem.setName(name);
        editingItem.setDescription(description);
        editingItem.setPrice((price != null) ? price : 0D);

        listItemRepository.update(editingItem);

        Toast.makeText(this, "Updated " + name, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
