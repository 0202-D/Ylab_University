package io.ylab;

import io.ylab.model.*;

import java.math.BigDecimal;

public class Utils {
    public static User getUser() {
        return User.builder()
                .userId(1L)
                .userName("username")
                .password("password")
                .balance(new BigDecimal(100))
                .build();
    }

    public static Transaction getDebitTransaction() {
        return Transaction.builder()
                .transactionId(1L)
                .transactionalType(TransactionalType.DEBIT)
                .sum(new BigDecimal("100"))
                .user(getUser())
                .build();
    }

    public static Transaction getCreditTransaction() {
        return Transaction.builder()
                .transactionId(1L)
                .transactionalType(TransactionalType.CREDIT)
                .sum(new BigDecimal("50"))
                .user(getUser())
                .build();
    }
    public static Action getHistoryAction(User user){
        return Action.builder()
                .actionId(1L)
                .activity(Activity.HISTORY)
                .user(user)
                .build();
    }
    public static Action getCreditAction(User user){
        return Action.builder()
                .actionId(1L)
                .activity(Activity.CREDIT)
                .user(user)
                .build();
    }
    public static Action getDebitAction(User user){
        return Action.builder()
                .actionId(1L)
                .activity(Activity.DEBIT)
                .user(user)
                .build();
    }
    public static Action getEnterAction(User user){
        return Action.builder()
                .actionId(1L)
                .activity(Activity.ENTERED)
                .user(user)
                .build();
    }
    public static Action getExitAction(User user){
        return Action.builder()
                .actionId(1L)
                .activity(Activity.EXITED)
                .user(user)
                .build();
    }

}
