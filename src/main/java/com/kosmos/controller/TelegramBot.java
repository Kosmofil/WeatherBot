package com.kosmos.controller;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;

import static com.kosmos.controller.commands.CommandList.*;


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


    @Override
    public String getBotUsername() {
        return "_____________";
    }

    @Override
    public String getBotToken() {
        return "___________________";
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

    public void IncomingMessage(Message message) throws TelegramApiException {

        SendMessage sendMessageRequest = new SendMessage();

        switch (message.getText()) {
            case COMMAND_HELP:
                sendMessageRequest = sendHelp(message);
                break;
            case COMMAND_START:
                sendMessageRequest = sendTest(message);
                break;
            case COMMAND_WEATHER:
                sendMessageRequest = sendWeather(message);
                break;
            case COMMAND_COMMANDS:
                sendMessageRequest = sendTextMessage(message);
        }

        sendMessage(sendMessageRequest);

    }

    private SendMessage sendHelp(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(HELP_TEXT);
        return sendMessage;
    }

    private SendMessage sendWeather(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText("сейчас температура в Алматы: " + GetWeather.getWeather());
        return sendMessage;
    }

    private SendMessage sendTest(Message message) throws TelegramApiRequestException {
        SendMessage sendingMessage = new SendMessage();
        sendingMessage.enableMarkdown(false);
        sendingMessage.setChatId(message.getChatId().toString());
        //sendingMessage.setReplyToMessageId(message.getMessageId());
        sendingMessage.setText("test");

        return sendingMessage;
    }

    private SendMessage sendTextMessage(Message message) {
        SendMessage sendingMessage = new SendMessage();
        sendingMessage.enableMarkdown(true);
        sendingMessage.setChatId(message.getChatId().toString());
        sendingMessage.setText(COMMANDS_TEXT);
        return sendingMessage;
    }

}
