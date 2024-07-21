package org.eastnets.entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll" , query = "select u from User u")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private int userId;

    @Transient
    private final static Logger logger = LogManager.getLogger(User.class);
    @Column(name = "username",  nullable = false)
    private String username;
    @Column(name = "password" ,  nullable = false)
    private String password;
    @Column(name = "email" ,  nullable = false)
    private String email;
    // todo
    @Enumerated(EnumType.STRING)
    @Column(name = "role"  , nullable = false)
    private UserType userType;
    @ManyToMany(mappedBy = "assignedTo", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Task> tasksAssigned = new ArrayList<>();

    public User() {}

    public User(int userId, String username, String password, String email, UserType userType , List<Task> tasks) {
        setUserId(userId);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setUserType(userType);
        setTaskAssigned(tasks);
        logger.info("User created");
    }

    public void setTaskAssigned(List<Task> tasks) {
        this.tasksAssigned = tasks;
    }

    public List<Task> getTasksAssigned() {
        return tasksAssigned;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


}
