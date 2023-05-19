package com.redhat.project.services;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// Runner can mark an order is delivered and his status to available
// Get number of trips completed by a runner make sure orders are not canceled
// and marked as completed


@Stateless 
public class RunnerController {
    
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

}