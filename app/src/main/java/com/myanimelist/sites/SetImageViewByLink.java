package com.myanimelist.sites;

import android.os.AsyncTask;

public class SetImageViewByLink extends AsyncTask<String, Void, byte[]> {

    private OnImageFetchedListener listener;

    public interface OnImageFetchedListener {
        void onImageFetched(byte[] imagem);
    }

    public SetImageViewByLink(OnImageFetchedListener listener) {
        this.listener = listener;
    }

    @Override
    protected byte[] doInBackground(String... params) {
        String urlString = params[0];

        try {
            byte[] imagem = SitesUtil.downloadImage(urlString);

            return imagem;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(byte[] imagem) {
        if (listener != null) {
            listener.onImageFetched(imagem);
        }
    }

}
