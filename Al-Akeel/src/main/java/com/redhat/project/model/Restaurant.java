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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
//SELECT e from Employee e where e.salary >:sal
@Entity
@NamedQuery(name = "getRestaurant", query = "SELECT r from Restaurant r where r.owner.id =:owner_id")
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;
    private double lifeTimeEarnings;
    
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private Set<Meal> mealsList;
    
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
    }
    
    public Restaurant(String name){
        this.name = name;
        this.lifeTimeEarnings = 0;
        this.mealsList = new HashSet<Meal>();
    }
    
    
    // Getters
    public int getId() {return id;}
    public String getName() {return name;}
    public double getLifeTimeEarnings() {return lifeTimeEarnings;}
    public Set<Meal> getMealsList() {return mealsList;}
    public User getOwner() {return owner;}
    
    
    // Setters
    public void setId(int id) {this.id = id;}
    public void setOwner(User owner) {this.owner = owner;}
    public void setName(String name) {this.name = name;}
    public void setLifeTimeEarnings(double lifeTimeEarnings) {this.lifeTimeEarnings = lifeTimeEarnings;}
    
    public void addMeal(Meal meal){mealsList.add(meal);}
    public void removeMeal(Meal meal){mealsList.remove(meal);}
    
}
