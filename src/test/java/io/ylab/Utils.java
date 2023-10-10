package io.ylab;

import io.ylab.model.User;

import java.math.BigDecimal;

public class Utils {
    public static User getUser() {
        return User.builder()
                .userName("username")
                .password("password")
                .balance(new BigDecimal(100))
                .build();
    }

}
