package io.ylab.auditaspectstarter.aop.aspect;


import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.exception.NotFoundException;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Aspect
public class AuditAspect {
    private static final String USER_NOT_FOUND = "Пользователь не найден";

    private final UserRepository userRepository;
    private final ActionRepository actionRepository;

    @Autowired
    public AuditAspect(ActionRepository actionRepository, UserRepository userRepository) {
        this.actionRepository = actionRepository;
        this.userRepository = userRepository;
    }

    @After("execution(* io.ylab.service.UserServiceImpl.balance(long)) && args(userId)")
    public void logBalanceAction(JoinPoint joinPoint, long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Action action = Action.builder().user(user).activity(Activity.BALANCE).build();
        actionRepository.addAction(action);
    }

    @After("execution(* io.ylab.service.UserService.debit(..)) && args(sum, userId)")
    public void addDebitAction(BigDecimal sum, long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        actionRepository.addAction(Action.builder()
                .user(user)
                .activity(Activity.DEBIT)
                .build());
    }

    @After("execution(* io.ylab.service.UserService.credit(..)) && args(sum, userId)")
    public void addCreditAction(BigDecimal sum, long userId) {
        User user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        actionRepository.addAction(Action.builder()
                .user(user)
                .activity(Activity.CREDIT)
                .build());
    }
    @Around("execution(* io.ylab.service.UserServiceImpl.history(long)) && args(userId)")
    public Object addHistoryAction(ProceedingJoinPoint joinPoint, long userId) throws Throwable {
        var result = joinPoint.proceed();
        var user = userRepository.getById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        actionRepository.addAction(Action.builder()
                .user(user)
                .activity(Activity.HISTORY)
                .build());
        return result;
    }
}



