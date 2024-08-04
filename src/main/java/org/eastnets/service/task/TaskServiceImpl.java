package org.eastnets.service.task;

import lombok.extern.log4j.Log4j2;
import org.eastnets.entity.Priority;
import org.eastnets.dao.TaskDAO;
import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the TaskService interface.
 * <p>
 * This service handles business logic for managing tasks, including adding, updating,
 * deleting, and filtering tasks. It also includes methods for assigning tasks to users
 * and retrieving tasks based on various criteria.
 * </p>
 */
@Service
@Log4j2
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDAO db;

    /**
     * Adds a new task to the system.
     * <p>
     * The task can only be added if the user modifying the task has the privilege to
     * create tasks.
     * </p>
     *
     * @param task the task to be added
     * @throws Exception if the user does not have the privilege to create tasks
     */
    @Override
    @Transactional
    public void addTask(Task task) throws Exception {
        try {
            if (!task.getModifiedBy().getUserType().hasCreatePrivlage())
                throw new Exception("You don't have privilege");
            db.save(task);
            log.info("Task added");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error inserting task", e);
            throw new Exception("Error inserting task");
        }
    }

    /**
     * Updates an existing task in the system.
     *
     * @param task the task to be updated
     */
    @Override
    @Transactional
    public void updateTask(Task task) {
        try {
            log.info(task.getTaskId());
            db.save(task);
            log.info("Task updated");
        } catch (Exception e) {
            log.error(e, new Exception("Error in updating task"));
        }
    }

    /**
     * Deletes a task from the system.
     * <p>
     * The task can only be deleted if the user modifying the task has the privilege to
     * delete tasks.
     * </p>
     *
     * @param task the task to be deleted
     * @throws Exception if the user does not have the privilege to delete tasks
     */
    @Override
    @Transactional
    public void deleteTask(Task task) {
        try {
            if (!task.getModifiedBy().getUserType().hasCreatePrivlage())
                throw new Exception("You don't have privilege");
            db.delete(task);
            log.info("Task deleted");
        } catch (Exception e) {
            log.error("Error deleting task", e.getMessage());
        }
    }

    /**
     * Assigns a user to a task.
     * <p>
     * The user can only be assigned if the user modifying the task has the privilege to
     * assign tasks, and the user is not already assigned to the task.
     * </p>
     *
     * @param task the task to be assigned
     * @param user the user to be assigned to the task
     * @throws Exception if the user does not have the privilege to assign tasks or is already assigned
     */
    @Override
    public void assignTask(Task task, User user) {
        try {
            if (!task.getModifiedBy().getUserType().hasAssignPrivlage())
                throw new Exception("You don't have the privilege");
            if (task.getAssignedTo().contains(user))
                throw new Exception("User already assigned to this task");
            task.addAssignedTo(user);
            db.save(task);
            log.info("Task assigned");
        } catch (Exception ex) {
            log.error("Error assigning task", ex.getMessage());
        }
    }

    /**
     * Retrieves all tasks based on user type privileges.
     *
     * @param userType the type of user requesting the tasks
     * @return a list of all tasks if the user has view privileges, otherwise an empty list
     */
    @Override
    public List<Task> getAllTasks(UserType userType) {
        List<Task> tasks;
        try {
            log.info("Getting tasks...");
            if (!userType.hasViewAllTasksAndUsersPrivlage())
                throw new Exception("You don't have privilege");
            tasks = db.findAll();
            if (tasks.isEmpty())
                throw new Exception("No tasks found");
        } catch (Exception ex) {
            log.error("Error getting tasks", ex.getMessage());
            return Collections.emptyList();
        }
        return tasks;
    }

    /**
     * Filters tasks by name.
     *
     * @param name     the name to filter tasks by
     * @param userType the type of user requesting the filtered tasks
     * @return a list of tasks matching the name if the user has view privileges, otherwise null
     */
    @Override
    public List<Task> filterByName(String name, UserType userType) {
        log.info("Getting tasks by name...");
        List<Task> tasks;
        try {
            if (!userType.hasViewOwnTasks())
                throw new Exception("You don't have the privilege");
            tasks = db.findByName(name);
            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");
        } catch (Exception ex) {
            log.error("Error filtering by name tasks", ex.getMessage());
            return null;
        }
        return tasks;
    }

    /**
     * Filters tasks by status.
     *
     * @param state    the status to filter tasks by ("yes" for completed, otherwise incomplete)
     * @param userType the type of user requesting the filtered tasks
     * @return a list of tasks matching the status if the user has view privileges, otherwise null
     */
    @Override
    public List<Task> filterByStatus(String state, UserType userType) {
        log.info("Getting tasks by status...");
        boolean status = "yes".equalsIgnoreCase(state);
        List<Task> tasks;
        try {
            if (!userType.hasViewOwnTasks())
                throw new Exception("You don't have the privilege");
            tasks = db.findByStatus(status);
            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");
        } catch (Exception ex) {
            log.error("Error filtering by status tasks", ex.getMessage());
            return null;
        }
        return tasks;
    }

    /**
     * Filters tasks by priority.
     *
     * @param priorityStr the priority to filter tasks by
     * @param userType    the type of user requesting the filtered tasks
     * @return a list of tasks matching the priority if the user has view privileges, otherwise null
     */
    @Override
    public List<Task> filterByPriority(String priorityStr, UserType userType) {
        log.info("Getting tasks by priority...");
        List<Task> tasks;
        try {
            if (!userType.hasViewOwnTasks())
                throw new Exception("You don't have the privilege");
            Priority priority = Priority.valueOf(priorityStr);
            tasks = db.findByPriority(priority);
            log.info("Tasks from db extracted: {}", tasks.size());
            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");
        } catch (Exception ex) {
            log.error("Error filtering by priority tasks", ex.getMessage());
            return null;
        }
        return tasks;
    }

    /**
     * Retrieves users assigned to a specific task based on task ID.
     *
     * @param taskId   the ID of the task
     * @param userType the type of user requesting the users
     * @return a list of users assigned to the task if the user has view privileges, otherwise null
     */
    @Override
    public List<User> getUsersByTaskId(int taskId, UserType userType) {
        List<User> users;
        try {
            log.info("Getting users by task ID...");
            if (!userType.hasViewAllTasksAndUsersPrivlage())
                throw new Exception("No privilege");
            users = db.findUsersByTaskId(taskId);
            if (users == null || users.isEmpty())
                throw new Exception("No users found");
            log.info("Users retrieved count: {}", users.size());
        } catch (Exception ex) {
            log.error("Error getting users by task ID", ex.getMessage());
            return null;
        }
        return users;
    }

    /**
     * Retrieves tasks assigned to a specific user based on user ID.
     *
     * @param userId the ID of the user
     * @return a list of tasks assigned to the user if any are found, otherwise null
     */
    @Override
    public List<Task> getUsersTask(int userId) {
        List<Task> tasks;
        try {
            tasks = db.findTaskByUserId(userId);
            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");
            log.info("Tasks retrieved count: {}", tasks.size());
        } catch (Exception ex) {
            log.error("Error getting user's tasks", ex.getMessage());
            return null;
        }
        return tasks;
    }

    /**
     * Filters tasks by due date.
     *
     * @param dueDate  the due date to filter tasks by (in "dd-MMM-yy" format)
     * @param userType the type of user requesting the filtered tasks
     * @return a list of tasks matching the due date if the user has view privileges, otherwise null
     */
    @Override
    public List<Task> filterTasksByDueDate(String dueDate, UserType userType) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
        List<Task> tasks;
        try {
            Date due = formatter.parse(dueDate);
            if (!userType.hasViewAllTasksAndUsersPrivlage())
                throw new Exception("Error");
            tasks = db.findByDueDate(due);
            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");
        } catch (Exception ex) {
            log.error("{} Cant get task by date", ex.getMessage(), ex.getCause());
            return null;
        }
        return tasks;
    }

    /**
     * Filters tasks by ID.
     *
     * @param taskId the ID to filter tasks by
     * @return a list of tasks matching the ID if any are found, otherwise an empty list
     */
    @Override
    public List<Task> filterById(int taskId) {
        try {
            log.info("Getting tasks by ID: {}", taskId);
            List<Task> tasks = db.findById(taskId);
            if (!tasks.isEmpty())
                return tasks;
        } catch (Exception e) {
            log.error("Error filtering by ID", e);
        }
        return Collections.emptyList();
    }

    /**
     * Filters tasks based on a specified category and item.
     *
     * @param category the category to filter tasks by (e.g., "id", "name", "status", "priority", "duedate")
     * @param item     the item to filter tasks by (e.g., ID, name, status, priority, due date)
     * @param role     the role of the user requesting the filtered tasks
     * @return a list of tasks matching the filter criteria, or null if the category is unknown or an error occurs
     */
    @Override
    public List<Task> filterTasks(String category, String item, UserType role) {
        switch (category) {
            case "id":
                return filterById(Integer.parseInt(item));
            case "name":
                return filterByName(item, role);
            case "status":
                return filterByStatus(item, role);
            case "priority":
                return filterByPriority(item, role);
            case "duedate":
                return filterTasksByDueDate(item, role);
        }
        return null;
    }
}
