package org.eastnets.service;


import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.List;

public interface TaskService {
    void addTask(Task task ) throws Exception;
    void updateTask(Task task );
    void deleteTask(Task task);
    void assignTask(Task task, User user);
    List<Task> getAllTasks(UserType userType);
    List<Task> filterByName(String name , UserType userType);
    List<Task>filterByStatus(String state , UserType userType);
    List<Task> filterByPriority(String priority , UserType userType);
    List<User> getUsersByTaskId(int taskId , UserType userType);
    List<Task> getUsersTask(int userId , UserType userType);
    List<Task> filterTasksByDueDate(String dueDate, UserType userType);
    List<Task> filterTasks(String category ,String item , UserType role);
    List<Task> filterById(String item);
}
