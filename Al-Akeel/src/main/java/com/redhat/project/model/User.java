package com.redhat.project.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User implements Serializable {
    public enum Role{CUSTOMER, RESTUARANT_OWNER, RUNNER}
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private Role role;

    
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Restaurant> restaurant;


    // Getters
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public Role getRole(){return this.role;}

    // Setters
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setRole(Role role){this.role = role;}

}
