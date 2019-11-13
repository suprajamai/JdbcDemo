package com.luv2code.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	
	private DataSource dataSource;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		try{
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String theCommand=request.getParameter("command");
		//	System.out.println(theCommand);
			if(theCommand==null)
			{
				theCommand="LIST";
			}
			//System.out.println(theCommand);
			switch(theCommand){
			
			case "LIST":
				listStudents(request,response);
				break;
				
			case "ADD":
				addstudent(request,response);
				break;
				
			case "LOAD":
				loadstudent(request,response);
				break;
				
			case "UPDATE":
				updatestudent(request,response);
				break;
			
			case "DELETE":
				deletestudent(request,response);
				break;
				
			default:
				listStudents(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deletestudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		String theStudentId=request.getParameter("studentId");
		studentDbUtil.deleteStudent(theStudentId);
		listStudents(request, response);
	}

	private void updatestudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		// TODO Auto-generated method stub
		int id=Integer.parseInt(request.getParameter("studentId"));
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String email=request.getParameter("email");
		
		Student theStudent=new Student(id, firstname, lastname, email);
		
		studentDbUtil.updateStudent(theStudent);
		
		listStudents(request, response);
		
	}

	private void loadstudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		// TODO Auto-generated method stub
		String theStudentId=request.getParameter("studentId");
		Student theStudent=studentDbUtil.getStudent(theStudentId);
		request.setAttribute("THE_STUDENT", theStudent);
		
		RequestDispatcher rd=request.getRequestDispatcher("/update-student-form.jsp");
		rd.forward(request, response);
	}

	private void addstudent(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//read the student data
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");
		String email=request.getParameter("email");
	//	System.out.println(firstname+"  "+lastname+"  "+email);
		//create student object
		Student theStudent=new Student(firstname, lastname, email);
		
		//add student to database
		studentDbUtil.addStudent(theStudent);
		
		//send back to main page
		listStudents(request, response);
		
	}

	private void listStudents(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		List<Student> students=studentDbUtil.getStudent();
		request.setAttribute("STUDENT_LIST",students);
		
		RequestDispatcher rd=request.getRequestDispatcher("/list_student.jsp");
		rd.forward(request, response);
		
	}

}
