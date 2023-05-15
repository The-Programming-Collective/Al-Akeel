package com.redhat.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Restuarant implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private int ownderId;
    List<Meal> mealsList;
    private double lifeTimeEarnings;
    

    
    //getters
    public int getId() {return id;}
    public String getName() {return name;}
    public int getOwnderId() {return ownderId;}
    public List<Meal> getMealsList() {return mealsList;}
    public double getLifeTimeEarnings() {return lifeTimeEarnings;}
    
    //setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setOwnderId(int ownderId) {this.ownderId = ownderId;}
    public void setMealsList(List<Meal> mealsList) {this.mealsList = mealsList;}
    public void setLifeTimeEarnings(double lifeTimeEarnings) {this.lifeTimeEarnings = lifeTimeEarnings;}
    
    
}
