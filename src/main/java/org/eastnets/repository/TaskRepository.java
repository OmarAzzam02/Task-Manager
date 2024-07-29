package org.eastnets.repository;

import org.eastnets.entity.Priority;
import org.eastnets.entity.Task;
import org.eastnets.entity.User;

import java.util.Date;
import java.util.List;

public interface TaskRepository {
    void insertTask(Task task);
    void updateTask(Task task);
    void deleteTask(Task task);
    List<Task> getAllTasksFromDB();
    List<Task> getTasksByName(String name);
    List<Task> getTasksByStatus(boolean status);
    List<Task> getTasksByPriority(Priority priority);
    List<User> getUsersByTaskId(int taskId);
    List<Task> getTasksByUserId(int userId);
    List<Task> getTasksByDueDate(Date dueDate);
    List<Task> getTaskById(int id);


}
