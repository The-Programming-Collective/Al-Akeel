package com.redhat.project.model;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "getOrders" , query = "SELECT r from Orders r where r.restaurant.id = :restaurant_id"),
})
public class Orders implements Serializable{
    public enum OrderStatus{PREPARING, DELIVERED, CANCELED}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private OrderStatus orderStatus;
    private String date;   
    private Double totalPrice=0.0; 
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "MealXOrder",
        joinColumns = @JoinColumn(name="order_id"),
        inverseJoinColumns = @JoinColumn(name ="meal_id")
        )
    private Set<Meal> itemsList;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "runner_id")
    private Runner runner;

    public Orders(){
        this.itemsList = new HashSet<Meal>();
        this.orderStatus = OrderStatus.PREPARING;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dateTimeStamp = new Date();
        this.date = formatter.format(dateTimeStamp);
    }

    // Getters
    public int getId() {return id;}
    public Runner getRunner() {return runner;}
    public int getRestaurant() {return restaurant.getId();}
    public Set<Meal> getItemsList() {return itemsList;}

    public double getTotalPrice() {
        if(orderStatus != OrderStatus.DELIVERED){
            totalPrice =  0.0;
            for(Meal meal : itemsList){
                totalPrice+= meal.getPrice();
            }
            totalPrice+=runner.getDeliveryFees();
        }
        return totalPrice;
    }
    
    public OrderStatus getOrderStatus() {return orderStatus;}
    public String getName() {return name;}
    public String getDate() {return date;}

    // Setters
    public void setRunner(Runner runner) {this.runner = runner;}
    public void setRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}
    public void setItemsList(HashSet<Meal> itemsList) {this.itemsList = itemsList;}
    public void setOrderStatus(OrderStatus orderStatus) {this.orderStatus = orderStatus;}
    public void setName(String name) {this.name = name;}
    public void setTotalPrice(Double totalPrice) { this.totalPrice = totalPrice;}

    public void addItem(Meal meal){itemsList.add(meal);}
    public void removeItem(Meal meal){itemsList.remove(meal);}

}