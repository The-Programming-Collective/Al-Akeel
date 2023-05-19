package com.redhat.project.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redhat.project.model.Meal;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Orders.OrderStatus;
import com.redhat.project.model.Restaurant;

// ■ Create restaurant menu [DONE]
// ■ Edit restaurant: change menu meals for each restaurant [DONE]
// ■ Get restaurant details by id [DONE]
// ■ Create restaurant report: given a restaurant id print [DONE]
// how much the restaurant earns (summation of total amount of all completed orders) , Number of completed orders, Number of canceled orders

@Stateless
public class RestaurantOwnerController {

    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;
    private Restaurant restaurant;
    
    public List<Restaurant> getRestaurant(int restaurant_id, int owner_id) throws Exception{
        TypedQuery<Restaurant> q = entityManager.createNamedQuery("getRestaurant",Restaurant.class);
        q.setParameter("res_id", restaurant_id);
        q.setParameter("owner_id", owner_id);

        List<Restaurant> res = q.getResultList();

        if(res.size()!=0){this.restaurant=res.get(0);}
        
        return res;
    }


    public Set<Meal> getMenu(){
        return restaurant.getMealsList();
    }

    
    public void setMenu(Set<Meal> menu){
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
    }


    public void addMenuMeal(Meal meal){
        meal.setRestaurant(restaurant);
        meal.setAvaliable(true);
        restaurant.addMeal(meal);
        entityManager.persist(meal);
    }


    public void removeMenuMeal(int meal_id){
        Meal meal = entityManager.find(Meal.class, meal_id);
        restaurant.removeMeal(meal);
    }


    public void updateMenuMeal(int meal_id, Meal meal){
        Meal oldMeal = entityManager.find(Meal.class, meal_id);

        oldMeal.setName(meal.getName());
        oldMeal.setAvaliable(meal.isAvaliable());
        oldMeal.setPrice(meal.getPrice());
        
        entityManager.merge(oldMeal);
    }


    public Map<String,Double> createReport(){
        TypedQuery<Orders> query = entityManager
        .createNamedQuery("getOrders", Orders.class);
        query.setParameter("restaurant_id", this.restaurant.getId());
        
        List<Orders> orders = query.getResultList();

        System.out.println(orders.size());

        int canceled = 0;
        int completed = 0;
        Double earnings = 0.0;
        for(Orders order : orders){
            OrderStatus orderStatus = order.getOrderStatus();
            if(orderStatus==OrderStatus.CANCELED)
                canceled++;
            else if(orderStatus==OrderStatus.COMPLETED){
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

}
