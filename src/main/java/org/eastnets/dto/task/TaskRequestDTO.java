package org.eastnets.dto.task;

import org.eastnets.entity.Priority;
import org.eastnets.entity.User;
import java.util.Date;
import java.util.List;

public class TaskRequestDTO {


    private String name;
    private String description;
    private boolean status;
    private Priority priority;
    private Date dueDate;
    private User modifiedBy;
    private List<User> assignedTo;

    public TaskRequestDTO() {}

    public List<User> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(List<User> assignedTo) {
        this.assignedTo = assignedTo;
    }

    public TaskRequestDTO(String name, String description, boolean status, Priority priority, Date dueDate, User modifiedBy , List<User>assignedTo ) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.modifiedBy = modifiedBy;
        this.assignedTo = assignedTo;
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

    public User getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(User modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
