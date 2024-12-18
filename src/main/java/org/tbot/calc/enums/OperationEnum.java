package org.tbot.calc.enums;

import java.util.Arrays;
import java.util.List;

public enum OperationEnum {
    ADDITION,        //сложение
    SUBTRACTION,     //вычитание
    MULTIPLICATION,  //умножение
    DIVISION;        //деление

    public static List<OperationEnum> getPlusMinusOperations() {
        return Arrays.asList(ADDITION, SUBTRACTION);
    }

    public static List<OperationEnum> getMultiplicationDivisionOperations() {
        return Arrays.asList(MULTIPLICATION, DIVISION);
    }

    public static List<OperationEnum> getAllOperations() {
        return Arrays.asList(values());
    }
}
