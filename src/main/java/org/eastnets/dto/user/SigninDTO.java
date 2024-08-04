package org.eastnets.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for handling user sign-in requests.
 * <p>
 * This class encapsulates the user credentials required for signing in, including the username and password.
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SigninDTO {

    /**
     * The username of the user attempting to sign in.
     */
    private String username;

    /**
     * The password of the user attempting to sign in.
     */
    private String password;
}
