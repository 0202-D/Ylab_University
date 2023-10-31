package io.ylab.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class SwaggerUIConfiguration extends WebMvcConfigurerAdapter {
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.
               addResourceHandler("/swagger-ui/**")
               .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
               .resourceChain(false);
   }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:" + "/swagger-ui/index.html");
    }

}
