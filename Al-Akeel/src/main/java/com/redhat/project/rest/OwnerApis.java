package com.redhat.project.rest;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import com.redhat.project.services.RestaurantOwnerController;

import com.redhat.project.util.Wrapper;


@Stateful
@Consumes("application/json")
@Produces("application/json")  
@Path("/owner")
@RolesAllowed("OWNER")
public class OwnerApis{
    @Resource
    EJBContext context;


    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;


    @Inject
    private RestaurantOwnerController restaurantOwnerController;


    @GET
    @Path("orders")
    public List<Orders> getOrdersList(){
        return restaurantOwnerController.getOrders();
    }


    @GET
    @Path("restaurant")
    public Restaurant getRestaurant(@QueryParam("restaurant_id") int restaurant_id) throws Exception{
        return restaurantOwnerController.getRestaurant(restaurant_id);
    }


    @POST
    @Path("meal")
    public boolean addMenuMeal(Meal meal){
        restaurantOwnerController.addMenuMeal(meal);
        return true;
    }


    @DELETE
    @Path("meal")
    public boolean removeMenuMeal(@QueryParam("meal_id") int meal_id){
        restaurantOwnerController.removeMenuMeal(meal_id);
        return true;
    }


    @PUT
    @Path("meal")
    public boolean updateMeal(Wrapper<Integer,Meal> obj){
        restaurantOwnerController.updateMenuMeal(obj.value1, obj.value2);
        return true;
    } 


    @POST
    @Path("menu")
    public boolean createMenu(Set<Meal> mealsList){
        restaurantOwnerController.setMenu(mealsList);
        return true;
    }

    
    @GET
    @Path("report")
    public Object getReport(){
        return restaurantOwnerController.createReport();
    }
   
} 