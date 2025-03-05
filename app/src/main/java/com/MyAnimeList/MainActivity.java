package com.MyAnimeList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        TextView textView = findViewById(R.id.textView);
        textView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdicionarAnime.class);
            startActivity(intent);
        });

    }
}
