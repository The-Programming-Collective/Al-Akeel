package com.redhat.project.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;

@Entity
public class Orders implements Serializable{
    public enum OrderStatus{PREPARING, DELIVERED, CANCELED}
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double totalPrice;
    private OrderStatus orderStatus;

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

    @OneToOne
    @JoinColumn(name = "runner_id")
    private Runner runner;

    public Orders(){
        this.itemsList = new HashSet<Meal>();
        this.orderStatus = OrderStatus.PREPARING;
    }

    // Getters
    public Runner getRunner() {return runner;}
    public int getRestaurant() {return restaurant.getId();}
    public Set<Meal> getItemsList() {return itemsList;}
    public double getTotalPrice() {return totalPrice;}
    public OrderStatus getOrderStatus() {return orderStatus;}
    public String getName() {return name;}

    // Setters
    public void setRunner(Runner runner) {this.runner = runner;}
    public void setRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}
    public void setItemsList(HashSet<Meal> itemsList) {this.itemsList = itemsList;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}
    public void setOrderStatus(OrderStatus orderStatus) {this.orderStatus = orderStatus;}
    public void setName(String name) {this.name = name;}

    public void addItem(Meal meal){itemsList.add(meal);}
    public void removeItem(Meal meal){itemsList.remove(meal);}

}