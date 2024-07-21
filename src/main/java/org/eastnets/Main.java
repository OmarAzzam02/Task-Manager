package org.eastnets;

import org.eastnets.entity.*;
import org.eastnets.service.TaskServiceProvider;
import org.eastnets.service.UserServiceProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



import java.util.Date;


public class Main {

    public static void main(String[] args) {
        // Create instances of the service providers
        TaskServiceProvider taskService = new TaskServiceProvider();
        UserServiceProvider userService = new UserServiceProvider();

        // Create user types
        UserType manager = UserType.MANAGER;
        UserType senior = UserType.SENIOR;
        UserType junior = UserType.JUNIOR;

        // Create test data
        User user1 = new User(1, "AHMAD", "passworqq23", "john@example.com", senior, null);
        User user2 = new User(2, "OMAR", "password456", "jane@example.com", junior, null);

        Task task = new Task(
                1,
                "Task 3",
                "Description for Task 3",
                false,
                Priority.HIGH,
                new Date(),
                Arrays.asList(user1, user2)
        );

        // Test UserServiceProvider methods
       // testUserService(userService, user1, user2);

        // Test TaskServiceProvider methods
        testTaskService(taskService, task, manager, user1);
    }

    private static void testUserService(UserServiceProvider userService, User user1, User user2) {
        // Test signin
        User signedInUser = userService.signin("john_doe", "password123");
        System.out.println("Signed in user: " + signedInUser);

        // Test signup
        userService.signup(user1);
        System.out.println("User signed up: " + user1);

        // Test getAllUsers
        UserType manager = UserType.MANAGER;
        List<User> allUsers = userService.getAllUsers(manager);
        System.out.println("All users: " + allUsers);
    }

    private static void testTaskService(TaskServiceProvider taskService, Task task, UserType manager, User user) {
        try {
            // Test addTask
            taskService.addTask(task, manager);

            // Test updateTask
            task.setDescription("Updated Description");
            taskService.updateTask(task, manager);

            // Test deleteTask
            taskService.deleteTask(task, manager);

            // Test assignTask
            taskService.assignTask(task, user, manager);

            // Test getAllTasks
            List<Task> allTasks = taskService.getAllTasks(manager);
            System.out.println("All tasks: " + allTasks);

            // Test filterByName
            List<Task> tasksByName = taskService.filterByName(task, manager);
            System.out.println("Tasks filtered by name: " + tasksByName);

            // Test filterByStatus
            List<Task> tasksByStatus = taskService.filterByStatus(task, manager);
            System.out.println("Tasks filtered by status: " + tasksByStatus);

            // Test filterByPriority
            List<Task> tasksByPriority = taskService.filterByPriority(task, manager);
            System.out.println("Tasks filtered by priority: " + tasksByPriority);

            // Test getUsersByTaskId
            List<User> usersByTaskId = taskService.getUsersByTaskId(task.getTaskId(), manager);
            System.out.println("Users assigned to task: " + usersByTaskId);

            // Test getUsersTask
            List<Task> tasksByUser = taskService.getUsersTask(user.getUserId(), manager);
            System.out.println("Tasks assigned to user: " + tasksByUser);

        } catch (Exception e) {
            System.err.println("Error during TaskServiceProvider testing: " + e.getMessage());
        }
    }
}
