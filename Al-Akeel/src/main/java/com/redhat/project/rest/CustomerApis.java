package com.redhat.project.rest;


import java.util.List;
import java.util.Set;

import javax.annotation.security.RolesAllowed;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.redhat.project.model.Orders;
import com.redhat.project.model.Restaurant;
import com.redhat.project.services.CustomerController;
import com.redhat.project.util.Authenticator;
import com.redhat.project.util.CreditCardInfo;
import com.redhat.project.util.Wrapper;


@Stateful
@Consumes("application/json")
@Produces("application/json")  
@Path("/customer")
@RolesAllowed("CUSTOMER")
public class CustomerApis {
    
    @Inject
    CustomerController customerController;


    @Inject
    Authenticator authenticator;

    
    @GET
    @Path("restaurants")
    public List<Restaurant> getRestaurantsList(){
        return customerController.getRestaurants();
    }

    
    @GET
    @Path("orders")
    public Set<Orders> getOrders(){
        return authenticator.authenticate().getOrders();
    }


    @POST
    @Path("order")
    public Object createOrder(Wrapper<CreditCardInfo,Wrapper<Integer,Set<Integer>>> orderWrapper){
        try{
            return customerController.createOrder(orderWrapper.value1, orderWrapper.value2.value1, orderWrapper.value2.value2);
        }catch(Exception e){
            return false;
        }
    }

    
    @PUT
    @Path("order")
    public boolean editOrder(Wrapper<Integer,Set<Integer>> orderWrapper){
        return customerController.editOrder(orderWrapper.value1, orderWrapper.value2);
    }

}