package com.redhat.project.rest;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.redhat.project.model.Runner;
import com.redhat.project.model.User;
import com.redhat.project.services.RunnerController;
import com.redhat.project.util.Authenticator;



@Stateful
@Consumes("application/json")
@Produces("application/json")  
@Path("runner")
public class RunnerApis {

    @Inject
    private RunnerController runnerController;

    @Inject
    Authenticator authenticator;


    @RolesAllowed("RUNNER")
    @PUT
    @Path("fees")
    public Boolean changeRunnerFees(@QueryParam("new_fees") Double new_fees){
        return runnerController.changeFees(new_fees);
    }


    @RolesAllowed("RUNNER")
    @GET
    @Path("runner")
    public Runner getRunner(){
        User runner = authenticator.authenticate();
        return runnerController.getRunner(runner.getId());
    }


    @RolesAllowed("RUNNER") 
    @GET
    @Path("completedOrders")
    public Map<String,Integer> getCompletedOrders(){
        Map<String,Integer> map = new HashMap<String,Integer>();
        map.put("completedOrders", runnerController.getCompletedOrdersCount());
        return map;
    } 


    @RolesAllowed("RUNNER")
    @PUT
    @Path("completeOrder")
    public Boolean completeOrder(){
        runnerController.completeOrder();
        return true;
    }
}