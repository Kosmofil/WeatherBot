package com.kosmos.controller;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetWeather {

    public static void main(String[] args) {
        getWeather();
    }

    private static int IO_COUNT = 0;

    List<Document> documents = new ArrayList<>();
   static Document doc = null;
    private static final String url = "https://www.gismeteo.ru/weather-almaty-5205/";

    public static String getWeather(){
        Elements elements = getElements(url).get().select(".content");
       String res = getTextFromElements(elements, "div[class=js_meas_container temperature]");
       return res;
    }

    private static String getTextFromElements(Elements element, String cssQuery) {//получаем текст
        Elements result = element.select(cssQuery);
        if (result != null) {
            return result.text();
        }
        return null;//null продумать
    }

    private static Optional<Document> getElements(String url) {
        Optional<Document> documents = Optional.empty();
        try {
            documents = Optional.of(Jsoup.connect(url).get());
        } catch (IOException e) {
            IO_COUNT++;//e.printStackTrace();
        }
        return documents;
    }
}