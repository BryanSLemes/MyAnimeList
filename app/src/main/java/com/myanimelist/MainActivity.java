package com.myanimelist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.myanimelist.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

// Inicializando a lista e o adaptador
        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        // Vinculando o adaptador ao ListView
        binding.listView.setAdapter(adapter);

        // Adicionando itens dinamicamente
        addItem("Item 1");
        addItem("Item 2");
        addItem("Item 3");

        // Você pode adicionar novos itens em tempo de execução
        addItem("Novo Item 4");
        Log.d("MainActivity", "Lista de itens: " + items.toString());

        adapter.notifyDataSetChanged();


    }
    private void addItem(String newItem) {
        items.add(newItem); // Adiciona o item à lista
        adapter.notifyDataSetChanged(); // Atualiza o ListView
    }

        private ArrayList<String> getNamesList() {
        ArrayList<String> names = new ArrayList<>();
        names.add("John");
        names.add("Alice");
        names.add("Bob");
        return names;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}