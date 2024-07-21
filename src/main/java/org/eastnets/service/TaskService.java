package org.eastnets.service;


import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.List;

public interface TaskService {
    void addTask(Task task, UserType userType) throws Exception;
    void updateTask(Task task , UserType userType);
    void deleteTask(Task task , UserType userType);
    void assignTask(Task task, User use,UserType userType);
    List<Task> getAllTasks(UserType userType);
    List<Task> filterByName(Task task , UserType userType);
    List<Task>filterByStatus(Task task , UserType userType);
    List<Task> filterByPriority(Task task , UserType userType);
    List<User> getUsersByTaskId(int taskId , UserType userType);
    List<Task> getUsersTask(int userId , UserType userType);





}
