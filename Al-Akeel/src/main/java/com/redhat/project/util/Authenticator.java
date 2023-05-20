package com.redhat.project.util;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.redhat.project.model.User;

@Stateless
public class Authenticator {
        

    @Resource
    EJBContext context;

    @PersistenceContext(unitName = "persistUnit")
    EntityManager entityManager;

    public User authenticate(){
        try{
            String name = context.getCallerPrincipal().getName();
            TypedQuery<User> query = entityManager.createQuery("SELECT u from User u where u.userName = :userName",User.class);
            query.setParameter("userName", name);
            User user = query.getResultList().get(0);
            return user;
        }catch(Exception e){
            return null;
        }
    }
    
}