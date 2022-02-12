package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan(basePackages = {},excludeFilters = @ComponentScan.Filter(RestController.class))
public class RootConfig {
    //place here shared beans and so on
    //it's root context
    //component scan mentioned second time
}
