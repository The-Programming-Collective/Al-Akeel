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

import com.redhat.project.model.*;


@Stateless
@Consumes("application/json")
@Produces("application/json")   
@Path("/")
public class test {
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;


    @POST
    @Path("persist")
    public void persistUser(){
        Runner runner = new Runner();
        runner.setName("test");
        Order order = new Order();
        order.setRunner(runner);
        order.setTotalPrice(2.5);
        order.setRestuarant(new Restuarant());
        runner.setOrder(order);

        entityManager.persist(runner);
        entityManager.persist(order);
    }

    @GET
    @Path("get")
    public List<Runner> getRunner(){
        TypedQuery<Runner> query = entityManager
        .createQuery("select r from Runner r", Runner.class);
        return query.getResultList();
    }


}
