package com.kosmos.controller.handler;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class GetWeather {

    private static int IO_COUNT = 0;
    private static final String url = "https://www.gismeteo.ru/weather-almaty-5205/";

    public static String getWeather(){
        Elements elements = getElements(url).get().select(".content");
        return getTextFromElements(elements, "div[class=js_meas_container temperature]");
    }

    private static String getTextFromElements(Elements element, String cssQuery) {//получаем текст
        Elements result = element.select(cssQuery);
        return result != null ? result.text() : null;
    }

    private static Optional<Document> getElements(String url) {
        Optional<Document> documents = Optional.empty();
        try {
            documents = Optional.of(Jsoup.connect(url).get());
        } catch (IOException e) {
            IO_COUNT++;
        }
        return documents;
    }

}