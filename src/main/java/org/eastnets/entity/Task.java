    package org.eastnets.entity;
    import com.fasterxml.jackson.annotation.JsonBackReference;
    import com.fasterxml.jackson.annotation.JsonIdentityInfo;
    import com.fasterxml.jackson.annotation.JsonManagedReference;
    import com.fasterxml.jackson.annotation.ObjectIdGenerators;
    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;
    import org.springframework.stereotype.Component;

    import javax.jws.soap.SOAPBinding;
    import javax.persistence.*;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

    @Entity
    @Table(name = "Tasks")
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t")
    public class Task {
        private final static Logger logger = LogManager.getLogger(Task.class);
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "TASK_ID_SEQ")
        @SequenceGenerator(name = "TASK_ID_SEQ", sequenceName = "TASK_ID_SEQ", allocationSize = 1)
        @Column(name = "TASK_ID")
        private int taskId;
        @Column(name = "TASK_NAME")
        private String name;
        @Column(name = "TASK_DESC")
        private String description;

        @Column(name = "COMPLETION_STATUS")
        private boolean status;
        @Enumerated(EnumType.STRING)
        @Column(name = "PRIORITY"  , nullable = false)
        private Priority priority;

        @Column(name = "DUE_DATE")
        @Temporal(TemporalType.DATE)
        private Date dueDate;
        @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE } , fetch = FetchType.EAGER)

        @JoinTable(
                name = "tasks_assigned",
                joinColumns = @JoinColumn(name = "TASK_ID"),
                inverseJoinColumns = @JoinColumn(name = "USER_ID")
        )
        private List<User> assignedTo = new ArrayList<>();
        @ManyToOne
        @JoinColumn(name = "MODIFIED_BY", referencedColumnName = "USER_ID")
        private User modifiedBy;

        public User getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(User modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public  Task() {}

      public Task(int taskId, String name, String description, boolean status, Priority priority, Date dueDate , List<User> assignedTo , User modifiedBy) {
            setTaskId(taskId);
            setName(name);
            setDescription(description);
            setStatus(status);
            setPriority(priority);
            setDueDate(dueDate);
            setAssignedTo(assignedTo);
            setModifiedBy(modifiedBy);
            logger.info("Task created in the first Constructor");
        }



        public Task(String name, String description, boolean status, Priority priority, Date dueDate , List<User> assignedTo , User modifiedBy) {
            setName(name);
            setDescription(description);
            setStatus(status);
            setPriority(priority);
            setDueDate(dueDate);
            setAssignedTo(assignedTo);
            setModifiedBy(modifiedBy);
            logger.info("Task created in the second Constructor");
        }


        void addUser(User user) {
            assignedTo.add(user);
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

        public List<User> getAssignedTo() {
            return assignedTo;
        }

        public void setAssignedTo(List<User> assignedTo) {
            this.assignedTo  = assignedTo;
        }


    }
