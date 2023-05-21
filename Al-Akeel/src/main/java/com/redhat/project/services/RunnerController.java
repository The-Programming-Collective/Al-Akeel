package com.redhat.project.services;


import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.project.model.Orders.OrderStatus;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Runner;
import com.redhat.project.model.Runner.RunnerStatus;


@Stateless 
public class RunnerController {
    
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;
    private Runner runner;

    public boolean setRunner(Runner runner){
        this.runner = runner;
        return true;
    }

  
    public void completeOrder(){
        if (this.runner.getRunnerStatus().equals(RunnerStatus.BUSY)){
            this.runner.setRunnerStatus(RunnerStatus.AVAILABLE);
            this.runner.returnAssignedOrder().setOrderStatus(OrderStatus.DELIVERED);
            


            entityManager.merge(this.runner.returnAssignedOrder());
            entityManager.merge(this.runner);
        }
    }

    
    public int getCompletedOrdersCount(){
        Set<Orders> orders = this.runner.returnAssignedOrders();
        int counter = 0 ;
        for(Orders order : orders){
            if(order.getOrderStatus()==OrderStatus.DELIVERED)
                counter++;
        }
        return counter;
    }


    public boolean changeFees(Double newFees){
        try{
            if(runner.getRunnerStatus()==RunnerStatus.BUSY)
                throw new IllegalAccessError();
                
            runner.setDeliveryFees(newFees);
            entityManager.merge(runner);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}