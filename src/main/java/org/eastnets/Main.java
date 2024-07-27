package org.eastnets;

import org.eastnets.config.AppConfig;
import org.eastnets.controller.UserController;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;
import org.eastnets.repository.UserRepositoryImpl;
import org.eastnets.service.UserService;
import org.eastnets.service.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        try {
            // Initialize the application context
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

            // Retrieve all bean names defined in the context
            String[] beanNames = context.getBeanDefinitionNames();

            // Print out the names and classes of all beans
            System.out.println("All Beans in the ApplicationContext:");
            for (String beanName : beanNames) {
                System.out.println(beanName + " : " + context.getBean(beanName).getClass().getName());
            }

            // Close the context
            context.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserRepositoryImpl db  = new UserRepositoryImpl();
        UserServiceImpl userService  = new UserServiceImpl(db);
        User user1 =  userService.signin("Azzam" , "password1!");
        System.out.println(user1.getUsername());

    }
}
