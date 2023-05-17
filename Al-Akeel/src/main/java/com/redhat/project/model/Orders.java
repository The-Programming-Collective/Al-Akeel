package com.redhat.project.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

enum OrderStatus{
    PREPARING, DELIVERED, CANCELED
}

@Entity
public class Orders implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double totalPrice;
    private OrderStatus orderStatus;
    private ArrayList<Meal> itemsList;
    private Restaurant restaurant;
    private Runner runner;

    public Runner getRunner() {
        return runner;
    }
    public void setRunner(Runner runner) {
        this.runner = runner;
    }
    public Restaurant getRestaurant() {
        return restaurant;
    }
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    public ArrayList<Meal> getItemsList() {
        return itemsList;
    }
    public void setItemsList(ArrayList<Meal> itemsList) {
        this.itemsList = itemsList;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}