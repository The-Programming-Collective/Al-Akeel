package com.redhat.project.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test {
    /**
    * Connect to a sample database
    */
   public static void connect() {
       Connection conn = null;
       try {
            // db parameters
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            String sql = "select * from test";
            java.sql.Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery(sql);

            while(res.next()){
                System.out.println(res.getString("name"));
            }
            
            // ResultSet res2 = statement.executeQuery("create table lol");
            System.out.println("Connection to SQLite has been established.");
           
       } catch (SQLException e) {
           System.out.println(e.getMessage());
           System.out.println("bug");
       } finally {
           try {
               if (conn != null) {
                   conn.close();
               }
           } catch (SQLException ex) {
               System.out.println(ex.getMessage());
           }
       }
   }
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       connect();
   }
}
