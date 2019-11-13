package com.luv2code.web.jdbc;
import java.sql.*;

public class GetDriverInfo {
  public static void main(String[] args) {
    try {
      Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","root");  
 
      DatabaseMetaData dbMetaData = con.getMetaData();
 
      System.out.println("Driver: " + dbMetaData.getDriverName());
      System.out.println("Version: " + dbMetaData.getDriverVersion());
 
    } catch(SQLException e) {
      System.out.println("SQL exception occured" + e);
    }
  }
}
