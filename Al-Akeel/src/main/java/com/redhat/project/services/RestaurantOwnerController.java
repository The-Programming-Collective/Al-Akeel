package com.redhat.project.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import com.redhat.project.model.Restaurant;

@Stateless
@Consumes("application/json")
@Produces("application/json") 
public class RestaurantOwnerController {

    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;
    // private Restaurant restaurant;

    public RestaurantOwnerController(){}
    
    public List<Restaurant> getRestaurant(int owner_id) throws Exception{
        TypedQuery<Restaurant> q = entityManager.createNamedQuery("getRestaurant",Restaurant.class);
        q.setParameter("owner_id", owner_id);
        return q.getResultList();
    }
    public void CreateMenu(){

    }



}
