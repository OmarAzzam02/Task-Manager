package org.eastnets.service;

import com.sun.org.apache.bcel.internal.generic.ATHROW;
import org.eastnets.databaseservice.DataBaseProvider;
import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TaskServiceProvider implements TaskService {
    private final static Logger logger = LogManager.getLogger(TaskServiceProvider.class);
    DataBaseProvider db = new DataBaseProvider();



    @Override
    public void addTask(Task task, UserType userType) throws Exception {
        try {
            if (!userType.hasCreatePrivlage()) throw new Exception("you dont have privilege");
            db.insertTask(task);
            logger.info("TaskAdded");

        } catch (Exception e) {
            logger.error("Error inserting task", e.getMessage());
        }

    }

    @Override
    public void updateTask(Task task, UserType userType) {

        try {
            if (!userType.hasCreatePrivlage()) throw new Exception("you dont have privilege");
            db.updateTask(task);
            logger.info("TaskUpdated");
        } catch (Exception e) {
            logger.error("Error updating  task", e.getMessage());
        }

    }



    @Override
    public void deleteTask(Task task, UserType userType) {

        try {
            if (!userType.hasCreatePrivlage()) throw new Exception("you dont have privilege");
            db.deleteTask(task);
            logger.info("Task Deleted");
        } catch (Exception e) {
            logger.error("Error Deleting  task", e.getMessage());
        }
    }

    @Override
    public void assignTask(Task task, User user, UserType userType) {
        try {
            if (!userType.hasAssignPrivlage()) throw new Exception("you dont have the privlage");
            db.assignTask(task, user);
            logger.info("TaskAssigned");
        } catch (Exception ex) {
            logger.error("Error assigning task", ex.getMessage());
        }


    }

    @Override
    public List<Task> getAllTasks(UserType userType) {
        try {
            logger.info("Getting Tasks.....");
            List<Task> tasks = new ArrayList<>();
            if (!userType.hasViewAllTasksAndUsersPrivlage()) throw new Exception("You dont have  previlage");

            tasks = db.getAllTasksFromDB();

            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");


            return tasks;

        } catch (Exception ex) {

            logger.error("Error getting Tasks", ex.getMessage());
            return Collections.emptyList();

        }
    }

    @Override
    public List<Task> filterByName(Task task, UserType userType) {
        logger.info("Getting Tasks By Name  ....");
        List<Task> tasks = new ArrayList<>();
        try {
            if (!userType.hasViewOwnTasks()) throw new Exception("you dont have the previlage");

            tasks = db.getTasksByName(task.getName());

            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");

            return tasks;

        } catch (Exception ex) {
            logger.error("Error filterin by name Tasks", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Task> filterByStatus(Task task, UserType userType) {
        logger.info("Getting Tasks By Status  ....");
        List<Task> tasks = new ArrayList<>();
        try {
            if (!userType.hasViewOwnTasks()) throw new Exception("you dont have the previlage");

            tasks = db.getTasksByStatus(task.getStatus());

            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");

            return tasks;

        } catch (Exception ex) {
            logger.error("Error filtering  by Status Tasks", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Task> filterByPriority(Task task, UserType userType) {
        logger.info("Getting Tasks By Priority  ....");
        List<Task> tasks = new ArrayList<>();
        try {
            if (!userType.hasViewOwnTasks()) throw new Exception("you dont have the previlage");

            tasks = db.getTasksByPriority(task.getPriority().name());

            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");

            return tasks;

        } catch (Exception ex) {
            logger.error("Error filtering  by Priority Tasks", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<User> getUsersByTaskId(int taskId, UserType userType) {
        List<User> users = new ArrayList<>();

        try {
            logger.info("Getting Users By Task Id....");
            if (!userType.hasViewAllTasksAndUsersPrivlage()) throw new Exception("no previlage");

            users = db.getUsersByTaskId(taskId);

            if(users == null || users.isEmpty())
                throw new Exception("No users found");

            logger.info(" users retrieved count: {}", users.size());
            return users;


        } catch (Exception ex) {
            logger.error("Error getting users by Task ID", ex.getMessage());
            return null;

        }

    }

    @Override
    public List<Task> getUsersTask(int userId, UserType userType) {
        List<Task> tasks = new ArrayList<>();
        try {

            if(!userType.hasViewAllTasksAndUsersPrivlage()) throw new Exception("no previlage");

            tasks = db.getTasksByUserId(userId);
            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");

            logger.info(" tasks retrieved count: {}", tasks.size());
            return tasks;

        }catch (Exception ex){
            logger.error("Error getting users task", ex.getMessage());
            return null;
        }
    }


}
