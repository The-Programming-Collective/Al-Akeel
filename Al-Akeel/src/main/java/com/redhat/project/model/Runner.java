package com.redhat.project.model;

import javax.persistence.Entity;

enum Status{
    AVAILABLE, BUSY
}

@Entity
public class Runner extends User{
    private double deliveryFees;
    private Status status;

    //getters
    public double getDeliveryFees(){return this.deliveryFees;}
    public Status geStatus(){return this.status;}

    //setters
    public void setDeliveryFees(double fees){this.deliveryFees = fees;}
    public void setStatus(Status status){this.status = status;}
}
