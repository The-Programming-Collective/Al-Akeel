package com.redhat.project.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.redhat.project.model.Orders;
import com.redhat.project.model.Restaurant;
import com.redhat.project.model.Runner;
import com.redhat.project.model.User;


@Stateless
@Consumes("application/json")
@Produces("application/json")   
@Path("/")
public class test {
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;


    @POST
    @Path("")
    public void persistUser(){
        User user = new Runner();
        user.setName("ahmed");
        Orders order = new Orders();
        order.setName("testOrder");
        Restaurant res = new Restaurant("koshary el tahrir", null);
        // order.setRestaurant(res);
        entityManager.persist(user);
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

}
