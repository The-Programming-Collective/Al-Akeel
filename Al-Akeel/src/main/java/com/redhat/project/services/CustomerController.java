package com.redhat.project.services;

import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redhat.project.model.Meal;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Restaurant;
import com.redhat.project.model.User;
import com.redhat.project.model.Runner.RunnerStatus;
import com.redhat.project.model.Runner;

// Create order by customer
// Exceptations: imagine a normal restaurant receipt

// Order details should contains date ,restaurant name, items list , delivery fees,
// runner name, total receipt value (summation of items prices , delivery fees )
// list orders by customer id
// When creating an order select a random available runner from db and assign it to
// an order and convert his status to busy
// Edit order [change an order’s items] make sure an order is not canceled and it is
// in the preparing state to be edited
// List all restaurants

@Stateless 
public class CustomerController {
    

    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

    private User customer;

    //date[done] , restaurant name[done], items list , delivery fees, runner name, total receipt value (summation of items prices , delivery fees )
    public Orders createOrder(int restaurant_id,Set<Integer> meals_ids){
        Orders order = new Orders();
        order.setName(/*customer.getName() +*/ "'s Order");
        Restaurant restaurant = entityManager.find(Restaurant.class,restaurant_id);
        order.setRestaurant(restaurant);

        Set<Meal> meals = restaurant.getMealsList();
        for(Integer meal_id : meals_ids){
            Meal meal = entityManager.find(Meal.class,meal_id);
            if(meals.contains(meal))
                order.addItem(meal);
        }

        //set first available runner to order
        TypedQuery<Runner> query = entityManager.createNamedQuery("getRunnerOnStatus", Runner.class);
        query.setParameter("runner_status", RunnerStatus.AVAILABLE);
        query.setMaxResults(1);
        Runner runner = query.getSingleResult();
        runner.setRunnerStatus(RunnerStatus.BUSY);
        order.setRunner(runner);

        entityManager.merge(runner);
        entityManager.persist(order);

        return order;
    }

}