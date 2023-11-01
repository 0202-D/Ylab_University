package io.ylab;

import io.ylab.dto.activity.ActivityRsDto;
import io.ylab.dto.transaction.CreditAndDebitRqDto;
import io.ylab.dto.transaction.TransactionHistoryRsDto;
import io.ylab.dto.transaction.UserBalanceRsDto;
import io.ylab.dto.user.UserDtoRs;
import io.ylab.dto.user.UserRqDto;
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

    public static UserRqDto getUserRqDto() {
        return UserRqDto.builder()
                .userName("username")
                .password("password")
                .build();
    }

    public static UserDtoRs getUserDtoRs() {
        return UserDtoRs.builder()
                .userName("username")
                .userId(1L)
                .balance(new BigDecimal(100)).
                build();
    }

    public static UserBalanceRsDto getUserBalanceRsDto() {
        return UserBalanceRsDto.builder()
                .userName("username")
                .balance(new BigDecimal(100))
                .build();
    }

    public static CreditAndDebitRqDto getCreditAndDebitRqDto() {
        return CreditAndDebitRqDto.builder()
                .userId(1L)
                .sum(new BigDecimal(100))
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

    public static TransactionHistoryRsDto getTransactionHistoryRsDto() {
        return TransactionHistoryRsDto.builder()
                .transactionId(1L)
                .transactionalType(TransactionalType.CREDIT)
                .sum(new BigDecimal(100))
                .userId(1)
                .build();
    }
    public static ActivityRsDto getActivityRsDto(){
        return ActivityRsDto.builder()
                .userId(1L)
                .activity(Activity.HISTORY)
                .actionId(1L)
                .build();
    }
    public static ActivityRsDto getSecondActivityRsDto(){
        return ActivityRsDto.builder()
                .userId(1L)
                .activity(Activity.BALANCE)
                .actionId(2L)
                .build();
    }

    public static Transaction getCreditTransaction() {
        return Transaction.builder()
                .transactionId(2L)
                .transactionalType(TransactionalType.CREDIT)
                .sum(new BigDecimal("50"))
                .user(getUser())
                .build();
    }

    public static Action getHistoryAction(User user) {
        return Action.builder()
                .actionId(1L)
                .activity(Activity.HISTORY)
                .user(user)
                .build();
    }

    public static Action getCreditAction(User user) {
        return Action.builder()
                .actionId(2L)
                .activity(Activity.CREDIT)
                .user(user)
                .build();
    }

    public static Action getDebitAction(User user) {
        return Action.builder()
                .actionId(3L)
                .activity(Activity.DEBIT)
                .user(user)
                .build();
    }

    public static Action getEnterAction(User user) {
        return Action.builder()
                .actionId(5L)
                .activity(Activity.ENTERED)
                .user(user)
                .build();
    }

    public static Action getExitAction(User user) {
        return Action.builder()
                .actionId(3L)
                .activity(Activity.EXITED)
                .user(user)
                .build();
    }

}
