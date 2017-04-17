package com.kosmos.controller.handler;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetExchange {


    private static ArrayList<String> currencyList = new ArrayList<>();

    public static void getExch() throws IOException {
        Document document = Jsoup
                .connect("https://kurs-valut.kz/").get();
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

    public static String getCurrency(String currency) {

        String result = null;
        for (int i = 0; i < currencyList.size(); i++) {
            if (currencyList.get(i).contains(currency)) {
                result = currencyList.get(i);
            }
        }
        return result;
    }

}
