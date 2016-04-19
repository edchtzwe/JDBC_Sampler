/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sampler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edmund
 */
public class Sampler {

    static String URL = "jdbc:mysql://localhost/SAMPLER";
    static String username = "root";
    static String password = "root";
    static Connection conn = null;
    static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static Statement stat = null;
    
    public static void connectDatabase()
    {        
        try{
            conn = DriverManager.getConnection(URL, username, password);            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }        
    }
    
    public static void createDatabase()
    {
        String sql = "CREATE DATABASE SAMPLER";
        
        try{
            stat = conn.createStatement();                        
            stat.execute(sql);
        } catch (SQLException ex) {        
            System.err.println("Database already exists.");
        }        
    }
    
    public static void createTable()
    {
        String sql = "CREATE TABLE REGISTRATION " +
                   "(id INTEGER not NULL, " +
                   " first VARCHAR(255), " + 
                   " last VARCHAR(255), " + 
                   " age INTEGER, " + 
                   " PRIMARY KEY ( id ))"; 
        try {    
            stat = conn.createStatement();
            stat.executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println("Table created.");
        }        
    }
    
    public static void insertRecord()
    {
        try {
            String sql = "INSERT INTO Registration " +
                    "VALUES (100, 'Zara', 'Ali', 18)";
            stat.executeUpdate(sql);            
        } catch (SQLException ex) {
            System.err.println("Record duplicate found!");
        }
        try{
        String sql = "INSERT INTO Registration " +
                    "VALUES (200, 'Sarah', 'Alih', 20)";
            stat.executeUpdate(sql);
        }
        catch(SQLException ex){
            System.err.println(ex.toString());
        }
        try{
        String sql = "INSERT INTO Registration " +
                    "VALUES (300, 'Darah', 'Alih', 23)";
            stat.executeUpdate(sql);
        }
        catch(SQLException ex){
            System.err.println(ex.toString());
        }       
    }
    
    public static void sortRecord()
    {
        try {
            String sql = "SELECT id, first, last, age FROM Registration" +
                    " ORDER BY first DESC";
            ResultSet rs = stat.executeQuery(sql);
            System.out.println("Sort record.");
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");
                
                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public static void selectRecord()
    {
        try {
            String sql = "SELECT * FROM Registration"; // WHERE id=100";
            ResultSet rs = stat.executeQuery(sql);
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");
                
                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public static void selectLike()
    {
        try {
            String sql = "SELECT id, first, last, age FROM Registration" +
                    " WHERE first LIKE '%za%' ";
            ResultSet rs = stat.executeQuery(sql);
            System.out.println("Select like");
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");
                
                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + age);
                System.out.print(", First: " + first);
                System.out.println(", Last: " + last);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Sampler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateRecord()
    {
        try {
            String sql = "UPDATE Registration " +
                    "SET age = 30 WHERE id in (100, 101)";
            stat.executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public static void deleteRecord()
    {
        String sql = "DELETE FROM Registration " +
                   "WHERE id = 200";
        try {
            stat.executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        
        //connect to the database
        connectDatabase();        
        
        //database connected, setup database
        createDatabase();
        
        //make a table
        createTable();
        
        //insert record
        insertRecord();
        
        //select statement
        selectRecord();
        
        //update statement
        updateRecord();
        selectRecord();
        
        //delete statement
        deleteRecord();
        selectRecord();
        
        //select like
        selectLike();
        
        //sort
        sortRecord();
        
        //all done, close connection
        try {            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
}
