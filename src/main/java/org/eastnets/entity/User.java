package org.eastnets.entity;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a User entity in the system.
 * <p>
 * A user is an individual who can have tasks assigned to them. This class
 * includes details such as username, password, email, and user type.
 * </p>
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
@Data
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "select u from User u")
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    @Column(name = "USER_ID")
    private int userId;

    /**
     * Username of the user.
     * Must not be null.
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * Password of the user.
     * Must not be null.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Email address of the user.
     * Must not be null.
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * Type of user role.
     * Must not be null.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserType userType;

    /**
     * List of tasks assigned to this user.
     * Eagerly fetched from the database.
     */
    @ManyToMany(mappedBy = "assignedTo", fetch = FetchType.EAGER)
    private List<Task> tasksAssigned = new ArrayList<>();

    /**
     * Constructs a new User with the specified details.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email address of the user
     * @param userType the type of user role
     */
    public User(String username, String password, String email, UserType userType) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setUserType(userType);
        log.info("User created!");
    }

    /**
     * Constructs a new User with the specified details, including an ID.
     *
     * @param userId the unique identifier for the user
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email address of the user
     * @param userType the type of user role
     */
    public User(int userId, String username, String password, String email, UserType userType) {
        setUserId(userId);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setUserType(userType);
        log.info("User created!");
    }
}
