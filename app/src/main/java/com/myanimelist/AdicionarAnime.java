package com.myanimelist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.myanimelist.databinding.ActivityAdicionarAnimeBinding;
import com.myanimelist.sites.AnimeHeavenManager;
import com.myanimelist.sites.AnimesOnlineManager;
import com.myanimelist.sites.IsTheAnimeFinishedManager;
import com.myanimelist.sites.SetImageViewByLink;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AdicionarAnime extends AppCompatActivity {

    private ActivityAdicionarAnimeBinding binding;
    private DatabaseManager databaseManager;
    private byte[] animeImage;
    private SetImageViewByLink setImageViewByLink;
    private AnimesOnlineManager animeOnlineManager;
    private AnimeHeavenManager animeHeavenManager;
    private IsTheAnimeFinishedManager isTheAnimeFinishedManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdicionarAnimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseManager = new DatabaseManager(this);

        setImageViewByLink = new SetImageViewByLink(new SetImageViewByLink.OnImageFetchedListener() {
            @Override
            public void onImageFetched(byte[] imagem) {
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imagem, 0, imagem.length);
                    binding.imgAnime.setImageBitmap(bitmap);
                    animeImage = imagem;
                    binding.btnSelectImage.setText("Substituir Imagem");
                }catch (Exception e){
                    Toast.makeText(AdicionarAnime.this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
                }
            }});

        animeOnlineManager = new AnimesOnlineManager(new AnimesOnlineManager.OnAnimeFetchedListener() {
            @Override
            public void onAnimeFetched(Anime anime) {
                if (anime != null && !anime.getNome().contains("Erro ao carregar: ")) {
                    binding.edtAnimeName.setText(anime.getNome());
                    binding.edtAnimeGender.setText(anime.getGender());
                    binding.edtAnimeReleaseYear.setText("" + anime.getDateRelease());
                    binding.edtAnimeSinopse.setText(anime.getSinopse());
                    if (anime.getImage() != null && anime.getImage().length > 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(anime.getImage(), 0, anime.getImage().length);
                        binding.imgAnime.setImageBitmap(bitmap);
                        animeImage = anime.getImage();
                    }
                }else{
                    Toast.makeText(AdicionarAnime.this, "Link Inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        animeHeavenManager = new AnimeHeavenManager(new AnimeHeavenManager.OnAnimeFetchedListener() {
            @Override
            public void onAnimeFetched(Anime anime) {
                if (anime != null && !anime.getNome().contains("Erro ao carregar: ")) {
                    binding.edtAnimeName.setText(anime.getNome());
                    binding.edtAnimeGender.setText(anime.getGender());
                    binding.edtAnimeReleaseYear.setText("" + anime.getDateRelease());
                    binding.edtAnimeEpisodes.setText("" + anime.getNumberEpisodes());
                    binding.edtAnimeSinopse.setText(anime.getSinopse());
                    if (anime.getImage() != null && anime.getImage().length > 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(anime.getImage(), 0, anime.getImage().length);
                        binding.imgAnime.setImageBitmap(bitmap);
                        animeImage = anime.getImage();
                    }
                }else{
                    Toast.makeText(AdicionarAnime.this, "Link Inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        isTheAnimeFinishedManager = new IsTheAnimeFinishedManager(new IsTheAnimeFinishedManager.OnAnimeFetchedListener() {
            @Override
            public void onAnimeFetched(Anime anime) {
                if (anime != null && !anime.getNome().contains("Erro ao carregar: ")) {
//                    binding.edtAnimeName.setText(anime.getNome());
//                    binding.edtAnimeGender.setText(anime.getGender());
//                    binding.edtAnimeReleaseYear.setText("" + anime.getDateRelease());
//                    binding.edtAnimeEpisodes.setText("" + anime.getNumberEpisodes());
//                    binding.edtAnimeSinopse.setText(anime.getSinopse());
//                    if (anime.getImage() != null && anime.getImage().length > 0) {
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(anime.getImage(), 0, anime.getImage().length);
//                        binding.imgAnime.setImageBitmap(bitmap);
//                        animeImage = anime.getImage();
//                    }
                }else{
                    Toast.makeText(AdicionarAnime.this, "Link Inválido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnSelectImage.setOnClickListener(v -> {
            String[] opcoes = {"Galeria", "Link da Web", "Cancelar"};

            ImageOptions(opcoes);
        });

        binding.btnSearchInformacion.setOnClickListener(v -> {
            String link = binding.edtAnimeLink.getText().toString();
            if (link == null || link.trim().isEmpty()){
                Toast.makeText(this, "Preencha o link do anime", Toast.LENGTH_SHORT).show();
            }else {
                String[] opcoes = {"Animes Online", "AnimeHeaven", "Is The Anime Finished.com", "Cancelar"};
                AnimeLinkOptions(opcoes, link);
            }
        });

        binding.btnSave.setOnClickListener(v -> {
//            String animeName = binding.edtAnimeName.getText().toString();
//            String imagePath = "";


//            if (!animeName.isEmpty() && !imagePath.isEmpty()) {
//                try {
//                    databaseManager.addAnime(animeName, null);
//                    Toast.makeText(this, "Dados Salvos: " + animeName, Toast.LENGTH_SHORT).show();
//                    setResult(RESULT_OK);
//                    finish();
//                } catch (Exception e) {
//                    Toast.makeText(this, "Erro ao cadastrar anime" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
//            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];

                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    byteBuffer.write(buffer, 0, len);
                }

                animeImage = byteBuffer.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(animeImage, 0, animeImage.length);
                binding.imgAnime.setImageBitmap(bitmap);
                binding.btnSelectImage.setText("Substituir Imagem");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void AnimeLinkOptions(String[] opcoes, String link){

        new AlertDialog.Builder(this)
                .setTitle("Escolha uma opção")
                .setItems(opcoes, (dialog, which) -> {
                    switch (which) {
                        case 0: // Animes Online
                            animeOnlineManager.execute(link);
                            break;
                        case 1: // AnimeHeaven
                            animeHeavenManager.execute(link);
                            break;
                        case 2: // AnimeHeaven
                            animeHeavenManager.execute(link);
                            break;

                        case 3: //Cancelar
                            dialog.dismiss();
                            break;
                    }
                })
                .show();
    }

    private void ImageOptions(String[] opcoes){

        new AlertDialog.Builder(this)
                .setTitle("Escolha uma opção")
                .setItems(opcoes, (dialog, which) -> {
                    switch (which) {
                        case 0: // Galeria
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.setType("image/*");
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(intent, 1);
                            break;

                        case 1: // Link da Web
                            AlertDialog.Builder inputDialog = new AlertDialog.Builder(this);
                            inputDialog.setTitle("Digite o link da imagem");

                            final EditText input = new EditText(this);
                            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI);
                            inputDialog.setView(input);

                            inputDialog.setPositiveButton("OK", (dialog1, which1) -> {
                                String url = input.getText().toString().trim();
                                if (!url.isEmpty()) {
                                    setImageViewByLink.execute(url);
                                } else {
                                    Toast.makeText(this, "Link vazio!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            inputDialog.setNegativeButton("Cancelar", (dialog1, which1) -> dialog1.cancel());
                            inputDialog.show();
                            break;

                        case 2: //Cancelar
                            dialog.dismiss();
                            break;
                    }
                })
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}