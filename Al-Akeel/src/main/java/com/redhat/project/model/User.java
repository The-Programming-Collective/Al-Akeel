package com.redhat.project.model;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


enum Role{
    CUSTOMER, RESTUARANT_OWNER, RUNNER 
}

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    Role role;

    //getters
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public Role getRole(){return this.role;}

    //setters 
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setRole(Role role){this.role = role;}



    


}
