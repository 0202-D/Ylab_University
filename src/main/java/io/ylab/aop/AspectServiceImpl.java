package io.ylab.aop;

import io.ylab.auditaspectstarter.aop.aspect.AspectService;
import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.exception.NotFoundException;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Aspect
public class AspectServiceImpl implements AspectService {
    private static final String USER_NOT_FOUND = "Такого ползователя не существует";
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;

    public AspectServiceImpl(ActionRepository actionRepository, UserRepository userRepository) {
        this.actionRepository = actionRepository;
        this.userRepository = userRepository;
    }

    @Override
    @After("execution(* io.ylab.service.UserServiceImpl.balance(long)) && args(userId)")
    public void logBalanceAction(JoinPoint joinPoint, long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        Action action = Action.builder().user(user).activity(Activity.BALANCE).build();
        actionRepository.addAction(action);
    }

    @Override
    @After("execution(* io.ylab.service.UserService.debit(..)) && args(sum, userId)")
    public void addDebitAction(BigDecimal sum, long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        actionRepository.addAction(Action.builder()
                .user(user)
                .activity(Activity.DEBIT)
                .build());
    }

    @Override
    @After("execution(* io.ylab.service.UserService.credit(..)) && args(sum, userId)")
    public void addCreditAction(BigDecimal sum, long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        actionRepository.addAction(Action.builder()
                .user(user)
                .activity(Activity.CREDIT)
                .build());
    }

    @Override
    @After("execution(* io.ylab.service.UserServiceImpl.history(long)) && args(userId)")
    public void addHistoryAction(JoinPoint joinPoint, long userId) {
        var user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        actionRepository.addAction(Action.builder()
                .user(user)
                .activity(Activity.HISTORY)
                .build());
    }
}
