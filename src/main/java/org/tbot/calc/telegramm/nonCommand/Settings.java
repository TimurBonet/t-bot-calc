package org.tbot.calc.telegramm.nonCommand;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Settings {
    private int min;
    private int max;
    private int pageCount;

    /**
     * Количество уникальных задач на сложение/вычитание, которыне можно сформировать с использованием интервала чисел
     * от min до maх, аналогично следующие переменные.
     */
    @EqualsAndHashCode.Exclude
    private int plusMinusUniqueTaskCount;

    @EqualsAndHashCode.Exclude
    private int multiplicationUniqueTaskCount;

    @EqualsAndHashCode.Exclude
    private int divisionUniqueTaskCount;

    public Settings(int min, int max, int pageCount) {
        this.min = ConfigureSettings.calculateMin(min, max);
        this.max = ConfigureSettings.calculateMax(min, max);
        this.pageCount = ConfigureSettings.calculatePageCount(pageCount);
        this.plusMinusUniqueTaskCount = ConfigureSettings.calculatePlusMinusUniqueTaskCount(this.min, this.max);
        this.multiplicationUniqueTaskCount = ConfigureSettings.calculateMultiplicationUniqueTaskCount(this.min, this.max);
        this.divisionUniqueTaskCount = ConfigureSettings.calculateDivisionUniqueTaskCount(this.min, this.max);
    }



}
