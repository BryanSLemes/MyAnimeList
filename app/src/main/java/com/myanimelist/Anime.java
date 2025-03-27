package com.myanimelist;

import java.util.ArrayList;

public class Anime {
    private int id;
    private String nome;

    public Anime(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome;
    }

    public static Anime findAnimeByName(String name, ArrayList<Anime> animes) {
        for (Anime anime : animes) {
            if (anime.getNome().equalsIgnoreCase(name)) {
                return anime;
            }
        }
        return null;
    }
}
