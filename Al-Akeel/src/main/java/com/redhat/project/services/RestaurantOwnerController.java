package com.redhat.project.services;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redhat.project.model.Meal;
import com.redhat.project.model.Restaurant;

// ■ Create restaurant menu
// ■ Edit restaurant: change menu meals for each restaurant TODO:
// ■ Get restaurant details by id [DONE]
// ■ Create restaurant report: given a restaurant id print
// how much the restaurant earns (summation of total amount of all completed orders) , Number of completed orders, Number of canceled orders

@Stateless 
public class RestaurantOwnerController {

    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;
    private Restaurant restaurant;

    public RestaurantOwnerController(){}
    
    public List<Restaurant> getRestaurant(int restaurant_id, int owner_id) throws Exception{
        TypedQuery<Restaurant> q = entityManager.createNamedQuery("getRestaurant",Restaurant.class);
        q.setParameter("res_id", restaurant_id);
        q.setParameter("owner_id", owner_id);
        List<Restaurant> res = q.getResultList();

        if(res.size()!=0){restaurant=res.get(0);}
        
        return res;
    }


    public void createMenu(){

    }

    public Set<Meal> getMenu(){
        return restaurant.getMealsList();
    }

    public void addMenuMeal(Meal meal){
        // entityManager
        // restaurant.addMeal(meal);
    }
    public void removeMenuMeal(int meal_id){

    }
    public void updateMenuMeal(int meal_id, Meal meal){
        
    }

    public void createReport(){

    }

}
