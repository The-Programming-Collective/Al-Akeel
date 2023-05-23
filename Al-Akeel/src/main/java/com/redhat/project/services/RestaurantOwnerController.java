package com.redhat.project.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redhat.project.model.Meal;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Orders.OrderStatus;
import com.redhat.project.util.Authenticator;
import com.redhat.project.model.Restaurant;
import com.redhat.project.model.User;


@Stateless
public class RestaurantOwnerController {


    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;


    @Inject
    private Authenticator authenticator;

  
    public Restaurant getRestaurant(int restaurant_id){
        try{
            User owner = authenticator.authenticate();
            TypedQuery<Restaurant> q = entityManager.createNamedQuery("getRestaurant",Restaurant.class);
            q.setParameter("res_id", restaurant_id);
            q.setParameter("owner_id", owner.getId());

            List<Restaurant> res = q.getResultList();

            return res.get(0);

        }catch(Exception e){
            return null;
        }
    }


    public Set<Meal> getMenu(int restaurant_id){
        return getRestaurant(restaurant_id).getMealsList();
    }

    
    public boolean setMenu(int restaurant_id, Set<Meal> menu){
        User owner = authenticator.authenticate();
        Restaurant restaurant = getRestaurant(restaurant_id);

        if(restaurant.getOwner().getId()!=owner.getId())
            return false;

        Set<Meal> oldMenu =  restaurant.getMealsList();

        for(Meal item: oldMenu){
            item.setAvaliable(false);
            entityManager.merge(item);
        }

        for (Meal item : menu){
            item.setRestaurant(restaurant);
            item.setAvaliable(true);
            entityManager.persist(item);
        }
        
        restaurant.setMealsList(menu);
        return true;
    }


    public void addMenuMeal(int restaurant_id, Meal meal){
        Restaurant restaurant = getRestaurant(restaurant_id);
        meal.setRestaurant(restaurant);
        restaurant.addMeal(meal);
        meal.setAvaliable(true);

        try{
            Thread.sleep(1000);
        }catch(Exception e){
            System.out.println("Error in completing order");
        }

        entityManager.persist(meal);
        entityManager.merge(restaurant);
    }


    public void removeMenuMeal(int restaurant_id,int meal_id){
        Restaurant restaurant = getRestaurant(restaurant_id);
        Meal meal = entityManager.find(Meal.class, meal_id);
        restaurant.removeMeal(meal);
    }


    public boolean updateMenuMeal(int restaurant_id,int meal_id, Meal meal){
        Restaurant restaurant = getRestaurant(restaurant_id);
        Meal oldMeal = entityManager.find(Meal.class, meal_id);

        if(!restaurant.getMealsList().contains(oldMeal))
            return false;
    
        oldMeal.setName(meal.getName());
        oldMeal.setAvaliable(meal.isAvaliable());
        oldMeal.setPrice(meal.getPrice());
        
        entityManager.merge(oldMeal);
        return true;
    }


    public Map<String,Double> createReport(int restaurant_id){
        Restaurant restaurant = getRestaurant(restaurant_id);

        TypedQuery<Orders> query = entityManager
        .createNamedQuery("getOrdersList", Orders.class);
        query.setParameter("restaurant_id", restaurant.getId());
        
        List<Orders> orders = query.getResultList();

        System.out.println(orders.size());

        int canceled = 0;
        int completed = 0;
        Double earnings = 0.0;
        for(Orders order : orders){
            OrderStatus orderStatus = order.getOrderStatus();
            if(orderStatus==OrderStatus.CANCELED)
                canceled++;
            else if(orderStatus==OrderStatus.DELIVERED){
                earnings+=order.getTotalPrice();
                completed++;
            }
            System.out.println(order.getName());
        }

        Map<String,Double> map = new HashMap<String,Double>();

        map.put("completedOrders", Double.valueOf(completed));
        map.put("canceledOrders", Double.valueOf(canceled));
        map.put("earnings", earnings);
        
        return map;
    }


    public List<Orders> getOrders(int restaurant_id){
        TypedQuery<Orders> query = entityManager.createNamedQuery("getOrdersList", Orders.class);
        query.setParameter("restaurant_id", restaurant_id);
        return query.getResultList();
    }

}
