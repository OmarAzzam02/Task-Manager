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

@Repository
public interface TaskDAO extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE t.name = :name")
    List<Task> findByName(@Param("name") String name);

    @Query("SELECT t FROM Task t WHERE t.status = :status")
    List<Task> findByStatus(@Param("status") boolean status);

    @Query("SELECT t FROM Task t WHERE t.priority = :priority")
    List<Task> findByPriority(@Param("priority") Priority priority);

    @Query("SELECT t FROM Task t WHERE t.dueDate = :dueDate")
    List<Task> findByDueDate(@Param("dueDate") Date dueDate);

    @Query("SELECT t FROM Task t JOIN t.assignedTo u WHERE u.userId = :userId")
    List<Task> findTaskByUserId(@Param("userId") int userId);

    @Query("SELECT u FROM User u JOIN u.tasksAssigned t WHERE t.taskId = :taskId")
    List<User> findUsersByTaskId(@Param("taskId") int taskId);
}
