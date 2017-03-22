package com.kosmos.controller;

import org.telegram.telegrambots.ApiContextInitializer;
        import org.telegram.telegrambots.exceptions.TelegramApiException;
        import org.telegram.telegrambots.TelegramBotsApi;
        import org.telegram.telegrambots.api.methods.send.SendMessage;
        import org.telegram.telegrambots.api.objects.Message;
        import org.telegram.telegrambots.api.objects.Update;
        import org.telegram.telegrambots.bots.TelegramLongPollingBot;


public class TelegramBot extends TelegramLongPollingBot {

    public static void runBot() {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public String getBotUsername() {
        return "Kosmos_Helper_Taskbot";
    }

    @Override
    public String getBotToken() {
        return "317299056:AAFHXGMWUtDd_K-ukxZMQ-zILtbf5SBprrE";
    }


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            if (message.getText().equals("/help"))
            { sendMsg(message, "Привет, я робот");}
            else if (message.getText().equals("/weather")){
                sendMsg(message, "сейчас температура в Алматы: " + GetWeather.getWeather());
            }
            else
            {
                sendMsg(message, "Я не знаю что ответить на это");
            }
        }
    }

    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}