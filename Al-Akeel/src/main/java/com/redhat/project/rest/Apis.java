package com.redhat.project.rest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.redhat.project.model.Meal;
import com.redhat.project.model.Orders;
import com.redhat.project.model.Orders.OrderStatus;
import com.redhat.project.model.Restaurant;
import com.redhat.project.model.Runner;
import com.redhat.project.model.User;
import com.redhat.project.model.User.Role;
import com.redhat.project.util.Authenticator;

@Stateless
@Consumes("application/json")
@Produces("application/json")  
@Path("/")
public class Apis {
    @Resource
    EJBContext context;

    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

    @Inject
    private Authenticator authenticator;

    private boolean inited = false;

    private Role loggedIn = null;

    private boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    
    @GET    
    @Path("initialization")
    public boolean initialization() throws IOException, InterruptedException{
        if(inited)
        return false;
        try{
            // Resets server users data
            new FileWriter("../domain/configuration/application-users.properties", false).close();
            new FileWriter("../standalone/configuration/application-users.properties", false).close();
     
            
            Runner runner = new Runner("d7k", "youssef",69);
            executeServerAdd("youssef","youssef",runner.getRole().toString());

            Runner runner2 = new Runner("mtboly", "test",10);
            executeServerAdd("test","test",runner2.getRole().toString());

            
            User owner = new User("mostafa", "mainUseless");
            owner.setRole(Role.RESTUARANT_OWNER);
            executeServerAdd("mainUseless","mainUseless",owner.getRole().toString());

            Restaurant res = new Restaurant("koshary el tahrir");
            Meal meal = new Meal("koshary",30.0,res);
            Meal meal2 = new Meal("Roz-blbn",10.0,res);
            res.setOwner(owner);
            res.addMeal(meal);
            res.addMeal(meal2);
            
            
            User owner2 = new User("kemol", "hecker");
            owner2.setRole(Role.RESTUARANT_OWNER);
            executeServerAdd("hecker","hecker",owner2.getRole().toString());

            Restaurant res2 = new Restaurant("KFC");
            Meal meal3 = new Meal("Mighty zinger",200.0, res2);
            Meal meal4 = new Meal("twister",100.0, res2);
            res2.setOwner(owner2);
            res2.addMeal(meal3);
            res2.addMeal(meal4);
            
            
            User customer = new User("faris","faris");
            customer.setRole(Role.CUSTOMER);
            executeServerAdd("faris","faris",customer.getRole().toString());
            
            
            Orders order = new Orders();
            order.setName("Order1");
            order.setRestaurant(res);
            order.setRunner(runner);
            order.addItem(meal);
            order.addItem(meal2);
            runner.addOrder(order);
            order.setOrderStatus(OrderStatus.DELIVERED);
            customer.addOrder(order);
            order.setCustomer(customer);
            order.setTotalPrice(Double.valueOf(40+10+69));
            

            entityManager.persist(customer);
            entityManager.persist(owner);
            entityManager.persist(owner2);
            entityManager.persist(res);
            entityManager.persist(res2);
            entityManager.persist(meal);
            entityManager.persist(meal2);
            entityManager.persist(meal3);
            entityManager.persist(meal4);
            entityManager.persist(runner);
            entityManager.persist(runner2);
            entityManager.persist(order);


            inited = true;
            return inited;
        }catch(Exception e){
            return false;
        }
    }
    
    
    //EAP_HOME/bin/add-user.bat -a -u "appuser1" -p "password1!" -g "guest"
    private void executeServerAdd(String userName,String password,String role) throws IOException, InterruptedException{
        System.out.println(role+"=================================");
        Process p;
        if(isWindows){
            p = Runtime.getRuntime().exec(String.format("cmd.exe /c add-user.bat -a -u %s -p %s -g %s", userName, password, role));
        }else{
            p = Runtime.getRuntime().exec(String.format("sh add-user.sh -a -u %s -p %s -g %s", userName, password, role));
        }
        Thread.sleep(1000);
        p.destroy();
    }


    // @GET
    // @Path("orders")
    // public Set<Orders> getOrdersList(){
    //     return customer.getOrders();
    // }


    // @GET
    // @Path("allorders")
    // public List<Orders> getaOrdersList(){
    //     TypedQuery<Orders> query = entityManager.createQuery("SELECT u from Orders u",Orders.class);
    //     // query.setParameter("user", customer);
    //     return query.getResultList();
    // }

    
    // @POST
    // @Path("order")
    // public Object createOrder(Wrapper<Integer,Set<Integer>> orderWrapper){
    //     // User customer = authenticator.authenticate();
    //     customerController.setCustomer(customer);
    //     try{
    //         return customerController.createOrder(orderWrapper.value1, orderWrapper.value2);
    //     }catch(Exception e){
    //         return false;
    //     }
    // }

    // @RolesAllowed("CUSTOMER")
    // @GET
    // @Path("restaurants")
    // public List<Restaurant> getRestaurantsList(){
    //     return customerController.getRestaurants();
    // }


    // @GET
    // @Path("meals")
    // public List<Meal> getMealsList(){
    //     TypedQuery<Meal> query = entityManager
    //     .createQuery("select o from Meal o", Meal.class);
    //     return query.getResultList();
    // }


    // @GET
    // @Path("runners")
    // public List<Runner> getRunnersList(){
    //     TypedQuery<Runner> query = entityManager
    //     .createQuery("select r from Runner r ", Runner.class);
    //     return query.getResultList();
    // }


    @GET
    @Path("users")
    public List<User> getAllUsersList(){
        TypedQuery<User> query = entityManager
        .createQuery("select r from User r", User.class);
        return query.getResultList();
    }
    

    // @GET
    // @Path("runner")
    // public Runner getRunner(@QueryParam("runner_id") int runner_id){
    //     return runnerController.getRunner(runner_id);
    // }


    @GET    
    @Path("login")
    public User login(){
        User user = authenticator.authenticate();
        Role role = user.getRole();
        switch (role){
            case CUSTOMER:
                this.loggedIn = Role.CUSTOMER;
                // this.customerController.setCustomer(user);
                break;
            case RUNNER:
                this.loggedIn = Role.RUNNER;
                break;
            case RESTUARANT_OWNER:
                this.loggedIn = Role.RESTUARANT_OWNER;
                break;
            default:
                break;
        }
        return user;
    }


    @PUT
    @Path("logout")
    public boolean logout(){
        try{
            this.loggedIn = null;
            return true;
        }catch(Exception e){
            return false;
        }
    }

    
    @POST
    @Path("signup")
    public boolean signup(Map<String,String> map)throws Exception{
        TypedQuery<User> query = entityManager.createNamedQuery("getUser", User.class);
        query.setParameter("userName", map.get("userName"));
        List<User> users = query.getResultList();   
        if(users.size() > 0)
            throw new Exception("User already exists"); 

        String name = map.get("name");
        String userName = map.get("userName");
        String password = map.get("password");
        String role = map.get("role");

        User user ;
        
        if(Role.valueOf(role)==Role.RUNNER){
            String stringFees = map.get("fees");
            Double fees = 0.0;
           
            if(stringFees!=null){fees = Double.valueOf(stringFees);}

            user = new Runner(name, userName,fees);
        }else{
            user = new User(name, userName);
        }

        user.setRole(Role.valueOf(role));
        entityManager.persist(user);

        executeServerAdd(userName, password, role);
        
        return true;
    }


}
