package com.kosmos.controller;

import com.kosmos.controller.handler.GetBashorg;
import com.kosmos.controller.handler.GetWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.IOException;

import static com.kosmos.model.commands.CommandList.*;



public class TelegramBot extends TelegramLongPollingBot {
    private int COUNT_HELP = 0;
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

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
        return null;
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

    public void IncomingMessage(Message message) throws TelegramApiException, IOException {

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
        sendMessage(sendMessageRequest);

    }


    private SendMessage send(Message message, String command) throws TelegramApiRequestException, IOException {

        if (command.equals(DEFAULT_TEXT)) {
            return conSend(message)
                    .setReplyToMessageId(message.getMessageId())
                    .setText(DEFAULT_TEXT);
        } else
            return conSend(message).setText(command);

    }

    private SendMessage sendWeather(Message message) {
        return conSend(message).setText("сейчас температура в Алматы: " + GetWeather.getWeather());
    }

    private SendMessage sendRandomBash(Message message) throws IOException {
        return conSend(message).setText(GetBashorg.getRandomQoute());
    }


    private static SendMessage conSend(Message message) {
        SendMessage sendingMessage = new SendMessage();
        sendingMessage.enableMarkdown(true);
        sendingMessage.setChatId(message.getChatId().toString());
        return sendingMessage;
    }

}
