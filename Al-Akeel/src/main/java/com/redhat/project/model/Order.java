package com.redhat.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

enum OrderStatus{
    PREPARING, DELIVERED, CANCELED
}


@Entity
public class Order implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private List<Meal> itemsList;
    private double totalPrice;
    private OrderStatus orderStatus;



    // foreign keys
    // private int fk_runnerId;
    // private int fk_restuarantId;


    //getters
    public int getId(){return this.id;}
    public List<Meal> getItemsList(){return this.itemsList;}
    public double getTotalPrice(){return this.totalPrice;}
    public OrderStatus getOrderStatus(){return this.orderStatus;}
    
    //setters
    public void setId(int id){this.id = id;}
    public void setItemsList(List<Meal> itemsList){this.itemsList = itemsList;}
    public void setTotalPrice(double totalPrice){this.totalPrice = totalPrice;}
    public void setOrderStatus(OrderStatus orderStatus){this.orderStatus = orderStatus;}


    
}
