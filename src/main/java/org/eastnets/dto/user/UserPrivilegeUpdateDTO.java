package org.eastnets.dto.user;

import org.eastnets.entity.User;

public class UserPrivilegeUpdateDTO {

    User updatedBy;
    User toUpdate;

    public UserPrivilegeUpdateDTO() {}

    public UserPrivilegeUpdateDTO(User updatedBy, User toUpdate, String privilege) {
        this.updatedBy = updatedBy;
        this.toUpdate = toUpdate;

    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public User getToUpdate() {
        return toUpdate;
    }

    public void setToUpdate(User toUpdate) {
        this.toUpdate = toUpdate;
    }


}
