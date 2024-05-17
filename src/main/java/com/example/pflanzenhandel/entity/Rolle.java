package com.example.pflanzenhandel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Represents a role entity in the application.
 * This entity is mapped to a database table using JPA annotations.
 */
@Entity
public class Rolle {

    /**
     * The unique identifier for the role.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * The name of the role.
     */
    private String rolename;

    public Rolle() {
        //empty constructor for Hibernate
    }

    /**
     * Constructor to create a role with a specific name.
     *
     * @param rolename the name of the role.
     */
    public Rolle(String rolename) {
        this.rolename = rolename;
    }

    /**
     * Gets the unique identifier for the role.
     *
     * @return the id of the role.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the role.
     *
     * @param id the new id of the role.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the role.
     *
     * @return the name of the role.
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * Sets the name of the role.
     *
     * @param rolename the new name of the role.
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}