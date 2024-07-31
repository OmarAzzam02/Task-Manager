package org.eastnets.service;


import org.eastnets.entity.Priority;
import org.eastnets.dao.TaskDAO;

import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TaskServiceImpl implements TaskService {
    private final static Logger logger = LogManager.getLogger(TaskServiceImpl.class);


    private final TaskDAO db;



   public TaskServiceImpl(TaskDAO taskRepository){
        db = taskRepository;
    }

    @Override
    @Transactional
    public void addTask(Task task) throws Exception {
        try {
            if (!task.getModifiedBy().getUserType().hasCreatePrivlage()) throw new Exception("you dont have privilege");


            // Save the task
            db.save(task);
            logger.info("Task added");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error inserting task", e);
            throw new Exception("Error inserting task");
        }

    }

    @Override
    public void updateTask(Task task) {

        try {
            if (!task.getModifiedBy().getUserType().hasCreatePrivlage()) throw new Exception("you dont have privilege");
            //TODO
            db.save(task);
            logger.info("TaskUpdated");
        } catch (Exception e) {
            logger.error("Error updating  task", e.getMessage());
        }

    }



    @Override
    public void deleteTask(Task task) {

        try {
            if (!task.getModifiedBy().getUserType().hasCreatePrivlage()) throw new Exception("you dont have privilege");
            db.delete(task);
            logger.info("Task Deleted");
        } catch (Exception e) {
            logger.error("Error Deleting  task", e.getMessage());
        }
    }

    @Override
    public void assignTask(Task task, User user) {
        try {
            if (!task.getModifiedBy().getUserType().hasAssignPrivlage()) throw new Exception("you dont have the privlage");
           // db.assignTask(task, user);
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

            tasks = db.findAll();

            if (tasks.isEmpty())
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

            tasks = db.findByName(name);

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
            tasks = db.findByStatus(status);

            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");

            return tasks;

        } catch (Exception ex) {
            logger.error("Error filtering  by Status Tasks", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Task> filterByPriority(String priorityStr, UserType userType) {
        logger.info("Getting Tasks By Priority  ....");
        List<Task> tasks;
        try {
            if (!userType.hasViewOwnTasks()) throw new Exception("you dont have the previlage");
            Priority priority = Priority.valueOf(priorityStr);
            tasks = db.findByPriority(priority);
            logger.info(  " tasks from db extracted  {} "   , tasks.size());
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

            users = db.findUsersByTaskId(taskId);

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

            tasks = db.findTaskByUserId(userId);
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        List<Task> tasks;
        try {
            Date due = formatter.parse(dueDate);
           if (!userType.hasViewAllTasksAndUsersPrivlage())  throw  new Exception("error");

           tasks = db.findByDueDate(due);
           if(tasks == null || tasks.isEmpty())
               throw new Exception("No tasks found");

           return tasks;
        }catch (Exception ex){
            logger.error("{}{}Cant get Task By date", ex.getMessage(), ex.getCause());
            return null;

        }
    }

    @Override
    public List<Task> filterById(String id) {
        Optional<Task> tasks;
        try {
        Integer Id = Integer.parseInt(id);
        logger.info("trying to get the task with id {}" , Id );
        tasks = db.findById(Id);
        if (!tasks.isPresent())
             throw new Exception("No task found");

        return Collections.singletonList(tasks.get());

        }catch (Exception ex){
            logger.error("{}{}Cant get Task By ID", ex.getMessage(), ex.getCause());
            return Collections.emptyList();
        }

    }

    @Override
    public List<Task> filterTasks(String category ,String  item , UserType role) {


        switch (category){
            case "id": return filterById(item);
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
