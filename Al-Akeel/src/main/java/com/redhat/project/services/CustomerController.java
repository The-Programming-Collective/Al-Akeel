package com.redhat.project.services;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redhat.project.model.Meal;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Restaurant;
import com.redhat.project.model.User;
import com.redhat.project.model.Runner.RunnerStatus;
import com.redhat.project.util.Authenticator;
import com.redhat.project.util.CreditCardInfo;
import com.redhat.project.util.PaymentAuthenticator;
import com.redhat.project.model.Runner;



@Stateless 
public class CustomerController {
    

    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

    @Inject
    private Authenticator authenticator;


    // used in both create order and edit order
    public boolean setItemsList(Orders order,Set<Integer> meals_ids){
        try{
            Restaurant restaurant = entityManager.find(Restaurant.class,order.getRestaurant());
            Set<Meal> meals = restaurant.getMealsList();
            order.setItemsList(new HashSet<Meal>());
            for(Integer meal_id : meals_ids){
                Meal meal = entityManager.find(Meal.class,meal_id);
                if(meals.contains(meal))
                    order.addItem(meal);
            }
            return true;
        }catch(Exception e){
            return false;
        }
    }


    public Orders createOrder(CreditCardInfo creditCardInfo, int restaurant_id,Set<Integer> meals_ids){

        User customer = authenticator.authenticate();
        Orders order = new Orders();

        order.setName( customer.getName()+"'s Order");
        Restaurant restaurant = entityManager.find(Restaurant.class,restaurant_id);
        order.setRestaurant(restaurant);

        setItemsList(order, meals_ids);

        if(order.getItemsList().size() == 0)
            return null;
        
        PaymentAuthenticator paymentAuthenticator = new PaymentAuthenticator();
        if(!paymentAuthenticator.pay(creditCardInfo))
            return null;

        // Set first available runner to order
        // need to check if no runners available
        TypedQuery<Runner> query = entityManager.createNamedQuery("getRunnerOnStatus", Runner.class);
        query.setParameter("runner_status", RunnerStatus.AVAILABLE);
        query.setMaxResults(1);
        Runner runner = query.getSingleResult();
        runner.setRunnerStatus(RunnerStatus.BUSY);

        order.setRunner(runner);
        order.setCustomer(customer);
        runner.addOrder(order);
        customer.addOrder(order);

        entityManager.persist(order);
        entityManager.merge(runner);
        entityManager.merge(customer);

        return order;
    }


    public boolean editOrder(int order_id,Set<Integer> meals_ids){
        User customer = authenticator.authenticate();
        Orders order = entityManager.find(Orders.class,order_id);
        
        if(order.getCustomerId()!=customer.getId())
            return false;

        if(order.getOrderStatus() == Orders.OrderStatus.PREPARING)
        {
            setItemsList(order, meals_ids);
            entityManager.merge(order);
            return true;
        }
        else{return false;}
    }


    public List<Restaurant> getRestaurants(){
        try{
            TypedQuery<Restaurant> query = entityManager.createNamedQuery("getRestaurants",Restaurant.class);
            return query.getResultList();
        }catch(Exception e){
            return null;
        }
    }



}

