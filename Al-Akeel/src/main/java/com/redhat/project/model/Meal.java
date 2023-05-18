package com.redhat.project.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Meal implements Serializable {

    // Default
    public Meal(){}
    public Meal(String name , double price, Restaurant restaurant){
        this.name = name ;
        this.price = price;
        this.restaurant = restaurant;
        this.avaliable = true;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private boolean avaliable;

    public boolean isAvaliable() {
        return avaliable;
    }
    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }

    // @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "itemsList")
    private Set<Orders> order;
    // foreign keys
    // fk_restuarantId;

    
    // Getters
    public int getId() {return id;}
    public String getName() {return name;}
    public double getPrice() {return price;}
    public int getRestaurant() {return restaurant.getId();}
    
    // Setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}
    public void setRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}
    
    
}