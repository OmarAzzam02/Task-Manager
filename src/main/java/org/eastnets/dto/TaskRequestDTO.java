package org.eastnets.dto;

import org.eastnets.entity.Task;
import org.eastnets.entity.UserType;

public class TaskRequestDTO {
   private Task task;
   private  UserType role;
   private String category;
    private String item;
    public TaskRequestDTO(Task task, UserType role) {
        this.task = task;
        this.role = role;
    }

    public TaskRequestDTO() {}

    public TaskRequestDTO(String item, String category, UserType role, Task task) {
        this.item = item;
        this.category = category;
        this.role = role;
        this.task = task;
    }


    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
