package com.coldrain;

import com.coldrain.test.TestSpringService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class FirstSpringApp {

    private TestSpringService testSpringService;

    public static void main(String[] args){
        ApplicationContext context = new AnnotationConfigApplicationContext(FirstSpringApp.class);
        TestSpringService testSpringService = context.getBean(TestSpringService.class);
        testSpringService.testNcmInside();
    }
}
