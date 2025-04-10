package com.myanimelist.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import lombok.Data;

@Entity
@Data
public class AnimeEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "link")
    private String link;

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "number_episodes")
    private int numberEpisodes;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "date_release")
    private int dateRelease;

    @ColumnInfo(name = "studio")
    private String studio;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "sinopse")
    private String sinopse;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    // Construtor usado pelo Room
    public AnimeEntity(int id, String link, String nome, int numberEpisodes, String status, int dateRelease, String studio, String gender, String sinopse, byte[] image) {
        this.id = id;
        this.link = link;
        this.nome = nome;
        this.numberEpisodes = numberEpisodes;
        this.status = status;
        this.dateRelease = dateRelease;
        this.studio = studio;
        this.gender = gender;
        this.sinopse = sinopse;
        this.image = image;
    }

    // Construtores auxiliares ignorados pelo Room
    @Ignore
    public AnimeEntity(String link, String nome, int dateRelease, String gender, String sinopse, byte[] image) {
        this.link = link;
        this.nome = nome;
        this.dateRelease = dateRelease;
        this.gender = gender;
        this.sinopse = sinopse;
        this.image = image;
    }

    @Ignore
    public AnimeEntity(String link, String nome, int dateRelease, int numberEpisodes, String gender, String sinopse, byte[] image) {
        this.link = link;
        this.nome = nome;
        this.dateRelease = dateRelease;
        this.numberEpisodes = numberEpisodes;
        this.gender = gender;
        this.sinopse = sinopse;
        this.image = image;
    }

    @Ignore
    public AnimeEntity(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Imagem: " + image;
    }
}
