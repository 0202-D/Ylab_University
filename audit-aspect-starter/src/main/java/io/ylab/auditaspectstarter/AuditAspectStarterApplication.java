package io.ylab.auditaspectstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "io.ylab")
public class AuditAspectStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuditAspectStarterApplication.class, args);
    }

}
