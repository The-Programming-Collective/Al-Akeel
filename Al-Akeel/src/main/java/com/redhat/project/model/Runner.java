package com.redhat.project.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
    @NamedQuery(name = "getRunner" , query = "SELECT r from Runner r where r.id = :runner_id"),
    @NamedQuery(name = "getRunnerOnStatus", query = "SELECT r from Runner r where r.runnerStatus = :runner_status"),
})
@Entity
public class Runner extends User{
    
    public enum RunnerStatus{AVAILABLE, BUSY}

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    

    public Runner(String name, String userName, double deliveryFees){
        super(name, userName);
        this.setRole(Role.RUNNER);
        this.runnerStatus = RunnerStatus.AVAILABLE;
        this.deliveryFees = deliveryFees;
    }

    public Runner(){
        this.setRole(Role.RUNNER);
        this.runnerStatus = RunnerStatus.AVAILABLE;
    }
    
    private double deliveryFees;
    private RunnerStatus runnerStatus;


    // Getters
    public int getId(){return this.id;}
    public double getDeliveryFees(){return this.deliveryFees;}
    public RunnerStatus getRunnerStatus(){return this.runnerStatus;}
    public Orders returnAssignedOrder(){
        Orders latestOrder = null;
        for (Orders item : this.orders) 
            latestOrder = item; // Update latestItem on each iteration
        return latestOrder;
    }
    public Set<Orders> returnAssignedOrders(){return this.orders;}


    // Setters
    public void setId(int id){this.id = id;}
    public void setDeliveryFees(double fees){this.deliveryFees = fees;}
    public void setRunnerStatus(RunnerStatus runnerStatus){this.runnerStatus = runnerStatus;}
    public void addOrder(Orders order){this.orders.add(order);}

}
