package com.kosmos.controller.handler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.*;

public class GetBashorg implements Runnable {

    private static final String url = "http://bashorg.org/page/";
    private static ArrayList<String> listText = new ArrayList<>();


    private static void getQuoteInPage(int countPage) throws IOException {
        Document document = Jsoup.connect(url + countPage).get();
        List<Element> elements = document.select(".quote");
        for (Element elem : elements
                ) {
            String qoute = get(elem, "div[class=quote]");
            listText.add(qoute);
        }
    }

    //рандомно получаем цитату
    public static String getRandomQoute() {
        int key = (int) (Math.random() * listText.size());
        return listText.get(key);
    }

    private static String get(Element element, String cssQuery) {
        final Element result = element.select(cssQuery).first();
        return result != null ? result.html()
                .replaceAll("(<br>)\n+(\\1)*", "\n")
                .replaceAll("<br>", "")
                : null;
    }

    @Override
    public void run() {
        for (int i = 0; i <= 1000; i++) {
            try {
                getQuoteInPage(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
