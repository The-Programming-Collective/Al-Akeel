package com.redhat.project.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@NamedQueries({
    @NamedQuery(name = "getRunner" , query = "SELECT r from Runner r where r.id = :runner_id"),
})
@Entity
public class Runner extends User{
    
    public enum RunnerStatus{AVAILABLE, BUSY}

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    public Runner(){
        this.setRole(Role.RUNNER);
        this.runnerStatus = RunnerStatus.AVAILABLE;
    }
    
    private double deliveryFees;
    private RunnerStatus runnerStatus;

    @OneToOne(mappedBy = "runner")
    private Orders assigned_order;

    // Getters
    public int getId(){return this.id;}
    public double getDeliveryFees(){return this.deliveryFees;}
    public RunnerStatus getRunnerStatus(){return this.runnerStatus;}
    public Orders returnAssignedOrder(){return this.assigned_order;}


    // Setters
    public void setId(int id){this.id = id;}
    public void setDeliveryFees(double fees){this.deliveryFees = fees;}
    public void setRunnerStatus(RunnerStatus runnerStatus){this.runnerStatus = runnerStatus;}
    public void submitAssignedOrder(Orders order){this.assigned_order = order;}

}
