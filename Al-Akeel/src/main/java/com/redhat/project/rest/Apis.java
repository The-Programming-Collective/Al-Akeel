package com.redhat.project.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
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
import com.redhat.project.model.Orders.OrderStatus;
import com.redhat.project.model.Runner.RunnerStatus;
import com.redhat.project.model.Restaurant;
import com.redhat.project.model.Runner;
import com.redhat.project.model.User;
import com.redhat.project.model.User.Role;
import com.redhat.project.services.CustomerController;
import com.redhat.project.services.RestaurantOwnerController;
import com.redhat.project.services.RunnerController;
import com.redhat.project.util.Authenticator;
import com.redhat.project.util.Wrapper;


@Stateless
@Consumes("application/json")
@Produces("application/json")  
@Path("/")
public class Apis {
    @Resource
    EJBContext context;

    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

    @Inject
    private CustomerController customerController;

    @Inject
    private Authenticator authenticator;


    @Inject
    private RestaurantOwnerController restaurantOwnerController;

    private boolean inited = false;

    @PermitAll
    @GET    
    @Path("initialization")
    public boolean initialization(){
        if(inited)
            return false;
        try{
            Runner runner = new Runner("test", "youssef");
            Runner runner2 = new Runner("fares", "faresKarim");
            runner.setDeliveryFees(69);
            runner2.setDeliveryFees(10);

            User owner = new User("mostafa", "mainUseless");
            owner.setRole(Role.RESTUARANT_OWNER);
            Restaurant res = new Restaurant("koshary el tahrir");
            Meal meal = new Meal("koshary",30.0,res);
            Meal meal2 = new Meal("Roz-blbn",10.0,res);
            res.setOwner(owner);
            res.addMeal(meal);
            res.addMeal(meal2);


            User owner2 = new User("kemol", "hecker");
            owner2.setRole(Role.RESTUARANT_OWNER);
            Restaurant res2 = new Restaurant("KFC");
            Meal meal3 = new Meal("Mighty zinger",200.0, res2);
            Meal meal4 = new Meal("twister",100.0, res2);
            res2.setOwner(owner2);
            res2.addMeal(meal3);
            res2.addMeal(meal4);
            

            Orders order = new Orders();
            order.setName("Order1");
            order.setRestaurant(res);
            order.setRunner(runner);
            order.addItem(meal);
            order.addItem(meal2);
            runner.addAssignedOrder(order);
            order.setOrderStatus(OrderStatus.DELIVERED);

            
            entityManager.persist(owner);
            entityManager.persist(owner2);
            entityManager.persist(res);
            entityManager.persist(res2);
            entityManager.persist(meal);
            entityManager.persist(meal2);
            entityManager.persist(meal3);
            entityManager.persist(meal4);
            entityManager.persist(runner);
            entityManager.persist(runner2);
            entityManager.persist(order);

            inited = true;
            return inited;
        }catch(Exception e){
            return false;
        }
    }



    // @GET
    // @Path("orders")
    // public List<Orders> getOrdersList(){
    //     User owner = authenticator.authenticate();
    //     return restaurantOwnerController.getOrders();
    // }


    @RolesAllowed("CUSTOMER")
    @GET
    @Path("restaurants")
    public List<Restaurant> getRestaurantsList(){
        return customerController.getRestaurants();
    }


    // @GET
    // @Path("meals")
    // public List<Meal> getMealsList(){
    //     TypedQuery<Meal> query = entityManager
    //     .createQuery("select o from Meal o", Meal.class);
    //     return query.getResultList();
    // }

    

    // @GET
    // @Path("runners")
    // public List<Runner> getRunnersList(){
    //     TypedQuery<Runner> query = entityManager
    //     .createQuery("select r from Runner r ", Runner.class);
    //     return query.getResultList();
    // }


    // @GET
    // @Path("users")
    // public List<User> getAllUsersList(){
    //     TypedQuery<User> query = entityManager
    //     .createQuery("select r from User r", User.class);
    //     return query.getResultList();
    // }
    

    // @GET
    // @Path("runner")
    // public Runner getRunner(@QueryParam("runner_id") int runner_id){
    //     return runnerController.getRunner(runner_id);
    // }
    
}
