package org.eastnets;

import org.eastnets.config.AppConfig;
import org.eastnets.service.TaskServiceProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        try {
            // Initialize the application context
            ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

            // Print all bean names
            String[] beanNames = context.getBeanDefinitionNames();
            System.out.println("All Beans in the ApplicationContext:");
            for (String beanName : beanNames) {
                System.out.println(beanName + " : " + context.getBean(beanName).getClass().getName());
            }

            // Retrieve a specific bean (if needed)


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
