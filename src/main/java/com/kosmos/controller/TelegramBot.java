package com.kosmos.controller;

import com.kosmos.controller.handler.GetBashorg;
import com.kosmos.controller.handler.GetWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

import static com.kosmos.controller.handler.GetExchange.getCurrency;
import static com.kosmos.controller.handler.GetExchange.getExch;
import static com.kosmos.model.commands.CommandList.*;


public class TelegramBot extends TelegramLongPollingBot {
    private int COUNT_HELP = 0;
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);
    private static ArrayList<String> list = new ArrayList<>();
    private static File file = new File("Pressure.txt");

    public static void runBot() {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new TelegramBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }


    @Override
    public String getBotUsername() {
        return "Jarvis";
    }

    @Override
    public String getBotToken() {
        return "317299056:AAFHXGMWUtDd_K-ukxZMQ-zILtbf5SBprrE";
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message message = update.getMessage();
                if (message.hasText() || message.hasLocation()) {
                    IncomingMessage(message);
                }
            }
        } catch (Exception e) {
            BotLogger.error(ERROR_MES, e);
        }

    }


    private void IncomingMessage(Message message) throws TelegramApiException, IOException {

        SendMessage sendMessageRequest;

        switch (message.getText()) {
            case COMMAND_HELP:
                logger.info("help run = " + COUNT_HELP++);
                sendMessageRequest = send(message, HELP_TEXT);
                break;
            case COMMAND_WEATHER:
                sendMessageRequest = sendWeather(message);
                break;
            case COMMAND_COMMANDS:
                sendMessageRequest = send(message, COMMANDS_TEXT);
                break;
            case COMMAND_NEXT_BASH:
                sendMessageRequest = sendRandomBash(message);
                break;
            default:
                sendMessageRequest = send(message, DEFAULT_TEXT);
        }
//мониторю свое давление, и сохраняю в текстовый файл
        if (message.getText().contains("АД")) {
            Date date = new Date();
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(message.getText() + " " + date + " " + message.getChatId() + System.lineSeparator());
                writer.flush();
            }
            sendMessageRequest = send(message, ARTRIAL_PRESSURE);
        }


        for (String anEUR : USD_LIST) {
            if (message.getText().toLowerCase().contains(anEUR)) {
                sendMessageRequest = sendCurrency(message, USD);
            }
        }
        for (String anUsd : EUR_LIST) {
            if (message.getText().toLowerCase().contains(anUsd)) {
                sendMessageRequest = sendCurrency(message, EUR);
            }
        }
        for (String anEUR : RUB_LIST) {
            if (message.getText().toLowerCase().contains(anEUR)) {
                sendMessageRequest = sendCurrency(message, RUB);
            }
        }
        for (String anEUR : AUD_LIST) {
            if (message.getText().toLowerCase().contains(anEUR)) {
                sendMessageRequest = sendCurrency(message, AUD);
            }
        }
        sendMessage(sendMessageRequest);

    }


    private SendMessage send(Message message, String command) throws TelegramApiRequestException, IOException {

        if (command.equals(DEFAULT_TEXT)) {
            return configSend(message)
                    .setReplyToMessageId(message.getMessageId())
                    .setText(DEFAULT_TEXT);
        } else if (command.equals(ARTRIAL_PRESSURE)) {
            return configSend(message).setText("Ваши показания артериального давления сохранены " +
                    "пожалуйста не забудьте внесте следующие показания");
        } else
            return configSend(message).setText(command);

    }

    private SendMessage sendWeather(Message message) throws TelegramApiException {

        return configSend(message).setText("сейчас температура в Алматы: " + GetWeather.getWeather());
    }

    private SendMessage sendRandomBash(Message message) throws IOException {
        return configSend(message).setText(GetBashorg.getRandomQoute());

    }

    private SendMessage sendCurrency(Message message, String currency) throws IOException {
        getExch();//вызываем этот метод всегда, чтобы была актуальная инфа
        return configSend(message).setText(getCurrency(currency));
    }

    private static SendMessage configSend(Message message) {
        SendMessage sendingMessage = new SendMessage();
        sendingMessage.enableMarkdown(true);
        sendingMessage.setChatId(message.getChatId().toString());
//        list.add(message.getChat().getUserName());
        return sendingMessage;
    }


}
