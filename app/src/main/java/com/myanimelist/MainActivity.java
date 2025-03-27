package com.myanimelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.myanimelist.databinding.ActivityMainBinding;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static Anime anime;
    private ActivityMainBinding binding;
    private TextView animeNames;
    private DatabaseManager databaseManager;
    private ArrayList<Anime> animes;
    private LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseManager = new DatabaseManager(this);
        params.setMargins(0, 0, 0, 30);

        atualizarListaAnimes();

        binding.addAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdicionarAnime.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void atualizarListaAnimes() {
        animes = databaseManager.getAllAnimeNames();
        for (int i = 0; i < animes.size(); i++) {
            animeNames = new TextView(this);
            animeNames.setTextSize(18);
            animeNames.setLayoutParams(params);
            animeNames.setText(animes.get(i).getNome());
            animeNames.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anime = Anime.findAnimeByName(((TextView) v).getText().toString(), animes);
                    Intent intent = new Intent(MainActivity.this, AnimeView.class);
                    startActivityForResult(intent,1);
                }
            });
            binding.listAnimes.addView(animeNames);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            binding.listAnimes.removeAllViews();
            atualizarListaAnimes();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}