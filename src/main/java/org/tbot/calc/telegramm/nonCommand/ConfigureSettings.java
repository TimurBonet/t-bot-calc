package org.tbot.calc.telegramm.nonCommand;


import org.tbot.calc.exceptions.IllegalSettingsException;

public class ConfigureSettings {

    static int calculateMin(int min, int max) {
        if (min == max) {
            throw new IllegalSettingsException("Минимальное и максимальное значение не должны совпадать");
        }
        if (min < max) {
            return min;
        }
        return max;
    }

    static int calculateMax(int min, int max) {
        if (min < max) {
            return max;
        }
        return min;
    }

    static int calculatePageCount(int pageCount) {
        if (pageCount > 15) {
            return 15;
        }
        return pageCount;
    }

    static int calculatePlusMinusUniqueTaskCount(int min, int max) {
        if (max - 2 * min + 1 >= 0) {
            return ((max - 2 * min + 2) * (max - 2 * min + 1)) / 2;
        }
        return 0;
    }

    static int calculateMultiplicationUniqueTaskCount(int min, int max) {
        if (max < 10) {
            return (((max - min + 1) * 10) * 2 - (max - min + 1)) / 2;
        } else {
            return (((max - min + 1) * max) * 2 - (max - min + 1)) / 2;
        }
    }

    static int calculateDivisionUniqueTaskCount(int min, int max) {
        if (max < 10) {
            return (((max - min + 1) * 10) - (max - min + 1)) / 2;
        } else {
            return (((max - min + 1) * max) - (max - min + 1)) / 2;
        }
    }

}
