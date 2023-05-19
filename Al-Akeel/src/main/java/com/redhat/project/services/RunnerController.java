package com.redhat.project.services;


import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redhat.project.model.Orders.OrderStatus;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Runner;
import com.redhat.project.model.Runner.RunnerStatus;

// Runner can mark an order is delivered and his status to available.
// Get number of trips completed by a runner make sure orders are not canceled
// and marked as completed.
// Note: when creating runner account let user enter delivery fees per order , this value will be
// reused when calculating the total order value.

//TODO add change fees functionality 
@Stateless 
public class RunnerController {
    
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;
    private Runner runner;


    public Runner getRunner(int runner_id){
        TypedQuery<Runner> query = entityManager.createNamedQuery("getRunner", Runner.class);
        query.setParameter("runner_id", runner_id);
        List<Runner> list = query.getResultList();
        if(!(list.isEmpty())){this.runner = list.get(0);}

        return this.runner;
    }

    
    public void completeOrder(){
        if (this.runner.returnAssignedOrder().getOrderStatus().equals(OrderStatus.DELIVERING) 
            && this.runner.getRunnerStatus().equals(RunnerStatus.BUSY)){

            this.runner.setRunnerStatus(RunnerStatus.AVAILABLE);
            this.runner.returnAssignedOrder().setOrderStatus(OrderStatus.COMPLETED);

            entityManager.merge(this.runner.returnAssignedOrder());
            entityManager.merge(this.runner);
        }
    }

    
    public int getCompletedOrdersCount(){
        Set<Orders> orders = this.runner.returnAssignedOrders();
        int counter = 0 ;
        for(Orders order : orders){
            if(order.getOrderStatus()==OrderStatus.COMPLETED)
                counter++;
        }
        return counter;
    }

}