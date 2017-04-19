package com.kosmos.controller.handler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetExchange {
    private static final String URL_EXCHENGE = "https://kurs-valut.kz/";
    private static ArrayList<String> currencyList = new ArrayList<>();

    public static void getExch() throws IOException {
        Document document = Jsoup
                .connect(URL_EXCHENGE).get();
        Elements rows = document
                .select("div[class=panel-body]")
                .select("table")
                .first()
                .select("tr");
        for (int i = 0; i < rows.size(); i++) {
            String colums = rows.get(i).select("td").text();
            currencyList.add(colums);
        }
    }
    //TODO: 19.04.17 при недоступности сайте вывести исключение
    public static String getCurrency(String currency) {

        String result = null;
        for (String aCurrencyList : currencyList) {
            if (aCurrencyList.contains(currency)) {
                result = aCurrencyList;
            }
        }
        return result;
    }

    // TODO: 18.04.17 пересмотреть данный метод
    //получение значения валюты
    public static double getCurrencyValue(String currency) {
        String temp = getCurrency(currency).replace("KZT", "char")
                .replace("-", "char")
                .replace("+", "char");
        String[] split = temp.split("char");
        return Double.parseDouble(split[1]);
    }

}
