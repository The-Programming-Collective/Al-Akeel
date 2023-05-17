package com.redhat.project.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

enum Status{
    AVAILABLE, BUSY
}

@Entity
public class Runner extends User{
    public Runner(){
        this.deliveryFees = 0;
        this.status = Status.AVAILABLE;
        this.order = null;
        this.role = Role.RUNNER;
    }
    
    
    private double deliveryFees;
    private Status status;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //getters
    public double getDeliveryFees(){return this.deliveryFees;}
    public Status geStatus(){return this.status;}
    public Order getOrder(){return this.order;}

    //setters
    public void setDeliveryFees(double fees){this.deliveryFees = fees;}
    public void setStatus(Status status){this.status = status;}
    public  void setOrder(Order order){this.order = order;}
}
