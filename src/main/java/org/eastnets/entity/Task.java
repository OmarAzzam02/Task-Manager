package org.eastnets.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class Task {
    private final static Logger logger = LogManager.getLogger(Task.class);

    private int taskId;
    private String name;
    private String description;
    private boolean status;
    private Priority priority;
    private Date dueDate;
    private List<Integer> assignedTo;


  public Task(int taskId, String name, String description, boolean status, Priority priority, Date dueDate , List<Integer>   assignedTo) {
        setTaskId(taskId);
        setName(name);
        setDescription(description);
        setStatus(status);
        setPriority(priority);
        setDueDate(dueDate);
        setAssignedTo(assignedTo);
        logger.info("Task created in the first Constructor");
    }



    public Task(String name, String description, boolean status, Priority priority, Date dueDate , List<Integer> assignedTo) {
        setName(name);
        setDescription(description);
        setStatus(status);
        setPriority(priority);
        setDueDate(dueDate);
        setAssignedTo(assignedTo);
        logger.info("Task created in the second Constructor");
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public List<Integer> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(List<Integer> assignedTo) {
        this.assignedTo  = assignedTo;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                ", assignedTo=" + assignedTo +
                '}';
    }
}
