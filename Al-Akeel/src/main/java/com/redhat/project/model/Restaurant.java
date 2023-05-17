package com.redhat.project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;
    private double lifeTimeEarnings;

    @OneToMany(mappedBy = "restaurant",fetch = FetchType.EAGER)
    private Set<Meal> mealsList;
    private ArrayList<Meal> mealsListArray;

    @NotNull
    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private Set<Orders> orders;
    
    // Default
    public Restaurant(){
        this.orders = new HashSet<Orders>();
        this.mealsList = new HashSet<Meal>();
        this.mealsListArray = new ArrayList<>(mealsList);
    }

    public Restaurant(String name){
        this.name = name;
        this.lifeTimeEarnings = 0;
        this.mealsList = new HashSet<Meal>();
        this.mealsListArray = new ArrayList<>(mealsList);
    }
    

    // Getters
    public String getName() {return name;}
    public double getLifeTimeEarnings() {return lifeTimeEarnings;}
    public ArrayList<Meal> getMealsList() {return mealsListArray;}
    public User getOwner() {return owner;}
    

    // Setters
    public void setOwner(User owner) {this.owner = owner;}
    public void setName(String name) {this.name = name;}
    public void setLifeTimeEarnings(double lifeTimeEarnings) {this.lifeTimeEarnings = lifeTimeEarnings;}
    // public void setMealsList(HashSet<Meal> mealsList) {this.mealsList = mealsList;}
    public void setMealsListArray(ArrayList<Meal> mealsList) {this.mealsListArray = mealsList;}
    
    public void addMeal(Meal meal){mealsListArray.add(meal);}
    public void removeMeal(Meal meal){mealsListArray.remove(meal);}
    
}
