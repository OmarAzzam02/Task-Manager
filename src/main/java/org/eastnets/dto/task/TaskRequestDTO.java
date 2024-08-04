package org.eastnets.dto.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eastnets.entity.Priority;
import org.eastnets.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object (DTO) for transferring task request information.
 * <p>
 * This class is used to encapsulate the details of a task request, including its ID, name, description,
 * status, priority, due date, the user who modified the task, and the list of users assigned to the task.
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskRequestDTO {

    /**
     * The unique identifier for the task.
     */
    private int taskId;

    /**
     * The name of the task.
     */
    private String name;

    /**
     * A description of the task.
     */
    private String description;

    /**
     * The completion status of the task.
     * <p>
     * {@code true} if the task is complete, {@code false} otherwise.
     * </p>
     */
    private boolean status;

    /**
     * The priority level of the task.
     */
    private Priority priority;

    /**
     * The due date for the task.
     */
    private Date dueDate;

    /**
     * The user who last modified the task.
     */
    private User modifiedBy;

    /**
     * A list of users assigned to the task.
     */
    private List<User> assignedTo;

    public boolean getStatus() {
        return status;
    }
}
