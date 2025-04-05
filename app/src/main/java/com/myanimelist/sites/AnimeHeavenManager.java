package com.myanimelist.sites;

import android.os.AsyncTask;
import com.myanimelist.Anime;
import org.jsoup.Jsoup;
import java.util.List;

public class AnimeHeavenManager extends AsyncTask<String, Void, Anime> {

    private OnAnimeFetchedListener listener;
    private SitesUtil sitesUtil;

    public interface OnAnimeFetchedListener {
        void onAnimeFetched(Anime anime);
    }

    public AnimeHeavenManager(OnAnimeFetchedListener listener) {this.listener = listener;}

    @Override
    protected Anime doInBackground(String... params) {
        String urlString = params[0];

        try {
            sitesUtil = new SitesUtil(Jsoup.connect(urlString).get());

            String nome = sitesUtil.searchInPage(".infotitle.c");
            String sinopse = sitesUtil.searchInPage(".infodes.c");
            List<String> generos = sitesUtil.searchAllInPage(".infotags.c", "a");
            List<String> epAndYear = sitesUtil.searchAllInPage(".infoyear.c", ".inline");
            byte[] imagem = SitesUtil.downloadImage(sitesUtil.searchImageSrc("infoimg"));

            StringBuilder generosTexto = new StringBuilder();
            for (int i = 0; i < generos.size(); i++) {
                generosTexto.append(generos.get(i));
                if (i < generos.size() - 1) generosTexto.append(", ");
            }

            int episodes = Integer.parseInt(epAndYear.get(0));
            int lancamento = Integer.parseInt(epAndYear.get(1).split("-")[0]);

            return new Anime(urlString, nome, lancamento, episodes,  generosTexto.toString(), sinopse, imagem);

        } catch (Exception e) {
            e.printStackTrace();

            return new Anime(
                    urlString,
                    "Erro ao carregar: " + e.getClass().getSimpleName(),
                    0,
                    "",
                    "Mensagem: " + e.getMessage(),
                    null
            );
        }
    }

    @Override
    protected void onPostExecute(Anime anime) {
        if (listener != null) {
            listener.onAnimeFetched(anime);
        }
    }

}