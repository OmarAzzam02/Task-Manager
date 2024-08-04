package org.eastnets.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eastnets.entity.User;

/**
 * Data Transfer Object (DTO) for handling user privilege update requests.
 * <p>
 * This class encapsulates the information required to update the privileges of a user, including the user
 * who is making the update request and the user whose privileges are being updated.
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserPrivilegeUpdateDTO {

    /**
     * The user who is performing the privilege update.
     */
    private User updatedBy;

    /**
     * The user whose privileges are being updated.
     */
    private User toUpdate;
}
