package com.redhat.project.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private ArrayList<Meal> itemsList;
    private Restaurant restaurant;

    @OneToOne(mappedBy = "order")
    private Runner runner;

    // Getters
    public Runner getRunner() {return runner;}
    public Restaurant getRestaurant() {return restaurant;}
    public ArrayList<Meal> getItemsList() {return itemsList;}
    public double getTotalPrice() {return totalPrice;}
    public OrderStatus getOrderStatus() {return orderStatus;}
    public String getName() {return name;}

    // Setters
    public void setRunner(Runner runner) {this.runner = runner;}
    public void setRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}
    public void setItemsList(ArrayList<Meal> itemsList) {this.itemsList = itemsList;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}
    public void setOrderStatus(OrderStatus orderStatus) {this.orderStatus = orderStatus;}
    public void setName(String name) {this.name = name;}
}