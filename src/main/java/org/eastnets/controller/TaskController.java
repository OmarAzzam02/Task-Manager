package org.eastnets.controller;

import lombok.extern.log4j.Log4j2;
import org.eastnets.dto.task.TaskRequestDTO;
import org.eastnets.dto.task.TaskSearchDTO;
import org.eastnets.dto.user.UserInfoDTO;
import org.eastnets.entity.Task;
import org.eastnets.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling task-related operations.
 * <p>
 * This controller provides endpoints for adding, updating, deleting, searching, and retrieving tasks.
 * </p>
 */
@RestController
@RequestMapping("/")
@Log4j2
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Endpoint to add a new task.
     * <p>
     * Accepts a {@link TaskRequestDTO} containing task details, creates a new {@link Task}, and saves it.
     * </p>
     *
     * @param taskReq the {@link TaskRequestDTO} containing task details
     * @return a {@link ResponseEntity} with a success or error message
     */
    @PostMapping("/add-task")
    public ResponseEntity<?> addTask(@RequestBody TaskRequestDTO taskReq) {
        try {
            log.info("Adding task {}", taskReq);

            if (taskReq.getName() == null) {
                throw new Exception("Task name is null");
            }

            taskService.addTask(new Task(taskReq.getName(), taskReq.getDescription(), taskReq.getStatus(), taskReq.getPriority(), taskReq.getDueDate(), taskReq.getAssignedTo(), taskReq.getModifiedBy()));
            return ResponseEntity.ok().body("Task added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage() + " " + e.getCause());
        }
    }

    /**
     * Endpoint to update an existing task.
     * <p>
     * Accepts a {@link TaskRequestDTO} containing updated task details, creates an updated {@link Task}, and saves it.
     * </p>
     *
     * @param taskReq the {@link TaskRequestDTO} containing updated task details
     * @return a {@link ResponseEntity} with a success or error message
     */
    @PostMapping("/update-tasks")
    public ResponseEntity<?> updateTasks(@RequestBody TaskRequestDTO taskReq) {
        try {
            taskService.updateTask(new Task(taskReq.getTaskId(), taskReq.getName(), taskReq.getDescription(), taskReq.getStatus(), taskReq.getPriority(), taskReq.getDueDate(), taskReq.getAssignedTo(), taskReq.getModifiedBy()));
            return ResponseEntity.ok().body("Task updated successfully");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Endpoint to delete an existing task.
     * <p>
     * Accepts a {@link TaskRequestDTO} containing the task details to be deleted, creates a {@link Task}, and deletes it.
     * </p>
     *
     * @param taskReq the {@link TaskRequestDTO} containing task details to be deleted
     * @return a {@link ResponseEntity} with a success or error message
     */
    @DeleteMapping("/delete-task")
    public ResponseEntity<?> deleteTask(@RequestBody TaskRequestDTO taskReq) {
        try {
            taskService.deleteTask(new Task(taskReq.getTaskId(), taskReq.getName(), taskReq.getDescription(), taskReq.getStatus(), taskReq.getPriority(), taskReq.getDueDate(), taskReq.getAssignedTo(), taskReq.getModifiedBy()));
            return ResponseEntity.ok().body("Task deleted successfully");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    /**
     * Endpoint to search for tasks based on category and item.
     * <p>
     * Accepts a {@link TaskSearchDTO} containing search criteria and returns a list of tasks matching the criteria.
     * </p>
     *
     * @param taskReq the {@link TaskSearchDTO} containing search criteria
     * @return a {@link ResponseEntity} with the number of tasks found or an error message
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchTask(@RequestBody TaskSearchDTO taskReq) {
        List<Task> tasks = taskService.filterTasks(taskReq.getCategoryToSearch(), taskReq.getItemToSearch(), taskReq.getRole().getUserType());
        if (tasks != null)
            return ResponseEntity.ok().body(tasks.size());

        return ResponseEntity.badRequest().body("No tasks found");
    }

    /**
     * Endpoint to retrieve tasks assigned to a specific user.
     * <p>
     * Accepts a {@link UserInfoDTO} containing user information and returns the list of tasks assigned to that user.
     * </p>
     *
     * @param user the {@link UserInfoDTO} containing user information
     * @return a {@link ResponseEntity} with the number of tasks found or an error message
     */
    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks(UserInfoDTO user) {
        try {
            log.info("Getting user's tasks");
            List<Task> tasks = taskService.getUsersTask(user.getUserId());

            if (tasks == null || tasks.isEmpty())
                throw new Exception("No tasks found");

            return ResponseEntity.ok().body("Tasks found: " + tasks.size());
        } catch (Exception ex) {
            log.error(ex);
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
