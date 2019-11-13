package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//import javax.sql.DataSource;











import javax.sql.DataSource;

import com.mysql.jdbc.Driver;


public class StudentDbUtil {
	
	private static DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		
		dataSource = theDataSource;
	}

	public List<Student> getStudent() throws Exception
	{
		List<Student> students=new ArrayList<>();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		
		try
		{
			conn=dataSource.getConnection();
			String sql="select * from student";
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next())
			{
				int id=rs.getInt("id");
				String firstname=rs.getString("firstname");
				String lastname=rs.getString("lastname");
				String email=rs.getString("email");
				
				Student tempStudent=new Student(id,firstname,lastname,email);
				
				students.add(tempStudent);
			}
			
			return students;
		}
		
		finally{
			close(conn,stmt,rs);
		}
	}

	private static void close(Connection conn, Statement stmt, ResultSet rs) {
		// TODO Auto-generated method stub
		try{
			if(conn!=null)
			{
				conn.close();
			}
			if(stmt!=null)
			{
				stmt.close();
			}
			if(rs!=null)
			{
				rs.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public static void addStudent(Student theStudent) throws SQLException {
		// TODO Auto-generated method stub
		
		Connection conn=null;
		PreparedStatement ps=null;
		try{
			conn=dataSource.getConnection();
			String sql="insert into student "
					   + "(firstname, lastname, email) "
					   + "values (?, ?, ?)";
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, theStudent.getFirstname());
			ps.setString(2, theStudent.getLastname());
			ps.setString(3, theStudent.getEmail());
			
			ps.execute();
		}
		finally{
			close(conn,ps,null);
		}
		
	}

	public Student getStudent(String theStudentId) throws Exception {
		// TODO Auto-generated method stub
		Student theStudent=null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		int studentId;
		
		try{
			studentId=Integer.parseInt(theStudentId);
			
			conn=dataSource.getConnection();
			
			String sql="select * from student where id=?";
			
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, studentId);
			
			rs=ps.executeQuery();
			
			if(rs.next())
			{
				String firstname=rs.getString("firstname");
				String lastname=rs.getString("lastname");
				String email=rs.getString("email");
				
				theStudent=new Student(studentId, firstname, lastname, email);
			}
			else{
				throw new Exception("Could not find student id: "+studentId);
			}
			return theStudent;
		}
		finally{
			close(conn,ps,rs);
		}
	}

	public void updateStudent(Student theStudent) throws Exception {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement ps=null;
		try{
		conn=dataSource.getConnection();
		
		String sql="update student "
				+ "set firstname=?, lastname=?, email=? "
				+ "where id=?";
		
		ps=conn.prepareStatement(sql);
		
		ps.setString(1,theStudent.getFirstname() );
		ps.setString(2, theStudent.getLastname());
		ps.setString(3, theStudent.getEmail());
		ps.setInt(4, theStudent.getId());
		
		ps.execute();
		}
		finally{
			close(conn,ps,null);
		}
		
	}

	public void deleteStudent(String theStudentId) throws Exception {
		// TODO Auto-generated method stub
		Connection conn=null;
		PreparedStatement ps=null;
		int studentId;
		try{
			studentId=Integer.parseInt(theStudentId);
			
			conn=dataSource.getConnection();
			
			String sql="delete from student where id=?";
			
			ps=conn.prepareStatement(sql);
			
			ps.setInt(1, studentId);
			
			ps.execute();
		}
		finally
		{
			close(conn,ps,null);
		}
	}
}
