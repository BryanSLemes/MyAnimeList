package com.myanimelist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
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

        animes = databaseManager.getAllAnimeNames();
        atualizarListaAnimes(animes);
        atualizarListaPendentAnimes();

        binding.addAnime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdicionarAnime.class);
                startActivityForResult(intent,1);
            }
        });

        binding.searchAnime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString(),animes);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void atualizarListaAnimes(ArrayList<Anime> animes) {
        binding.listAnimes.removeAllViews();
        binding.listPendentAnimes.removeAllViews();
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

        binding.countAnime.setText("Número de Animes: " + animes.size());
    }

    private void atualizarListaPendentAnimes() {
        ArrayList<Anime> animesPendentes = databaseManager.getAllPendentAnimeNames();
        for (int i = 0; i < animesPendentes.size(); i++) {
            animeNames = new TextView(this);
            animeNames.setTextSize(18);
            animeNames.setLayoutParams(params);
            animeNames.setText(animesPendentes.get(i).getNome());
            animeNames.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    anime = Anime.findAnimeByName(((TextView) v).getText().toString(), animesPendentes);
                    Intent intent = new Intent(MainActivity.this, AnimeView.class);
                    startActivityForResult(intent,1);
                }
            });
            binding.listPendentAnimes.addView(animeNames);
        }

        binding.pendentAnime.setText("Animes pendentes de dados: " + animesPendentes.size());
    }

    private void filterList(String query, ArrayList<Anime> animes) {
        ArrayList<Anime> foundAnimes = new ArrayList<>();

        if (query.isEmpty()) {
            atualizarListaAnimes(animes);
        } else {
            String queryWithoutSpaces = query.replaceAll("\\s+", "").toLowerCase();
            for (Anime anime : animes) {
                String animeName = anime.getNome();
                String animeNameWithoutSpaces = animeName.replaceAll("\\s+", "").toLowerCase();
                if (animeNameWithoutSpaces.contains(queryWithoutSpaces)) {
                    foundAnimes.add(anime);
                }
            }
            atualizarListaAnimes(foundAnimes);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            animes = databaseManager.getAllAnimeNames();
            atualizarListaAnimes(animes);
            atualizarListaPendentAnimes();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}