package com.enderstudy.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
        Double price = Double.parseDouble(priceInput.getText().toString());

        if (!editing) {
            this.createItem(name, description, price);
        }

        this.updateItem(name, description, price);
    }

    private void createItem(String name, String description, Double price) {
        Boolean inBasket = false;
        ListItem item = new ListItem(name, description, inBasket, price);
        listItemRepository.insert(item);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void updateItem(String name, String description, Double price) {
        editingItem.setName(name);
        editingItem.setDescription(description);
        editingItem.setPrice(price);

        listItemRepository.update(editingItem);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
