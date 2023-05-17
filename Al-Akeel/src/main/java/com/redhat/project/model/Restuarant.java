package com.redhat.project.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Restuarant implements Serializable {
    public Restuarant(){
        this.name = null;
        this.lifeTimeEarnings = 0;
        this.mealsList = null;
        this.owner = null;  
    }

    public Restuarant(String name, User owner, ArrayList<Meal> mealsList){
        this.name = name;
        this.lifeTimeEarnings = 0;
        this.mealsList = mealsList;
        this.owner = owner;
    }

    public Restuarant(String name, User owner){
        this.name = name;
        this.lifeTimeEarnings = 0;
        this.mealsList = new ArrayList<Meal>();
        this.owner = owner;
    }

    
    
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;
    private double lifeTimeEarnings;
    
    @NotNull
    @OneToMany
    @JoinColumn(name = "meal_id")
    List<Meal> mealsList;

    @NotNull
    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    
   
    
    

    
    //getters
    public int getId() {return id;}
    public String getName() {return name;}
    public User getOwner() {return owner;}
    public List<Meal> getMealsList() {return mealsList;}
    public double getLifeTimeEarnings() {return lifeTimeEarnings;}

    
    //setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setOwner(User owner) {this.owner = owner;}
    public void setLifeTimeEarnings(double lifeTimeEarnings) {this.lifeTimeEarnings = lifeTimeEarnings;}
    

    public void addMeal(Meal meal){
        mealsList.add(meal);
    }

    public void removeMeal(Meal meal){
        mealsList.remove(meal);
    }
    
    
    
}
