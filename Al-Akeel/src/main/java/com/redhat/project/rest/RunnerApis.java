package com.redhat.project.rest;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.redhat.project.model.Runner;
import com.redhat.project.services.RunnerController;
import com.redhat.project.util.Authenticator;



@Stateful
@Consumes("application/json")
@Produces("application/json")  
@Path("runner")
@RolesAllowed("RUNNER") 
public class RunnerApis {

    @Inject
    private RunnerController runnerController;

    @Inject
    Authenticator authenticator;


    @POST
    @Path("fees")
    public Boolean changeRunnerFees(@QueryParam("new_fees") Double new_fees){
        return runnerController.changeFees(new_fees);
    }


    @GET
    public Runner getRunner(){
        return (Runner)authenticator.authenticate();
    }


    @GET
    @Path("completedOrders")
    public Integer getCompletedOrders(){
        return runnerController.getCompletedOrdersCount();
    } 


    @GET
    @Path("completeOrder")
    public boolean completeOrder(){
        return runnerController.completeOrder();
    }
}