package com.myanimelist.sites;

import android.os.AsyncTask;

import com.myanimelist.Anime;

import org.jsoup.Jsoup;

import java.util.List;

public class IsTheAnimeFinishedManager extends AsyncTask<String, Void, Anime> {

    private OnAnimeFetchedListener listener;
    private SitesUtil sitesUtil;

    public interface OnAnimeFetchedListener {
        void onAnimeFetched(Anime anime);
    }

    public IsTheAnimeFinishedManager(OnAnimeFetchedListener listener) {this.listener = listener;}

    @Override
    protected Anime doInBackground(String... params) {
        String urlString = params[0];

        try {
            sitesUtil = new SitesUtil(Jsoup.connect(urlString).get());

            String nome = sitesUtil.searchInPage("h1").replace(" Todos os Episodios Online","");
            List<String> generos = sitesUtil.searchAllInPage(".sgeneros", "a");
            String sinopse = sitesUtil.searchInPage(".wp-content");
            int lancamento = Integer.parseInt(sitesUtil.searchInPage(".date"));
            byte[] imagem = SitesUtil.downloadImage(sitesUtil.searchImageSrc("poster"));
            StringBuilder generosTexto = new StringBuilder();
            for (int i = 0; i < generos.size(); i++) {
                generosTexto.append(generos.get(i));
                if (i < generos.size() - 1) generosTexto.append(", ");
            }

            return new Anime(urlString, nome, lancamento, generosTexto.toString(), sinopse, imagem);

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