package io.ylab.aop.aspect;

import io.ylab.dao.action.ActionRepository;
import io.ylab.dao.user.UserRepository;
import io.ylab.exception.NotFoundException;
import io.ylab.model.Action;
import io.ylab.model.Activity;
import io.ylab.model.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class AuditAspect {
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;

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
    // добавить остальные методы аудита
}



