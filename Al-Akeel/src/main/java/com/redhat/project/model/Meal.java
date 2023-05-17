package com.redhat.project.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Meal implements Serializable {


    public Meal(){
        this.name=null;
        this.price=0;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
 
    
    // foreign keys
    // fk_restuarantId;
 
    //getters
    public int getId() {return id;}
    public String getName() {return name;}
    public double getPrice() {return price;}

    //setters
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}





}
