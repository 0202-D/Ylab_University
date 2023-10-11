package io.ylab.utils;

import java.math.BigDecimal;

public class ConsoleWriter {
    public void printBalance(BigDecimal balance) {
        System.out.println("Ваш баланс = " + balance);
        System.out.println("**********************");
    }

    public void print(String message) {
        System.out.println(message);
        System.out.println("**********************");
    }
}
