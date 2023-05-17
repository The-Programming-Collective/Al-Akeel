package com.redhat.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

enum Status{
    AVAILABLE, BUSY
}

@Entity
public class Runner extends User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    public Runner(){ this.setRole(Role.RUNNER);}
    
    private double deliveryFees;
    private Status status;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    // Getters
    public int getId(){return this.id;}
    public double getDeliveryFees(){return this.deliveryFees;}
    public Status geStatus(){return this.status;}
    public Orders getOrder(){return this.order;}

    // Setters
    public void setId(int id){this.id = id;}
    public void setDeliveryFees(double fees){this.deliveryFees = fees;}
    public void setStatus(Status status){this.status = status;}
    public  void setOrder(Orders orders){this.order = orders;}
}
