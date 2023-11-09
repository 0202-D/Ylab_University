package io.ylab.auditaspectstarter.aop.aspect;

import org.aspectj.lang.JoinPoint;

import java.math.BigDecimal;

public interface AspectService {
     void logBalanceAction(JoinPoint joinPoint, long userId);
     void addDebitAction(BigDecimal sum, long userId);
     void addCreditAction(BigDecimal sum, long userId);
     void addHistoryAction(JoinPoint joinPoint, long userId);
}
