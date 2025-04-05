package com.myanimelist;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.myanimelist.databinding.ActivityAnimeViewBinding;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AnimeView extends AppCompatActivity {

    private ActivityAnimeViewBinding binding;
    private Anime anime;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_view);

        binding = ActivityAnimeViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseManager = new DatabaseManager(this);
        anime = MainActivity.anime;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(anime.getNome());

//        try {
//            Uri imageUri = Uri.parse(anime.getImage());
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//            binding.imgAnime.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            Toast.makeText(this, "Erro caminho imagem: " + anime.getImage(), Toast.LENGTH_SHORT).show();
//        }

        new DownloadImageTask(binding.imgAnime, databaseManager, anime).execute("https://animesgames.cc/wp-content/uploads/2019/08/assistir-kimi-ni-todoke-2-2-temporada-todos-os-episodios-super-gratis-animes-online-hd.jpg");






        binding.btnExclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setMessage("Tem certeza que quer excluir este anime?")
                        .setCancelable(false)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                databaseManager.deleteAnimeById(anime.getId());
                                setResult(RESULT_OK);
                                finish();
                                Toast.makeText(getApplicationContext(), "Anime excluído com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
            }
        });

        ArrayList<Object> elementos = new ArrayList<>();
        elementos.add(binding.webPage);
        elementos.add(binding.nomeANime);

        //new WebPageManager().execute("https://animesonlinecc.to/anime/kimi-no-koto-ga-daidaidaidaidaisuki-na-100-nin-no-kanojo/", elementos);
//        WebPageManager webPageManager = new WebPageManager();
//        webPageManager.fetchPage("https://animesonlinecc.to/anime/kimi-no-koto-ga-daidaidaidaidaisuki-na-100-nin-no-kanojo/", new WebPageManager.WebPageCallback() {
//            @Override
//            public void onResult(String result) {
//                binding.nomeANime.setText(result);
//            }
//
//            @Override
//            public void onError(String error) {
//                binding.nomeANime.setText("Erro: " + error);
//            }
//        });

//        String nome = webPageManager.searchInPage("h1");
//


//        binding.nomeANime.setText(nome);
//        binding.webPage.setText(webPageManager.searchInPage("sgeneros"));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private DatabaseManager databaseManager;
        private Anime anime;

        public DownloadImageTask(ImageView imageView, DatabaseManager databaseManager, Anime anime) {
            this.imageView = imageView;
            this.databaseManager = databaseManager;
            this.anime = anime;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String urlString = params[0];
            Bitmap bitmap = null;
            try {
                // Cria a URL e abre a conexão
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();

                // Obtém o InputStream da URL
                InputStream inputStream = connection.getInputStream();

                // Decodifica o InputStream em um Bitmap
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        public byte[] bitmapToByteArray(Bitmap bitmap) {
            // Cria um ByteArrayOutputStream para armazenar os bytes
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            // Compressão da Bitmap em formato PNG ou JPEG (dependendo da sua necessidade)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

            // Retorna o array de bytes
            return byteArrayOutputStream.toByteArray();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Se a imagem foi carregada, a exibe
            if (result != null) {
                imageView.setImageBitmap(result);

                databaseManager.updateImageInDatabase(anime.getId(), bitmapToByteArray(result));


            } else {
                // Caso contrário, exibe uma mensagem de erro
                Toast.makeText(imageView.getContext(), "Falha ao carregar a imagem", Toast.LENGTH_SHORT).show();
            }
        }
    }

}