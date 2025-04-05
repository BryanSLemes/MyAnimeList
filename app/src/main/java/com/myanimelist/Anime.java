package com.myanimelist;

import org.w3c.dom.CDATASection;

import java.util.ArrayList;

public class Anime {
    private int id;
    private String link;
    private String nome;
    private int numberEpisodes;
    private String status;
    private int dateRelease;
    private String studio;
    private String gender;
    private String sinopse;
    private byte[] image;

    public Anime(String link, String nome, int dateRelease,String gender, String sinopse, byte[] image) {
        this.link = link;
        this.nome = nome;
        this.dateRelease = dateRelease;
        this.gender = gender;
        this.sinopse = sinopse;
        this.image = image;
    }

    public Anime(String link, String nome, int dateRelease,int numberEpisodes, String gender, String sinopse, byte[] image) {
        this.link = link;
        this.nome = nome;
        this.dateRelease = dateRelease;
        this.numberEpisodes = numberEpisodes;
        this.status = status;
        this.gender = gender;
        this.sinopse = sinopse;
        this.image = image;
    }

    public Anime(int id,String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public String getNome() {
        return nome;
    }

    public int getNumberEpisodes() {
        return numberEpisodes;
    }

    public String getStatus() {
        return status;
    }

    public int getDateRelease() {
        return dateRelease;
    }

    public String getStudio() {
        return studio;
    }

    public String getGender() {
        return gender;
    }

    public String getSinopse() {
        return sinopse;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Imagem: " + image;
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
