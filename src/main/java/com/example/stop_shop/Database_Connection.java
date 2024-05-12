package com.example.stop_shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Database_Connection {

    String dburl = "jdbc:mysql://localhost:3306/stopandshop";
    String U_name = "root";
    String password = "Aryan@123";

    private Statement getStatement() {

        try{
            Connection conn = DriverManager.getConnection(dburl,U_name,password);
            return conn.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertUpdate(String query){
        Statement statement = getStatement();
        if(statement == null) return false;
        try{
            int result =  statement.executeUpdate(query);
            return result>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }
    public boolean executeUpdate(String query){
        Statement statement = getStatement();
        if(statement == null) return false;
        try{
            int result =  statement.executeUpdate(query);
            if(result > 0) return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getQueryTable(String query){
        Statement statement =  getStatement();
        try{
            return statement.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        Database_Connection dbcon = new Database_Connection();
        String query = "select product_name,price,product_img from products;";
        ResultSet resultSet = dbcon.getQueryTable(query);
        System.out.println(dbcon.insertUpdate("INSERT INTO in_cart VALUES('Aryan','Jeans-1')"));
        ResultSet rs = dbcon.getQueryTable(query);
        if(rs!=null){
            System.out.println("Connected to Database");
        }

    }
}
