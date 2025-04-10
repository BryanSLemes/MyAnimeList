package com.myanimelist.sites;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SitesUtil {

    private Document doc;

    public SitesUtil(Document doc){
        this.doc = doc;
    }

    public static byte[] downloadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream input = connection.getInputStream();
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;

            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }

            input.close();
            connection.disconnect();

            return output.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String searchInPage(String element) {
        Element found = doc.selectFirst(element);
        return found != null ? found.text() : "";
    }

    public String searchImageSrc(String className) {
        Element imgElement = doc.selectFirst("." + className + " img");
        return imgElement != null ? imgElement.attr("src") : "";
    }

    public List<String> searchAllInPage(String parent, String child) {
        List<String> results = new ArrayList<>();
        if (doc != null) {
            for (Element el : doc.select(parent + " " + child)) {
                results.add(el.text());
            }
        }
        return results;
    }

}
