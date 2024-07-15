package org.eastnets.databaseservice;


import org.eastnets.entity.Task;
import org.eastnets.entity.User;

import java.util.List;

public interface DataBaseService{
    List<User> getAllUsersFromDataBase();
    void addUser(User user);
    User login(String username , String password);
    void insertTask(Task task);
    void updateTask(Task task);
    void deleteTask(Task task);

    List<Task> getAllTasksFromDB();
    Task getTaskById(int id);
    List<Task> getTasksByName(String name);
    List<Task> getTasksByStatus(String status);
    List<Task> getTasksByPriority(String priority);



}
