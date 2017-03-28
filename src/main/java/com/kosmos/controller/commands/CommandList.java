package com.kosmos.controller.commands;


public class CommandList {
    public static final String COMMAND_HELP = "/help";
    public static final String COMMAND_START = "/start";
    public static final String COMMAND_WEATHER = "/weather";
    public static final String COMMAND_COMMANDS = "/commands";
    public static final String ERROR_MES = "error bot";

    public static final String HELP_TEXT =
            "Данный бот создан с целью изучения языка програмирования Java \n" +
                    "и узучения TelegramBot \n" +
                    "для начала работы введите команду  /commands";

    public static final String COMMANDS_TEXT = COMMAND_COMMANDS + " show all commands \n"
            + COMMAND_WEATHER + " show weather in Almaty \n" +
            COMMAND_HELP + " help";
}
