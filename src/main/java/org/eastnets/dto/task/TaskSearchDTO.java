package org.eastnets.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eastnets.entity.User;

/**
 * Data Transfer Object (DTO) for encapsulating search criteria for tasks.
 * <p>
 * This class is used to hold the details required to perform a task search, including the user's role,
 * the category of the search, and the specific item to search for.
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TaskSearchDTO {

    /**
     * The user role that is performing the search.
     */
    private User role;

    /**
     * The category by which tasks are to be searched.
     * <p>
     * This could be attributes like "id", "name", "status", etc.
     * </p>
     */
    private String categoryToSearch;

    /**
     * The specific item or value to search for within the selected category.
     * <p>
     * For example, if the category is "name", this field could contain the name of the task to search for.
     * </p>
     */
    private String itemToSearch;

    /**
     * Constructor to initialize a {@link TaskSearchDTO} without a user role.
     *
     * @param categoryToSearch the category by which tasks are to be searched.
     * @param itemToSearch     the specific item or value to search for within the selected category.
     */
    public TaskSearchDTO(String categoryToSearch, String itemToSearch) {
        this.categoryToSearch = categoryToSearch;
        this.itemToSearch = itemToSearch;
    }
}
