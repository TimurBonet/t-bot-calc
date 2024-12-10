package org.tbot.calc;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

public class Utils {

    public static String getUserName(Message message) {
        return getUserName(message.getFrom());
    }

    public static String getUserName(User user) {
        return (user.getUserName()!= null) ? user.getUserName() :
                String.format("%s %s", user.getFirstName(), user.getLastName());
    }
}
