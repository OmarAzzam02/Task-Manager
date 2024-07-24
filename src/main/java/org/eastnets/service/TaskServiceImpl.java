package org.eastnets.service;


import org.eastnets.repository.TaskRepositoryImpl;
import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    private final static Logger logger = LogManager.getLogger(TaskServiceImpl.class);

    private final TaskRepositoryImpl  db;




    @Autowired
   public TaskServiceImpl(TaskRepositoryImpl taskRepository){
        db = taskRepository;
    }

    @Override
    public void addTask(Task task, UserType userType) throws Exception {
        try {
            if (!userType.hasCreatePrivlage()) throw new Exception("you dont have privilege");
            db.insertTask(task);
            logger.info("TaskAdded");

        } catch (Exception e) {
            logger.error("Error inserting task", e.getMessage());
            throw new Exception("Error inserting task");
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
            List<Task> tasks;
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
    public List<Task> filterByName(String name, UserType userType) {
        logger.info("Getting Tasks By Name  ....");
        List<Task> tasks;
        try {
            if (!userType.hasViewOwnTasks()) throw new Exception("you dont have the previlage");

            tasks = db.getTasksByName(name);

            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");

            return tasks;

        } catch (Exception ex) {
            logger.error("Error filtering by name Tasks", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Task> filterByStatus(String state, UserType userType) {
        logger.info("Getting Tasks By Status  ....");
        boolean status;

        status = "yes".equalsIgnoreCase(state);

        List<Task> tasks;
        try {
            if (!userType.hasViewOwnTasks()) throw new Exception("you dont have the previlage");

            tasks = db.getTasksByStatus(status);

            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");

            return tasks;

        } catch (Exception ex) {
            logger.error("Error filtering  by Status Tasks", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Task> filterByPriority(String priority, UserType userType) {
        logger.info("Getting Tasks By Priority  ....");
        List<Task> tasks;
        try {
            if (!userType.hasViewOwnTasks()) throw new Exception("you dont have the previlage");

            tasks = db.getTasksByPriority(priority);

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
        List<User> users;

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
        List<Task> tasks;
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

    @Override
    public List<Task> filterTasksByDueDate(String dueDate, UserType userType) {
      // long date   =  Date.parse(dueDate);
        List<Task> tasks;
        try {
           if (!userType.hasViewAllTasksAndUsersPrivlage())  throw  new Exception("error");

           tasks = db.getTasksByDueDate(dueDate);
           if(tasks == null || tasks.isEmpty())
               throw new Exception("No tasks found");

           return tasks;
        }catch (Exception ex){
            logger.error("{}{}Cant get Task By date", ex.getMessage(), ex.getCause());
            return null;

        }
    }

    @Override
    public List<Task> filterById(String id , UserType userType) {
        List<Task> tasks;
        try {
        int Id = Integer.parseInt(id);
        tasks = db.getTaskById(Id);
        }catch (Exception ex){
            logger.error("{}{}Cant get Task By ID", ex.getMessage(), ex.getCause());
            return null;
        }
        return tasks;
    }

    @Override
    public List<Task> filterTasks(String category ,String  item , UserType role) {

        switch (category){

            case "id": filterById(item ,role);

            case "name":
              return   filterByName(item , role);
            case "status":
                return   filterByStatus(item , role);
                case "priority":
                    return   filterByPriority(item , role);
                    case "duedate":
                        return filterTasksByDueDate(item , role);

        }
        return null;
    }


}
