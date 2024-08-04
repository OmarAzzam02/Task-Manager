package org.eastnets.service.task;

import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.eastnets.entity.UserType;

import java.util.List;

/**
 * Service interface for managing tasks.
 * <p>
 * This interface defines methods for performing operations related to tasks, such as
 * adding, updating, deleting, and filtering tasks. It also includes methods for
 * assigning tasks to users and retrieving tasks based on various criteria.
 * </p>
 */
public interface TaskService {

    /**
     * Adds a new task to the system.
     *
     * @param task the task to be added
     * @throws Exception if an error occurs or if the user does not have the privilege
     */
    void addTask(Task task) throws Exception;

    /**
     * Updates an existing task in the system.
     *
     * @param task the task to be updated
     */
    void updateTask(Task task);

    /**
     * Deletes a task from the system.
     *
     * @param task the task to be deleted
     */
    void deleteTask(Task task);

    /**
     * Assigns a user to a task.
     *
     * @param task the task to be assigned
     * @param user the user to be assigned to the task
     */
    void assignTask(Task task, User user);

    /**
     * Retrieves all tasks based on user type privileges.
     *
     * @param userType the type of user requesting the tasks
     * @return a list of all tasks if the user has view privileges, otherwise an empty list
     */
    List<Task> getAllTasks(UserType userType);

    /**
     * Filters tasks by name.
     *
     * @param name the name to filter tasks by
     * @param userType the type of user requesting the filtered tasks
     * @return a list of tasks matching the name if the user has view privileges, otherwise null
     */
    List<Task> filterByName(String name, UserType userType);

    /**
     * Filters tasks by status.
     *
     * @param state the status to filter tasks by ("yes" for completed, otherwise incomplete)
     * @param userType the type of user requesting the filtered tasks
     * @return a list of tasks matching the status if the user has view privileges, otherwise null
     */
    List<Task> filterByStatus(String state, UserType userType);

    /**
     * Filters tasks by priority.
     *
     * @param priority the priority to filter tasks by
     * @param userType the type of user requesting the filtered tasks
     * @return a list of tasks matching the priority if the user has view privileges, otherwise null
     */
    List<Task> filterByPriority(String priority, UserType userType);

    /**
     * Retrieves users assigned to a specific task based on task ID.
     *
     * @param taskId the ID of the task
     * @param userType the type of user requesting the users
     * @return a list of users assigned to the task if the user has view privileges, otherwise null
     */
    List<User> getUsersByTaskId(int taskId, UserType userType);

    /**
     * Retrieves tasks assigned to a specific user based on user ID.
     *
     * @param userId the ID of the user
     * @return a list of tasks assigned to the user if any are found, otherwise null
     */
    List<Task> getUsersTask(int userId);

    /**
     * Filters tasks by due date.
     *
     * @param dueDate the due date to filter tasks by (in "dd-MMM-yy" format)
     * @param userType the type of user requesting the filtered tasks
     * @return a list of tasks matching the due date if the user has view privileges, otherwise null
     */
    List<Task> filterTasksByDueDate(String dueDate, UserType userType);

    /**
     * Filters tasks by ID.
     *
     * @param id the ID to filter tasks by
     * @return a list of tasks matching the ID if any are found, otherwise an empty list
     */
    List<Task> filterById(int id);

    /**
     * Filters tasks based on a specified category and item.
     *
     * @param category the category to filter tasks by (e.g., "id", "name", "status", "priority", "duedate")
     * @param item the item to filter tasks by (e.g., ID, name, status, priority, due date)
     * @param role the role of the user requesting the filtered tasks
     * @return a list of tasks matching the filter criteria, or null if the category is unknown or an error occurs
     */
    List<Task> filterTasks(String category, String item, UserType role);
}
