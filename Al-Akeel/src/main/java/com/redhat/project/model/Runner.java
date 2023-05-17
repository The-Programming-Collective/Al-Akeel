package com.redhat.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

enum Status{
    AVAILABLE, BUSY
}

@Entity
public class Runner extends User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    
    private double deliveryFees;
    private Status status;

    // @OneToOne
    // @JoinColumn(name = "order")
    private Orders orders;

    // Getters
    public int getId(){return this.id;}
    public double getDeliveryFees(){return this.deliveryFees;}
    public Status geStatus(){return this.status;}
    public Orders getOrder(){return this.orders;}

    // Setters
    public void setId(int id){this.id = id;}
    public void setDeliveryFees(double fees){this.deliveryFees = fees;}
    public void setStatus(Status status){this.status = status;}
    public  void setOrder(Orders orders){this.orders = orders;}
}
