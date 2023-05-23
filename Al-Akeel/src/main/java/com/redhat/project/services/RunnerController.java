package com.redhat.project.services;


import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.project.model.Orders.OrderStatus;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Runner;
import com.redhat.project.model.Runner.RunnerStatus;
import com.redhat.project.util.Authenticator;


@Stateless 
public class RunnerController {
    
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

    @Inject 
    Authenticator authenticator;
    
  
    public void completeOrder(){
        Runner runner = (Runner)authenticator.authenticate();

        if (runner.getRunnerStatus().equals(RunnerStatus.BUSY)){
            runner.setRunnerStatus(RunnerStatus.AVAILABLE);
            runner.returnAssignedOrder().setOrderStatus(OrderStatus.DELIVERED);
            


            entityManager.merge(runner.returnAssignedOrder());
            entityManager.merge(runner);
        }
    }

    
    public int getCompletedOrdersCount(){
        Runner runner = (Runner)authenticator.authenticate();

        Set<Orders> orders = runner.returnAssignedOrders();
        int counter = 0 ;
        for(Orders order : orders){
            if(order.getOrderStatus()==OrderStatus.DELIVERED)
                counter++;
        }
        return counter;
    }


    public boolean changeFees(Double newFees){
        Runner runner = (Runner)authenticator.authenticate();
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