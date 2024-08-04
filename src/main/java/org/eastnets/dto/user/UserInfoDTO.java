package org.eastnets.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eastnets.entity.UserType;

/**
 * Data Transfer Object (DTO) for transferring user information.
 * <p>
 * This class is used to encapsulate user details such as ID, username, password, email, and user type
 * during communication between different layers of the application.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfoDTO {

    /**
     * The unique identifier for the user.
     */
    private int userId;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     * <p>
     * Note: In a real-world application, passwords should not be exposed or transmitted in plain text.
     */
    private String password;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The type of the user, indicating their role or permissions within the application.
     */
    private UserType userType;
}
