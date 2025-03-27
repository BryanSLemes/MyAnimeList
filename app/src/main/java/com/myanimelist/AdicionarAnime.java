package com.myanimelist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.myanimelist.databinding.ActivityAdicionarAnimeBinding;

public class AdicionarAnime extends AppCompatActivity {

    private ActivityAdicionarAnimeBinding binding;
    private Uri selectedImageUri;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdicionarAnimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        databaseManager = new DatabaseManager(this);

        binding.buttonSelectImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, 1);
        });

        binding.buttonSave.setOnClickListener(v -> {
            String animeName = binding.editTextAnimeName.getText().toString();
            String imagePath = "";

            if (binding.radioButtonEnterPath.isChecked()) {
                imagePath = binding.editTextImagePath.getText().toString();
            } else if (binding.radioButtonSelectImage.isChecked() && selectedImageUri != null) {
                imagePath = selectedImageUri.toString();
            }

            if (!animeName.isEmpty() && !imagePath.isEmpty()) {
                try {
                    databaseManager.addAnime(animeName, imagePath);
                    Toast.makeText(this, "Dados Salvos: " + animeName, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Erro ao cadastrar anime", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        binding.radioGroupImageOption.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonEnterPath) {
                binding.editTextImagePath.setVisibility(View.VISIBLE);
                binding.buttonSelectImage.setVisibility(View.GONE);
            } else if (checkedId == R.id.radioButtonSelectImage) {
                binding.editTextImagePath.setVisibility(View.GONE);
                binding.buttonSelectImage.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
        }
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