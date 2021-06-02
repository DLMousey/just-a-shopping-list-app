package com.enderstudy.shoppinglist.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.enderstudy.shoppinglist.R;

public class PrivacyActivity extends AppCompatActivity {

    private static final String TAG = PrivacyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
    }

    public void openGithub(View view) {
        Uri uri = Uri.parse("https://github.com/dlmousey/shopping-list-app");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);
    }
}
