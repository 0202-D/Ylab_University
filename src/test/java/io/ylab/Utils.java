package io.ylab;

import io.ylab.model.User;

import java.math.BigDecimal;

public class Utils {
    public static User getUser() {
        return User.builder()
                .userName("Иван")
                .password("123")
                .balance(new BigDecimal(100))
                .build();
    }

}
