package org.eastnets.entity;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a Task entity in the system.
 * <p>
 * A task is a unit of work that includes details such as name, description,
 * completion status, priority, due date, and the users assigned to it.
 * </p>
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Log4j2
@Table(name = "Tasks")
@NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t")
public class Task {

    /**
     * Unique identifier for the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TASK_ID_SEQ")
    @SequenceGenerator(name = "TASK_ID_SEQ", sequenceName = "TASK_ID_SEQ", allocationSize = 1)
    @Column(name = "TASK_ID")
    private int taskId;

    /**
     * Name of the task.
     */
    @Column(name = "TASK_NAME")
    private String name;

    /**
     * Description of the task.
     */
    @Column(name = "TASK_DESC")
    private String description;

    /**
     * Status of the task indicating whether it is completed.
     */
    @Column(name = "COMPLETION_STATUS")
    private boolean status;

    /**
     * Priority level of the task.
     * Must not be null.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORITY", nullable = false)
    private Priority priority;

    /**
     * Due date for the task.
     */
    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    /**
     * List of users assigned to this task.
     * Eagerly fetched from the database.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tasks_assigned",
            joinColumns = @JoinColumn(name = "TASK_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    private List<User> assignedTo = new ArrayList<>();

    /**
     * User who last modified the task.
     */
    @ManyToOne()
    @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "USER_ID")
    private User modifiedBy;

    /**
     * Constructs a new Task with the specified details.
     *
     * @param name        the name of the task
     * @param description the description of the task
     * @param status      the completion status of the task
     * @param priority    the priority of the task
     * @param dueDate     the due date of the task
     * @param assignedTo  list of users assigned to the task
     * @param modifiedBy  the user who last modified the task
     */
    public Task(String name, String description, boolean status, Priority priority, Date dueDate, List<User> assignedTo, User modifiedBy) {
        setName(name);
        setDescription(description);
        setStatus(status);
        setPriority(priority);
        setDueDate(dueDate);
        setAssignedTo(assignedTo);
        setModifiedBy(modifiedBy);
        log.info("Task created in the second Constructor");
    }

    /**
     * Adds a user to the list of users assigned to this task.
     *
     * @param user the user to be assigned to the task
     */
    public void addAssignedTo(User user) {
        this.assignedTo.add(user);
    }
}
