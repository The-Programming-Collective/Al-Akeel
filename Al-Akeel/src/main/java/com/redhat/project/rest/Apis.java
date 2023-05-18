package com.redhat.project.rest;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
public class Apis {
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

    @Inject
    private RestaurantOwnerController roc;
    
    @PostConstruct
    private void init(){
        Runner runner = new Runner();
        runner.setName("ahmed");
        
        User owner = new User();
        owner.setName("ali");
        owner.setRole(Role.RESTUARANT_OWNER);
        
        Restaurant res = new Restaurant("koshary el tahrir");
        res.setOwner(owner);
        
        Orders order = new Orders();
        order.setName("Order1");
        order.setRestaurant(res);
        order.setRunner(runner);
        
        Meal meal = new Meal("koshary",30,res);
        Meal meal2 = new Meal("Roz-blbn",10,res);
        res.addMeal(meal);
        res.addMeal(meal2);
        order.addItem(meal);
        order.addItem(meal2);

        // res.getMealsList().iterator().next().getName();
        
        entityManager.persist(owner);
        entityManager.persist(res);
        entityManager.persist(meal);
        entityManager.persist(meal2);
        entityManager.persist(runner);
        entityManager.persist(order);
    }


    @GET
    @Path("")
    public List<User> getRunnersList(){
        TypedQuery<User> query = entityManager
        .createQuery("select r from User r", User.class);
        return query.getResultList();
    }


    @GET
    @Path("order")
    public List<Orders> getOrdersList(){
        TypedQuery<Orders> query = entityManager
        .createQuery("select o from Orders o", Orders.class);
        return query.getResultList();
    }


    @GET
    @Path("res")
    public List<Restaurant> getRestaurantsList(){
        TypedQuery<Restaurant> query = entityManager
        .createQuery("select o from Restaurant o", Restaurant.class);
        return query.getResultList();
    }


    @GET
    @Path("meal")
    public List<Meal> getMealsList(){
        TypedQuery<Meal> query = entityManager
        .createQuery("select o from Meal o", Meal.class);
        return query.getResultList();
    }


    @GET
    @Path("restaurants")
    public Object getRestaurant(@QueryParam("restaurant_id") int restaurant_id, @QueryParam("owner_id") int owner_id) throws Exception{
        List<Restaurant> res =  roc.getRestaurant(restaurant_id,owner_id);
        return res;
    }


    @POST
    @Path("meal")
    public boolean addMenuMeal(Meal meal) throws Exception{
        roc.addMenuMeal(meal);
        return true;
    }


    @DELETE
    @Path("meal")
    public boolean removeMenuMeal(@QueryParam("meal_id") int meal_id){
        roc.removeMenuMeal(meal_id);
        return true;
    }


    @PUT
    @Path("meal")
    public boolean updateMeal(Object object){
        int meal_id = 1;
        System.out.println();
        // roc.updateMenuMeal(meal_id, meal);
        return true;
    } 


    @POST
    @Path("menu")
    public boolean createMenu(Set<Meal> mealsList){
        roc.setMenu(mealsList);
        return true;
    }

    
}
