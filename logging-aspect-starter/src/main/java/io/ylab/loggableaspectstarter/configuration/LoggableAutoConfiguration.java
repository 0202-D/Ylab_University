package io.ylab.loggableaspectstarter.configuration;

import io.ylab.loggableaspectstarter.aop.aspect.LoggableAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggableAutoConfiguration {
    public LoggableAutoConfiguration(){
    }
    @Bean
    public LoggableAspect getLoggableAspect(){
        return new LoggableAspect();
    }
}
