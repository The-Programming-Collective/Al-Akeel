package com.redhat.project.services;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redhat.project.model.Meal;
import com.redhat.project.model.Restaurant;

// ■ Create restaurant menu [DONE]
// ■ Edit restaurant: change menu meals for each restaurant [DONE]
// ■ Get restaurant details by id [DONE]
// ■ Create restaurant report: given a restaurant id print
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

        if(res.size()!=0){restaurant=res.get(0);}
        
        return res;
    }


    public Set<Meal> getMenu(){
        return restaurant.getMealsList();
    }

    public void setMenu(Set<Meal> menu){
        Set<Meal> oldMenu =  restaurant.getMealsList();

        for(Meal item: oldMenu){
            System.out.println(item.getName());
            item.setAvaliable(false);
            entityManager.merge(item);
        }

        for (Meal item : menu) {
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

        try{
            oldMeal.setName(meal.getName());
        }catch(Exception e){};

        try{
            oldMeal.setAvaliable(meal.isAvaliable());
        }catch(Exception e){};
        
        try{
            oldMeal.setPrice(meal.getPrice());
        }catch(Exception e){};
        
        entityManager.merge(oldMeal);
    }


    public void createReport(){

    }

}
