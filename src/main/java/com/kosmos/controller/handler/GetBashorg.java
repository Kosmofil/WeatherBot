package com.kosmos.controller.handler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.util.*;

public class GetBashorg implements Runnable {


    private static final String url = "http://bashorg.org/page/";
    static int[] count = {0};
    private static HashMap<Integer, String> mapText = new HashMap<>();


    //рандомно получаем цитату
    public static String getingBash() {

        int key = (int) (Math.random() * mapText.size());
        return mapText.get(key);
    }

    //добавляем в map текст цитаты
    private static void getBash(int countPage) throws IOException {

        Document document = Jsoup.connect(url + countPage).get();
        final List<Element> elements = document.select(".quote");
        elements.forEach((elem) -> {
            String text = get(elem, "div[class=quote]");
            mapText.put(count[0]++, text);
        });
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
        for (int i = 0; i <= 100; i++) {
            try {
                getBash(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
