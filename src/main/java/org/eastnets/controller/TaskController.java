package org.eastnets.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eastnets.dto.task.TaskRequestDTO;
import org.eastnets.dto.task.TaskSearchDTO;
import org.eastnets.entity.Task;
import org.eastnets.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController{
    private final static Logger logger = LogManager.getLogger(TaskController.class);
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add-task")
    public ResponseEntity<?> addTask(@RequestBody TaskRequestDTO taskReq) {
        try{

            logger.info(" Adding task {} ",taskReq);

            if(taskReq.getName() == null){
                throw new Exception("Task is null");
            }

        taskService.addTask(new Task(taskReq.getName()  , taskReq.getDescription() , taskReq.getStatus(), taskReq.getPriority(),taskReq.getDueDate() , taskReq.getAssignedTo() , taskReq.getModifiedBy() ));
         return ResponseEntity.ok().body("Task added successfully");
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage() + " "  + e.getCause());
        }
    }

    @PostMapping("/update-tasks")
    public ResponseEntity<?> updateTasks(@RequestBody TaskRequestDTO taskReq) {
        try {
            taskService.updateTask(new Task(taskReq.getName()  , taskReq.getDescription() , taskReq.getStatus(), taskReq.getPriority(),taskReq.getDueDate() , taskReq.getAssignedTo() , taskReq.getModifiedBy() )  );
            return ResponseEntity.ok().body("Task updated successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete-task")
    public ResponseEntity<?> deleteTask(@RequestBody TaskRequestDTO taskReq) {

        try {
            taskService.deleteTask(new Task(taskReq.getName()  , taskReq.getDescription() , taskReq.getStatus(), taskReq.getPriority(),taskReq.getDueDate() , taskReq.getAssignedTo() , taskReq.getModifiedBy() ));
          return ResponseEntity.ok().body("Task deleted successfully");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

   @GetMapping("/search")
    public ResponseEntity<?> searchTask(@RequestBody TaskSearchDTO taskReq) {

       List<Task> tasks =  taskService.filterTasks(taskReq.getCategoryToSearch() , taskReq.getItemToSearch() , taskReq.getRole().getUserType());
        if (tasks!=null)
            return ResponseEntity.ok().body(" tasks found ");

        return ResponseEntity.badRequest().body("No tasks found");

    }




}
