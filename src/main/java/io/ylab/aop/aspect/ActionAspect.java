package io.ylab.aop.aspect;

import io.ylab.model.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ActionAspect {
    private static final Logger logger = LogManager.getLogger(ActionAspect.class);

    @AfterReturning(pointcut = "execution(* io.ylab.service.AuthServiceImpl.addUser(..))", returning = "user")
    public void afterAddUser(JoinPoint joinPoint, User user) {
        // реализовать
        if (user != null) {
            logger.log(Level.INFO, "Новый пользователь " + user.getUserName());
        }
    }

    @AfterReturning(pointcut = "execution(* io.ylab.service.AuthServiceImpl.authenticateUser(..))", returning = "user")
    public void afterAuthenticateUser(JoinPoint joinPoint, User user) {
        //реализовать
        if (user!=null) {
            logger.log(Level.INFO, "Успешная аутентификация пользователя: " + user.getUserName());
        }
    }

  /*  @AfterReturning(value = "execution(* io.ylab.service.UserServiceImpl.debit(BigDecimal , long)) && args(sum, userId)", returning = "result", argNames = "sum,userId,result")
    public void afterDebit(BigDecimal sum, long userId, boolean result) {
        //реализовать
        if (result) {
            logger.log(Level.INFO, "Пользователь " + userId + "снял средства на сумму " + sum);
        }
    }

    @AfterReturning(value = "execution(* io.ylab.service.UserServiceImpl.credit(BigDecimal, long)) && args(sum, userId)", returning = "result", argNames = "sum,userId,result")
    public void afterCredit(BigDecimal sum, long userId, boolean result) {
        //реализовать
        if (result) {
            logger.log(Level.INFO, "Пользователь " + userId + "снял средства на сумму " + sum);
        }
    }*/
}

