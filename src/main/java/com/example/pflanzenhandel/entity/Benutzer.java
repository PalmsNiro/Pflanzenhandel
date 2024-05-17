package com.example.pflanzenhandel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import java.util.Set;

/**
 * Represents a user entity in the application.
 * This entity is mapped to a database table using JPA annotations.
 */
@Entity
public class Benutzer {
    /**
     * The unique identifier for the user.
     */

    @Id
    @GeneratedValue
    private Integer userId;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */

    private String password;

    /**
     * Indicates whether the user account is enabled.
     */

    private boolean enabled = true;

    /**
     * The roles assigned to the user.
     * This is a many-to-many relationship fetched eagerly.
     */

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Rolle> rolles;

    /**
     * Default constructor required by Hibernate.
     */
    public Benutzer() {
        // emp
        // ty constructor for Hibernate
    }
    /**
     * Gets the unique identifier for the user.
     *
     * @return the userId of the user.
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userId the new userId of the user.
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Checks if the user account is enabled.
     *
     * @return true if the user account is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled status of the user account.
     *
     * @param enabled the new enabled status of the user account.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the roles assigned to the user.
     *
     * @return the roles assigned to the user.
     */
    public Set<Rolle> getRoles() {
        return rolles;
    }

    /**
     * Sets the roles assigned to the user.
     *
     * @param rollen the new roles assigned to the user.
     */
    public void setRoles(Set<Rolle> rollen) {
        this.rolles = rollen;
    }
}


