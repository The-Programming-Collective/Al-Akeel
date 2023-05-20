package com.redhat.project.services;


import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.redhat.project.model.Orders.OrderStatus;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Runner;
import com.redhat.project.model.Runner.RunnerStatus;

// Runner can mark an order is delivered and his status to available.
// Get number of trips completed by a runner make sure orders are not canceled
// and marked as completed.
// Note: when creating runner account let user enter delivery fees per order , this value will be
// reused when calculating the total order value.

// Entities:
// User: Id ,name, role
// Meal: id, name , price, fk_restaurantId
// Order: Id, Item array, total_price, fk_runnerId, fk_restaurantId, order_status(preparing, delivered,
// canceled )
// Runner: Id, name, status(available, busy),delivery_fees
// Restaurant: Id, name, ownerId, list of meals

@Stateless 
public class RunnerController {
    
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;
    private Runner runner;

    public boolean setRunner(Runner runner){
        this.runner = runner;
        return true;
    }


    public Runner getRunner(){
        // TypedQuery<Runner> query = entityManager.createNamedQuery("getRunner", Runner.class);
        // query.setParameter("runner_id", runner_id);
        // List<Runner> list = query.getResultList();
        // if(!(list.isEmpty())){this.runner = list.get(0);}

        return this.runner;
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
            if(runner.getRunnerStatus().equals(RunnerStatus.BUSY))
                throw new IllegalAccessError();
                
            runner.setDeliveryFees(newFees);
            entityManager.merge(runner);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}