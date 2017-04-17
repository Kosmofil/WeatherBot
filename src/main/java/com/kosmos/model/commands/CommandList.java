package com.kosmos.model.commands;


import com.kosmos.controller.handler.GetExchange;

public class CommandList {

    public static final String COMMAND_HELP = "/help";
    public static final String COMMAND_NEXT_BASH = "/nextBash";
    public static final String COMMAND_WEATHER = "/weather";
    public static final String COMMAND_COMMANDS = "/commands";
    public static final String ERROR_MES = "error bot";

    public static final String USD = "USD";
    public static final String RUB = "RUB";
    public static final String EUR = "EUR";
    public static final String KGS = "KGS";
    public static final String AUD = "AUD";
    public static final String CNY = "CNY";

    public static final String DEFAULT_TEXT = "на данный момент, я мало что умею, но мы с моим создателем \n " +
            "работаем над моей производительность и способностями";

    public static final String HELP_TEXT =
            "Данный бот создан с целью изучения языка програмирования Java \n" +
                    "и узучения TelegramBot \n" +
                    "для начала работы введите команду  /commands";

    public static final String COMMANDS_TEXT = COMMAND_COMMANDS + " show all commands \n"
            + COMMAND_WEATHER + " show weather in Almaty \n"
            + COMMAND_HELP + " help \n"
            + COMMAND_NEXT_BASH + " рандомно выводит любую цитату, для начала работы необходимо запустить команду \n";

}
