package com.redhat.project.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// Create order by customer
// Exceptations: imagine a normal restaurant receipt

// Order details should contains date ,restaurant name, items list , delivery fees,
// runner name, total receipt value (summation of items prices , delivery fees )
// list orders by customer id
// When creating an order select a random available runner from db and assign it to
// an order and convert his status to busy
// Edit order [change an order’s items] make sure an order is not canceled and it is
// in the preparing state to be edited
// List all restaurants

@Stateless 
public class CustomerController {
    
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;



    public void createOrder(){

    }

}