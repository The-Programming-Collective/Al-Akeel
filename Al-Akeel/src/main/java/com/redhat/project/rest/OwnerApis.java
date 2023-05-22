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
@RolesAllowed("RESTUARANT_OWNER")
public class OwnerApis{
    @Resource
    EJBContext context;


    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;


    @Inject
    private RestaurantOwnerController restaurantOwnerController;


    @GET
    @Path("orders")
    public List<Orders> getOrdersList(@QueryParam("restaurant_id") int restaurant_id){
        return restaurantOwnerController.getOrders(restaurant_id);
    }


    @GET
    @Path("restaurant")
    public Restaurant getRestaurant(@QueryParam("restaurant_id") int restaurant_id) throws Exception{
        return restaurantOwnerController.getRestaurant(restaurant_id);
    }


    @POST
    @Path("meal")
    public boolean addMenuMeal(Wrapper<Integer,Meal> Obj){
        restaurantOwnerController.addMenuMeal(Obj.value1,Obj.value2);
        return true;
    }


    @DELETE
    @Path("meal")
    public boolean removeMenuMeal(@QueryParam("retaurant_id") int restaurant_id, @QueryParam("meal_id") int meal_id){
        restaurantOwnerController.removeMenuMeal(restaurant_id,meal_id);
        return true;
    }


    @POST
    @Path("meal")
    public boolean updateMeal(Wrapper<Wrapper<Integer,Integer>,Meal> obj){
        return restaurantOwnerController.updateMenuMeal(obj.value1.value1,obj.value1.value2,obj.value2);
    } 


    @POST
    @Path("menu")
    public boolean createMenu(Wrapper<Integer,Set<Meal>> obj){
        return restaurantOwnerController.setMenu(obj.value1,obj.value2);
    }

    
    @GET
    @Path("report")
    public Object getReport(@QueryParam("restaurant_id") int restaurant_id){
        return restaurantOwnerController.createReport(restaurant_id);
    }
   
} 