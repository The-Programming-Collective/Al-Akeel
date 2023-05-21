package com.redhat.project.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@NamedQueries({
    @NamedQuery(name="getUser",query="SELECT u from User u where u.userName = :userName"),

})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"userName"}))
@Entity
public class User implements Serializable {
    public enum Role{CUSTOMER, RESTUARANT_OWNER, RUNNER}
    
    public User() {}
    public User(String name, String userName){
        this.name = name;
        this.userName = userName;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private Role role = Role.CUSTOMER;
    
    @Column(name = "userName")
    private String userName;

    
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Restaurant> restaurant;


    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    protected Set<Orders> orders = new LinkedHashSet<Orders>();


    // Getters
    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public Role getRole(){return this.role;}
    public String getUserName(){return this.userName;}
    public Set<Orders> getOrders(){return this.orders;}
    

    // Setters
    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setRole(Role role){this.role = role;}

    public void addOrder(Orders order){this.orders.add(order);}

}
