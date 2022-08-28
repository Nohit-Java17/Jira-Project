package com.nohit.jira_project.config;

import org.springframework.boot.web.server.*;
import org.springframework.boot.web.servlet.server.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

import com.nohit.jira_project.constant.*;

import static com.nohit.jira_project.constant.AttributeConstant.*;
import static org.springframework.http.HttpStatus.*;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(ViewConstant.NOT_FOUND_VIEW).setViewName(AttributeConstant.FORWARD_PREFIX + ViewConstant.INDEX_VIEW);
    }

    // Add error page to container
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(NOT_FOUND, ViewConstant.NOT_FOUND_VIEW));
        };
    }
    
}
