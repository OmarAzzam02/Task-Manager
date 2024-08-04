package org.eastnets.dao;

import org.eastnets.entity.Priority;
import org.eastnets.entity.Task;
import org.eastnets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Data Access Object (DAO) interface for performing CRUD operations on {@link Task} entities.
 * <p>
 * This interface extends {@link JpaRepository} to provide standard CRUD operations and custom queries
 * for {@link Task} entities. It includes methods to find tasks by various attributes and relationships.
 */
@Repository
public interface TaskDAO extends JpaRepository<Task, Integer> {

    /**
     * Finds tasks by their name.
     *
     * @param name the name of the task.
     * @return a list of tasks with the specified name.
     */
    List<Task> findByName(@Param("name") String name);

    /**
     * Finds tasks by their ID.
     *
     * @param taskId the ID of the task.
     * @return a list of tasks with the specified ID.
     */
    List<Task> findById(int taskId);

    /**
     * Finds tasks by their status.
     *
     * @param status the status of the tasks.
     * @return a list of tasks with the specified status.
     */
    List<Task> findByStatus(boolean status);

    /**
     * Finds tasks by their priority.
     *
     * @param priority the priority of the tasks.
     * @return a list of tasks with the specified priority.
     */
    List<Task> findByPriority(Priority priority);

    /**
     * Finds tasks by their due date.
     *
     * @param dueDate the due date of the tasks.
     * @return a list of tasks with the specified due date.
     */
    List<Task> findByDueDate(Date dueDate);

    /**
     * Finds tasks assigned to a user by the user's ID.
     *
     * @param userId the ID of the user.
     * @return a list of tasks assigned to the user with the specified ID.
     */
    @Query("SELECT t FROM Task t JOIN t.assignedTo u WHERE u.userId = :userId")
    List<Task> findTaskByUserId(@Param("userId") int userId);

    /**
     * Finds users assigned to a task by the task's ID.
     *
     * @param taskId the ID of the task.
     * @return a list of users assigned to the task with the specified ID.
     */
    @Query("SELECT u FROM User u JOIN u.tasksAssigned t WHERE t.taskId = :taskId")
    List<User> findUsersByTaskId(@Param("taskId") int taskId);
}
