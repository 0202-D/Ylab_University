package io.ylab.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    String userName;
    String password;
    BigDecimal balance;
    boolean isAuthenticated;
}
