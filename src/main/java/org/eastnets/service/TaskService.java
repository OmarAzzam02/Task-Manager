package org.eastnets.service;


import org.eastnets.entity.Task;
import org.eastnets.entity.UserType;

public interface TaskService {
    void addTask(Task task, UserType userType) throws Exception;
    void updateTask(Task task , UserType userType);
    void deleteTask(Task task , UserType userType);
    void assignTask(Task task, int userId ,UserType userType);
     void addAssignedTo(Task task,  int assignedToId , UserType userType);
     void removeAssignedTo(Task task, int assignedToId , UserType userType);



}
