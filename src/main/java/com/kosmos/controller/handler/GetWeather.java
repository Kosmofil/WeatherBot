package com.kosmos.controller.handler;


import com.kosmos.controller.TelegramBot;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class GetWeather {
    private static final Logger logger = LoggerFactory.getLogger(GetWeather.class);
    private static int IO_COUNT = 0;
    private static final String url = "https://www.gismeteo.ru/weather-almaty-5205/";

    public static String getWeather(){
        // TODO: 17.09.17 create проверку на изменения 
        Elements elements = getElements(url).get().select(".content");
        return getTextFromElements(elements, "div[class=js_meas_container temperature tab-weather__value]");
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
            logger.error("ошибка с сайтом");

        }
        return documents;
    }

}