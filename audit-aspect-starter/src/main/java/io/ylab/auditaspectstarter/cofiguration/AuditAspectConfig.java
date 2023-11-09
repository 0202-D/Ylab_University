package io.ylab.auditaspectstarter.cofiguration;

import io.ylab.auditaspectstarter.AuditAspectStarterApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(AuditAspectStarterApplication.class)
public class AuditAspectConfig {
}
