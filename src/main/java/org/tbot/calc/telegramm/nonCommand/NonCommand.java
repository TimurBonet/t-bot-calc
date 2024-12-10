package org.tbot.calc.telegramm.nonCommand;

import lombok.extern.slf4j.Slf4j;
import org.tbot.calc.exceptions.IllegalSettingsException;
import org.tbot.calc.telegramm.Bot;

@Slf4j
public class NonCommand {

    public String nonCommandRequest(Long chatId, String userName, String text) {
        log.debug("Пользователь {}. Обработка сообщения {}, не являющегося командой.", userName, text);
        Settings settings;
        String answer;

        try {
            log.debug("Пользователь {}. Попытка создать объект настроек из сообщения {}", userName, text);
            settings = createSettings(text);
            saveUserSettings(chatId, settings);
            log.debug("Пользователь {}. Объект настроек из сообщения {} создан и сохранён.",
                    userName, text);
            answer = String.format("Настройки обновлены. Вы можете увидеть их с помощью команды /settings {}",
                    createSettingsWarning(settings));
        } catch (IllegalSettingsException e) {
            log.debug("Пользоветель {}. Не удалось создать объект настроек из сообщения {}, {}",
                    userName, text ,e.getMessage());
            answer = e.getMessage() + "\n\n Настройки не были изменены." +
                    " Вы можете увидеть их с помощью команды /settings";
        } catch (Exception e) {
            log.debug("Пользоветель {}. Не удалось создать объект настроек из сообщения {}, {}, {}",
                    userName, text ,e.getMessage(), e.getClass().getSimpleName(), e.getMessage());
            answer = "Сообщение не соответствует формату или использованы слишком болшие числа \n\n" +
                    "Возможно вам поможет команда /help";
        }
        log.debug("Пользователь {}. Завершена обработка сообщения {}, не являющегося командой", userName, text);
        return answer;
    }

    //Получение настроек из полученного сообщения
    private Settings createSettings(String text) throws IllegalArgumentException {
        // отфильтровать файлы, стикеры, гифки и пр.
        if (text == null) {
            throw new IllegalArgumentException("Сообщение не является текстом");
        }
        text = text.replaceAll("-", "")
                .replaceAll(", ", ",")
                .replaceAll(" ", ",");
        String[] parameters = text.split(",");
        if (parameters.length != 3) {
            throw new IllegalArgumentException(String.format("Не удалось преобразовать сообщение {}, " +
                    "на 3 составляющих", text));
        }
        int min = Integer.parseInt(parameters[0]);
        int max = Integer.parseInt(parameters[1]);
        int pageCount = Integer.parseInt(parameters[2]);
        validateSettings(min, max, pageCount);
        return new Settings(min, max, pageCount);
    }

    //Проверка настроек на корректность
    private void validateSettings(int min, int max, int pageCount) {
        if (min <= 0 || max <= 0 || pageCount <= 0) {
            throw new IllegalSettingsException("Ни один из параметров не может быть меньше или равен нулю!");
        }
        if (min> 10000 || max >10000) {
            throw new IllegalSettingsException("Хаданные значения слишком велики, не стоит перегружать ребёнка");
        }
    }


    //Добавление настроек в Map<Long,Settings> чтобы использовать для пользователя при генерации файла
    //Если настройки совпадают с дефолтными, они не сохраняются
    private void saveUserSettings(Long chatId, Settings settings) {
        if (!settings.equals(Bot.getDefailtSettings())){
            Bot.getUserSettings().put(chatId, settings);
        } else {
            Bot.getUserSettings().remove(chatId);
        }
    }

    private String createSettingsWarning(Settings settings) {
        return (settings.getPlusMinusUniqueTaskCount() == 0) ?
                String.format("\r\n\r\n Для пары чисел {} - {} не существует сложений и вычитаний," +
                        "результат оторых попадает в интервал между ними, поэтому вместо минимального значения при " +
                        "формировании заданий будет использовано число 1", settings.getMin(),settings.getMax()) : "";
    }
}
