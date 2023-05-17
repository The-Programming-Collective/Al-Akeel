package com.redhat.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

enum OrderStatus{
    PREPARING, DELIVERED, CANCELED
}


@Entity
public class Order implements Serializable {
    
    public Order(){
        this.itemsList = null;
        this.totalPrice = 0;
        this.orderStatus = OrderStatus.PREPARING;
        this.restuarant = null;
        this.runner = null;
    }


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    
    @NotNull
    private double totalPrice;
    
    @NotNull
    private OrderStatus orderStatus;
    
    @NotNull
    @OneToMany
    @JoinColumn(name = "meal_id")
    private List<Meal> itemsList;

    @ManyToOne
    @JoinColumn(name = "restuarant_id")
    Restuarant restuarant;

    @NotNull
    @OneToOne(mappedBy = "order")
    private User runner;



    //getters
    public int getId(){return this.id;}
    public List<Meal> getItemsList(){return this.itemsList;}
    public double getTotalPrice(){return this.totalPrice;}
    public OrderStatus getOrderStatus(){return this.orderStatus;}
    public Restuarant getRestuarant(){return this.restuarant;}
    public User getRunner(){return this.runner;}
    
    //setters
    public void setId(int id){this.id = id;}
    public void setItemsList(List<Meal> itemsList){this.itemsList = itemsList;}
    public void setTotalPrice(double totalPrice){this.totalPrice = totalPrice;}
    public void setOrderStatus(OrderStatus orderStatus){this.orderStatus = orderStatus;}
    public void setRestuarant(Restuarant restuarant){this.restuarant = restuarant;}
    public void setRunner(User runner){this.runner = runner;}

    public void addItem(Meal meal){this.itemsList.add(meal);}
    public void removeItem(Meal meal){this.itemsList.remove(meal);}   

}
