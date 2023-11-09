package io.ylab;

import io.ylab.auditaspectstarter.aop.annotation.EnableAuditStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAuditStarter
public class Ylab_UniversityApplication {
    public static void main(String[] args) {
        SpringApplication.run(Ylab_UniversityApplication.class, args);
    }
}
