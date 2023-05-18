package com.redhat.project.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.redhat.project.model.Meal;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Restaurant;
import com.redhat.project.model.Runner;
import com.redhat.project.model.User;
import com.redhat.project.model.User.Role;
import com.redhat.project.services.RestaurantOwnerController;



@Stateless
@Consumes("application/json")
@Produces("application/json")   
@Path("/")
public class test {
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;
    @Inject
    private RestaurantOwnerController roc;


    @POST
    @Path("")
    public void persistUser(){
        Runner runner = new Runner();
        runner.setName("ahmed");
        
        User owner = new User();
        owner.setName("ali");
        owner.setRole(Role.RESTUARANT_OWNER);
        
        
        Restaurant res = new Restaurant("koshary el tahrir");
        res.setOwner(owner);
        
        Orders order = new Orders();
        order.setName("testOrder");
        order.setRestaurant(res);
        order.setRunner(runner);


        
        Meal meal = new Meal("koshary",4.5);
        Meal meal2 = new Meal("d7k",7.5);
        res.addMeal(meal);
        res.addMeal(meal2);
        meal.setRestaurant(res);
        meal2.setRestaurant(res);
        order.addItem(meal);
        order.addItem(meal2);

        // res.getMealsList().iterator().next().getName();
        
        entityManager.persist(meal);
        entityManager.persist(meal2);
        entityManager.persist(runner);
        entityManager.persist(owner);
        entityManager.persist(order);
        entityManager.persist(res);

    }

    @GET
    @Path("")
    public List<User> getRunner(){
        TypedQuery<User> query = entityManager
        .createQuery("select r from User r", User.class);
        return query.getResultList();
    }


    //working
    @GET
    @Path("order")
    public List<Orders> getOrder(){
        TypedQuery<Orders> query = entityManager
        .createQuery("select o from Orders o", Orders.class);
        return query.getResultList();
    }

    @GET
    @Path("res")
    public List<Restaurant> getRes(){
        TypedQuery<Restaurant> query = entityManager
        .createQuery("select o from Restaurant o", Restaurant.class);
        return query.getResultList();
    }

    @GET
    @Path("meal")
    public List<Meal> getMeal(){
        TypedQuery<Meal> query = entityManager
        .createQuery("select o from Meal o", Meal.class);
        return query.getResultList();
    }

    @GET
    @Path("restaurants")
    public Object getRestaurant() throws Exception{
        List<Restaurant> res =  roc.getRestaurant(1);
        return res;
    }
    
}
