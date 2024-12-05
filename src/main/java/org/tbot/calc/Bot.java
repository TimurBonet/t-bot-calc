package org.tbot.calc;

import lombok.Getter;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;

import java.util.HashMap;
import java.util.Map;

public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    @Getter
    private static final Settings defailtSettings = new Settings(1, 10, 1);

    private final NonCommand nonCommand;

    @Getter
    private static Map<Long, Settings> userSettings;

    public Bot(String botName, String botToken) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        this.nonCommand = new NonCommand();
        register(new StartCommand("start", "Старт"));
        register(new PlusCommand("plus", "Сложение"));
        register(new MinusCommand("minus", "Вычитание"));
        register(new PlusMinusCommand("minus", "Сложение и вычитание"));
        register(new HelpCommand("help", "Помощь"));
        register(new SettingsCommand("settings", "Мои настройки"));
        userSettings = new HashMap<>();
    }
}
