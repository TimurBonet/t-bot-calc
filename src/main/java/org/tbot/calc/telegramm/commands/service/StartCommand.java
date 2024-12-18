package org.tbot.calc.telegramm.commands.service;
import lombok.extern.slf4j.Slf4j;
import org.tbot.calc.Utils;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Команда "Старт"
 */
@Slf4j
public class StartCommand extends ServiceCommand {

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        // Формируем имя
        String userName = Utils.getUserName(user);

        log.debug(String.format("User name %s, начато выполнение команды %s", userName, this.getCommandIdentifier));

        sendAnswer(absSender,chat.getId(),this.getCommandIdentifier(), userName,
                "Приступим! Если Вам потребуется помощь, нажмите /help");
        log.debug(String.format("Пользователь %s. Команда %s выполнена.", userName, this.getCommandIdentifier()));
    }
}
