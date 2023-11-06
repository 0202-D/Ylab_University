package io.ylab.auditaspectstarter.aop.annotation;

import io.ylab.auditaspectstarter.AuditAspectStarterApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({AuditAspectStarterApplication.class})
public @interface EnableAuditStarter {
}
