package org.tbot.calc;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public class Utils {
    /**
     * Формирование имени пользователя
     * @param message сообщение
     */
    public static String getUserName(Message message) {
        return getUserName(message.getFrom());
    }

    /**
     * Формирование имени пользователя. Если есть никнейм используем его, если нет использовать фамилию и имя
     * @param user пользователь
     */
    public static String getUserName(User user) {
        return (user.getUserName()!= null) ? user.getUserName() :
                String.format("%s %s", user.getFirstName(), user.getLastName());
    }
}
