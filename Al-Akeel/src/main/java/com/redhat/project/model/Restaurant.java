package com.redhat.project.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Restaurant implements Serializable {

    public Restaurant(){}
    public Restaurant(String name,  ArrayList<Meal> mealsList){
        this.name = name;
        this.lifeTimeEarnings = 0;
        this.mealsList = mealsList;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;
    private double lifeTimeEarnings;
    
    // @NotNull
    // @OneToMany
    // @JoinColumn(name = "meal_id")
    private ArrayList<Meal> mealsList;

    @NotNull
    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    
    // Getters
    public String getName() {return name;}
    public double getLifeTimeEarnings() {return lifeTimeEarnings;}
    public ArrayList<Meal> getMealsList() {return mealsList;}
    public User getOwner() {return owner;}
    

    // Setters
    public void setOwner(User owner) {this.owner = owner;}
    public void setName(String name) {this.name = name;}
    public void setLifeTimeEarnings(double lifeTimeEarnings) {this.lifeTimeEarnings = lifeTimeEarnings;}
    public void setMealsList(ArrayList<Meal> mealsList) {this.mealsList = mealsList;}
    
    public void addMeal(Meal meal){mealsList.add(meal);}
    public void removeMeal(Meal meal){mealsList.remove(meal);}
    
}
