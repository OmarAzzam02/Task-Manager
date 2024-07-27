package org.eastnets.controller;


import org.eastnets.dto.TaskRequestDTO;
import org.eastnets.entity.Task;
import org.eastnets.entity.UserType;
import org.eastnets.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController{

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }



    @PostMapping("/add-task")
    public ResponseEntity<?> addTask(@RequestBody TaskRequestDTO taskReq) {
        try {
        taskService.addTask(taskReq.getTask() , taskReq.getRole());
         return ResponseEntity.ok().body("Task added successfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/update-tasks")
    public ResponseEntity<?> updateTasks(@RequestBody TaskRequestDTO taskReq) {
        try {
            taskService.updateTask(taskReq.getTask() ,  taskReq.getRole() );
            return ResponseEntity.ok().body("Task updated successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<?> deleteTask(@RequestBody TaskRequestDTO taskReq) {

        try {
            taskService.deleteTask(taskReq.getTask() ,  taskReq.getRole());
          return ResponseEntity.ok().body("Task deleted successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchTask(@RequestBody TaskRequestDTO taskReq) {

        List<Task> tasks =  taskService.filterTasks(taskReq.getCategory(), taskReq.getItem() ,  taskReq.getRole());
        if (tasks!=null)
             return ResponseEntity.ok().body(tasks);

        return ResponseEntity.badRequest().body("No tasks found");

    }





}
