/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fyp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Muhammad Ali
 */
public class DataAccess {
    
    Connection conn;
    Statement stmt;
    static final String DB_URL = "jdbc:mysql://localhost/FYP";
    static final String USER = "guest";
    static final String PASS = "guest123";
 
    static final String CREATE_STD_TABLE = "CREATE TABLE IF NOT EXISTS students(id INTEGER PRIMARY KEY, name TEXT, password TEXT, project_id INTEGER, supervisor_id INTEGER)";
    static final String CREATE_SUP_TABLE = "CREATE TABLE IF NOT EXISTS supervisors(id INTEGER PRIMARY KEY, name TEXT, password TEXT)";
    static final String CREATE_PROJECTS_TABLE = "CREATE TABLE IF NOT EXISTS projects(id INTEGER PRIMARY KEY AUTO_INCREMENT, name TEXT NOT NULL)";

    public DataAccess(){
         connectDB();
    }

    public void connectDB(){
        try{
//            Class.forName("com.mysql.jdbc.Driver");   
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
//            deleteTables();
            stmt.executeUpdate(CREATE_STD_TABLE);
            stmt.executeUpdate(CREATE_SUP_TABLE);
            stmt.executeUpdate(CREATE_PROJECTS_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet read_students(){
        ResultSet rs = null;
        try{
            PreparedStatement  read = conn.prepareStatement("SELECT * FROM students");
            rs = read.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
      return rs;
    }

    public ResultSet read_supervisors(){
        ResultSet rs = null;
        try{
            PreparedStatement  read = conn.prepareStatement("SELECT * FROM supervisors");
            rs = read.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
      return rs;
    }

    public ResultSet read_projects(){
        ResultSet rs = null;
        try{
            PreparedStatement  read = conn.prepareStatement("SELECT * FROM projects");
            rs = read.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
      return rs;
    }

    public String getAdminPassword(int user_id){
        if(user_id == 1){
            return "admin";
        }else{
        return "no";
        }
    }

    public void add_student(int id, String name, String password){
        try{
            PreparedStatement  insert = conn.prepareStatement("insert into students(id,name,password)values(?,?,?)");
            insert.setInt(1, id);
            insert.setString(2, name);
            insert.setString(3, password);
            insert.executeUpdate();
            }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void update_student(int id, String name){
    try{
            PreparedStatement  update = conn.prepareStatement("UPDATE students SET name=? WHERE id=?");
            update.setString(1, name);
            update.setInt(2, id);
            update.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void delete_student(int id){
        try{
            PreparedStatement  delete = conn.prepareStatement("DELETE FROM students WHERE id=?");
            delete.setInt(1, id);
            delete.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet search_student(int id){
        ResultSet rs = null;        
        try{
            PreparedStatement  search = conn.prepareStatement("SELECT * FROM students WHERE id=?");
            search.setInt(1, id);
            rs = search.executeQuery();
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet search_student(String name){
        ResultSet rs = null;        
        try{
            PreparedStatement  search = conn.prepareStatement("SELECT * FROM students WHERE name=?");
            search.setString(1, name);
            rs = search.executeQuery();
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet search_supervisor(int id){
        ResultSet rs = null;        
        try{
            PreparedStatement  search = conn.prepareStatement("SELECT * FROM supervisors WHERE id=?");
            search.setInt(1, id);
            rs = search.executeQuery();
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet search_supervisor(String name){
        ResultSet rs = null;        
        try{
            PreparedStatement  search = conn.prepareStatement("SELECT * FROM supervisors WHERE name=?");
            search.setString(1, name);
            rs = search.executeQuery();
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkLogin(int user_id, String password, String type){
    try{
    if(type.equals("std")){
         ResultSet std = search_student(user_id);
         if(std.next() && std.getString("password").equals(password)){
             return true;
         }
    }else if(type.equals("sup")){
        ResultSet sup = search_supervisor(user_id);         
        if(sup.next() && sup.getString("password").equals(password)){
             return true;
         }
    }else if (type.equals("admin")){
        if(getAdminPassword(user_id).equals(password)){
             return true;
         }
    }
    }catch(SQLException e){
            e.printStackTrace();
    }
    return false;
    }

public void add_supervisor(int id, String name, String password){
        try{
            PreparedStatement  insert = conn.prepareStatement("insert into supervisors(id,name,password)values(?,?,?)");
            insert.setInt(1, id);
            insert.setString(2, name);
            insert.setString(3, password);
            insert.executeUpdate();
            }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void update_supervisor(int id, String name){
    try{
            PreparedStatement  update = conn.prepareStatement("UPDATE supervisors SET name=? WHERE id=?");
            update.setString(1, name);
            update.setInt(2, id);
            update.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void delete_supervisor(int id){
    try{
            PreparedStatement  delete = conn.prepareStatement("DELETE FROM supervisors WHERE id=?");
            delete.setInt(1, id);
            delete.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void add_project(String name){
        try{
            PreparedStatement  insert = conn.prepareStatement("insert into projects(name)values(?)");
            insert.setString(1, name);
            insert.executeUpdate();
            }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void update_project(int id, String name){
    try{
            PreparedStatement  update = conn.prepareStatement("UPDATE projects SET name=? WHERE id=?");
            update.setString(1, name);
            update.setInt(2, id);
            update.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void delete_project(int id){
    try{
            PreparedStatement  delete = conn.prepareStatement("DELETE FROM projects WHERE id=?");
            delete.setInt(1, id);
            delete.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet search_project(int id){
        ResultSet rs = null;        
        try{
            PreparedStatement  search = conn.prepareStatement("SELECT * FROM projects WHERE id=?");
            search.setInt(1, id);
            rs = search.executeQuery();
            return rs;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void update_std_sup(int std_id, int sup_id){
    try{
            PreparedStatement  update = conn.prepareStatement("UPDATE students SET supervisor_id=? WHERE id=?");
            update.setInt(1, sup_id);
            update.setInt(2, std_id);
            update.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }

}

public void update_std_proj(int std_id, int proj_id){
    try{
            PreparedStatement  update = conn.prepareStatement("UPDATE students SET project_id=? WHERE id=?");
            update.setInt(1, proj_id);
            update.setInt(2, std_id);
            update.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }

}

    public void clearSupervisors(){
    try{
            PreparedStatement  update = conn.prepareStatement("UPDATE students SET supervisor_id=?");
            update.setInt(1, 0);
            update.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public void deleteTables(){
        try{
                stmt = conn.createStatement();
                stmt.executeUpdate("DROP TABLE students");
                stmt.executeUpdate("DROP TABLE supervisors");
                stmt.executeUpdate("DROP TABLE projects");

        }catch(SQLException e){
        }        
    }

    public String generate_random_password(){
        return "";
    }
}
