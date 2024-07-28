package org.eastnets;

import org.eastnets.config.AppConfig;
import org.eastnets.controller.UserController;
import org.eastnets.entity.Priority;
import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;
import org.eastnets.repository.TaskRepositoryImpl;
import org.eastnets.repository.UserRepositoryImpl;
import org.eastnets.service.TaskServiceImpl;
import org.eastnets.service.UserService;
import org.eastnets.service.UserServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

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
        TaskRepositoryImpl db2 = new TaskRepositoryImpl();
        UserServiceImpl userService  = new UserServiceImpl(db);
        TaskServiceImpl taskService = new TaskServiceImpl(db2);
        User user1 =  userService.signin("user1" , "password1");

        Task task = new Task();
        task.setName("check modidfied if works");
        task.setDescription("Test description");
        task.setStatus(false);
        task.setPriority(Priority.HIGH);
        task.setDueDate(new Date());
        task.setModifiedBy(user1);
        try {
        taskService.addTask(task);

        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println(task.getModifiedBy().getUsername());


    }
}
