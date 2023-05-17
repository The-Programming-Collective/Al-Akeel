package com.redhat.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

enum Status{AVAILABLE, BUSY}

@Entity
public class Runner extends User{
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    public Runner(){
        this.setRole(Role.RUNNER);
        this.status = Status.AVAILABLE;
    }
    
    private double deliveryFees;
    private Status status;

    @OneToOne(mappedBy = "runner")
    private Orders assigned_order;

    // Getters
    public int getId(){return this.id;}
    public double getDeliveryFees(){return this.deliveryFees;}
    public Status geStatus(){return this.status;}

    // Setters
    public void setId(int id){this.id = id;}
    public void setDeliveryFees(double fees){this.deliveryFees = fees;}
    public void setStatus(Status status){this.status = status;} 
}
