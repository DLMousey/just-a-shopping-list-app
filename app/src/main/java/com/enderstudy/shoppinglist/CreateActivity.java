package com.enderstudy.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class CreateActivity extends AppCompatActivity {

    private final ListItemRepository listItemRepository = new ListItemRepository(getApplication());

    private TextView nameInput;
    private TextView descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        nameInput = findViewById(R.id.input_name);
        descriptionInput = findViewById(R.id.input_description);
    }

    public void saveItem(View view) {
        String name = nameInput.getText().toString();
        String description = descriptionInput.getText().toString();
        Boolean inBasket = false;

        ListItem item = new ListItem(name, description, inBasket);

        listItemRepository.insert(item);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
