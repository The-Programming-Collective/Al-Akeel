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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
    @NamedQuery(name = "getRestaurant", query = "SELECT r from Restaurant r where r.id = :res_id and r.owner.id = :owner_id"),
    @NamedQuery(name = "getRestaurants", query = "select o from Restaurant o"),
})
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;
    
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
    private Set<Meal> mealsList;
    
    @NotNull
    @ManyToOne
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
        this.mealsList = new HashSet<Meal>();
    }
    
    
    // Getters
    public int getId() {return id;}
    public String getName() {return name;}
    public Set<Meal> getMealsList() { 
        Set<Meal> returnedSet = new HashSet<Meal>();

        for (Meal item : mealsList) {
            if(item.isAvaliable()){
                returnedSet.add(item);
            }
        }

        return returnedSet;
    }
    public User getOwner() {return owner;}
    
    
    // Setters
    public void setId(int id) {this.id = id;}
    public void setOwner(User owner) {this.owner = owner;}
    public void setName(String name) {this.name = name;}
    public void setMealsList(Set<Meal> mealsList){this.mealsList = mealsList;}
    
    public void addMeal(Meal meal){mealsList.add(meal);}
    public void removeMeal(Meal meal){meal.setAvaliable(false);mealsList.remove(meal);}
    
}
