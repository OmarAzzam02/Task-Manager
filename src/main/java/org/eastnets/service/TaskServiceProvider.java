package org.eastnets.service;

import org.eastnets.databaseservice.DataBaseProvider;
import org.eastnets.entity.Task;
import org.eastnets.entity.UserType;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskServiceProvider implements TaskService {
    private final static Logger logger = LogManager.getLogger(TaskServiceProvider.class);
    DataBaseProvider db = new DataBaseProvider();

    @Override
    public void addTask(Task task, UserType userType) throws Exception {
        logger.debug("addTask called with task: {} and userType: {}", task, userType);
        if (!userType.hasCreatePrivlage()) {
            logger.error("no previlage", new Exception("User Does not have the previlage"));

        }

        db.insertTask(task);
        logger.debug("addTask called with task: {} and userType: {}", task, userType);
    }

    @Override
    public void updateTask(Task task, UserType userType) {

        try {
            logger.debug("updateTask called with task: {} and userType: {}", task, userType);
            if (!userType.hasEditPrivlage())
                logger.error("User Does not have the previlage", new Exception("you dont have the previlage"));
            db.updateTask(task);
            logger.debug(" DB updateTask method called with task: {} and userType: {}", task, userType);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void deleteTask(Task task, UserType userType) {
        try {
            logger.debug("deleteTask called with task: {} and userType: {}", task, userType);
            if (userType.hasDeletePrivlage()) {
                logger.error("no previlage", new Exception("User Does not have the previlage"));
            }
            db.deleteTask(task);
            logger.debug(" DB deleteTask called with task: {} and userType: {}", task, userType);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void assignTask(Task task, int userId, UserType userType) {
        logger.debug("assignTask called with task: {} and userType: {}", task, userType);
        if (!userType.hasAssignPrivlage())
            logger.error("User does not have the previlage", new Exception("you dont have the previlage"));

        db.assignTask(task.getTaskId(), task.getAssignedTo());
        logger.debug(" DB assignTask called with task: {} and userType: {}", task, userType);

    }

    @Override
    public void addAssignedTo(Task task, int assignedToId, UserType userType) {
        try {
            logger.debug("addAssignedTo called with task: {} and userType: {}", task, userType);
            if (!userType.hasAssignPrivlage())
                logger.error("User does not have the previlage", new Exception("User does not have the previlage"));

            List<Integer> assigns = task.getAssignedTo();
            assigns.add(assignedToId);
            task.setAssignedTo(assigns);
            db.updateTask(task);
            logger.debug(" DB Update from add AssignedTo called with task: {} and userType: {}", task, userType);

        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + e.getCause());
        }
    }

    @Override
    public void removeAssignedTo(Task task, int assignedToId, UserType userType) {

        try {
            if (!userType.hasAssignPrivlage())
                logger.error("User does not have the previlage", new Exception("User does not have the previlage"));
            List<Integer> assigns = task.getAssignedTo();
            assigns.remove(assignedToId);
            task.setAssignedTo(assigns);
            db.updateTask(task);
            logger.debug(" DB Update from remove AssignedTo called with task: {} and userType: {}", task, userType);


        } catch (Exception ex) {
            System.out.println(ex.getMessage() + " " + ex.getCause());
        }

    }


}
