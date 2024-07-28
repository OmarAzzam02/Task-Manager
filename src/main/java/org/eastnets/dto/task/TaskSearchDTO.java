package org.eastnets.dto.task;

import org.eastnets.entity.User;

public class TaskSearchDTO {

    private User role;
    private String categoryToSearch;
    private String itemToSearch;

    TaskSearchDTO(){}


    public User getRole() {
        return role;
    }

    public void setRole(User role) {
        this.role = role;
    }

    public TaskSearchDTO(User role, String categoryToSearch, String itemToSearch) {
        this.role = role;
        this.categoryToSearch = categoryToSearch;
        this.itemToSearch = itemToSearch;
    }



    public TaskSearchDTO(String categoryToSearch, String itemToSearch) {
        this.categoryToSearch = categoryToSearch;
        this.itemToSearch = itemToSearch;
    }


    public String getCategoryToSearch() {
        return categoryToSearch;
    }

    public void setCategoryToSearch(String categoryToSearch) {
        this.categoryToSearch = categoryToSearch;
    }

    public String getItemToSearch() {
        return itemToSearch;
    }

    public void setItemToSearch(String itemToSearch) {
        this.itemToSearch = itemToSearch;
    }
}
