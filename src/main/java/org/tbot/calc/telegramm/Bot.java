package org.tbot.calc.telegramm;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.tbot.calc.telegramm.nonCommand.Settings;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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
        log.debug("Конструктор суперкласса отработал");
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        log.debug("Имя и токен присвоены");

        this.nonCommand = new NonCommand();
        log.debug("Класс для обработки сообщения, не являющегося командой, создан");

        register(new StartCommand("start", "Старт"));
        log.debug("Команда start создана");

        register(new PlusCommand("plus", "Сложение"));
        log.debug("Команда plus создана");

        register(new MinusCommand("minus", "Вычитание"));
        log.debug("Команда minus создана");

        register(new PlusMinusCommand("plusminus", "Сложение и вычитание"));
        log.debug("Команда plusminus создана");

        register(new MultiplicationCommand("multiply", "умножение"));
        log.debug("Команда multiply создана");

        register(new DivisionCommand("divide", "Деление"));
        log.debug("Команда divide создана");

        register(new MultiplicationDivisionCommand("multiplydivide", "Умножение и деление"));
        log.debug("Команда multiplydivide создана");

        register(new AllCommand("all", "Сложение, вычитание, умножение и деление"));
        log.debug("Команда all создана");

        register(new HelpCommand("help", "Помощь"));
        log.debug("Команда help создана");

        register(new SettingsCommand("settings", "Мои настройки"));
        log.debug("Команда settings создана");

        userSettings = new HashMap<>();
        log.info("Бот создан");
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     *  Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandRequest(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);

        String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
        setAnswer(chatId, userName, answer);
    }

    /**
     *  Получение настроек чата по id, если не были установлены высталвяются настройки по умолчанию
     */
    public static Settings getUserSettings(Long chatId) {
        Map<Long, Settings> userSettings = Bot.getUserSettings();
        Settings settings = userSettings.get(chatId);
        if(settings == null) {
            return defailtSettings;
        }
        return settings;
    }

    /**
     *  Формирование имени пользователя
     */
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getFirstName(), user.getLastName());
    }

    /**
     *  Формирование имени пользователя
     */
    private void setAnswer(Long chatId, String userName, String txt) {
        SendMessage answer = new SendMessage();
        answer.setText(txt);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        }
        catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Это сообщение не является командо. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }

}
