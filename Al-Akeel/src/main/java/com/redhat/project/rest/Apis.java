package com.redhat.project.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
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
import com.redhat.project.util.Wrapper;


@Stateless
@Consumes("application/json")
@Produces("application/json")  
@Path("/")
public class Apis {
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

    @Inject
    private RestaurantOwnerController restaurantOwnerController;

    @Inject
    private RunnerController runnerController;

    @Inject
    private CustomerController customerController;
    
    @PostConstruct
    private void init(){
        Runner runner = new Runner();
        runner.setName("ahmed");
        runner.setRunnerStatus(RunnerStatus.BUSY);
        
        User owner = new User();
        owner.setName("ali");
        owner.setRole(Role.RESTUARANT_OWNER);
        
        Restaurant res = new Restaurant("koshary el tahrir");
        res.setOwner(owner);
        
        Orders order = new Orders();
        order.setName("Order1");
        order.setRestaurant(res);
        order.setRunner(runner);
        order.setOrderStatus(OrderStatus.DELIVERING);
        
        Meal meal = new Meal("koshary",30.0,res);
        Meal meal2 = new Meal("Roz-blbn",10.0,res);
        res.addMeal(meal);
        res.addMeal(meal2);
        order.addItem(meal);
        order.addItem(meal2);

        // res.getMealsList().iterator().next().getName();
        
        entityManager.persist(owner);
        entityManager.persist(res);
        entityManager.persist(meal);
        entityManager.persist(meal2);
        entityManager.persist(runner);
        entityManager.persist(order);
    }


    @GET
    @Path("orders")
    public List<Orders> getOrdersList(){
        TypedQuery<Orders> query = entityManager
        .createQuery("select o from Orders o", Orders.class);
        return query.getResultList();
    }


    @GET
    @Path("restaurants")
    public List<Restaurant> getRestaurantsList(){
        TypedQuery<Restaurant> query = entityManager
        .createQuery("select o from Restaurant o", Restaurant.class);
        return query.getResultList();
    }


    @GET
    @Path("meals")
    public List<Meal> getMealsList(){
        TypedQuery<Meal> query = entityManager
        .createQuery("select o from Meal o", Meal.class);
        return query.getResultList();
    }


    @GET
    @Path("restaurant")
    public Object getRestaurant(@QueryParam("restaurant_id") int restaurant_id, @QueryParam("owner_id") int owner_id) throws Exception{
        List<Restaurant> res =  restaurantOwnerController.getRestaurant(restaurant_id,owner_id);
        return res;
    }


    @POST
    @Path("meal")
    public boolean addMenuMeal(Meal meal){
        System.out.println(meal.getName());
        System.out.println(meal.getPrice());

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
    

    @GET
    @Path("runners")
    public List<Runner> getRunnersList(){
        TypedQuery<Runner> query = entityManager
        .createQuery("select r from Runner r ", Runner.class);
        return query.getResultList();
    }


    @GET
    @Path("users")
    public List<User> getAllUsersList(){
        TypedQuery<User> query = entityManager
        .createQuery("select r from User r", User.class);
        return query.getResultList();
    }
    

    @GET
    @Path("runner")
    public Runner getRunner(@QueryParam("runner_id") int runner_id){
        return runnerController.getRunner(runner_id);
    }


    @GET
    @Path("completedOrders")
    public Map<String,Long> getCompletedOrders(){
        Map<String,Long> map = new HashMap<>();
        map.put("completedOrders", runnerController.getCompletedOrdersCount());
        return map;
    } 


    @PUT
    @Path("completeOrder")
    public Boolean completeOrder(){
        runnerController.completeOrder();
        return true;
    }
    
    
}
