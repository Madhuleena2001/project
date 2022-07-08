package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SignUpServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest hreq,HttpServletResponse hres) throws ServletException,IOException{
		
		//set response content type
		hres.setContentType("text/html");
		
		//get PrintWriter Object
		PrintWriter pw=hres.getWriter();
		
		//get request parameter
		String firstName=hreq.getParameter("fname");
		String lastName=hreq.getParameter("lname");
		String gender=hreq.getParameter("gender");
		String phoneNo=hreq.getParameter("phno");
		String userName=hreq.getParameter("uname");
		String password=hreq.getParameter("pwd");
		
		Connection con=null;
		Statement st=null;
		String query="";
		
		try {
			//load and register oracle driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "arjun28a");
			
			//create statement object
			if(con!=null)
				st=con.createStatement();
			
			//prepare SQL query
			query="insert into app values('"+firstName+"','"+lastName+"','"+gender+"','"+phoneNo+"','"+userName+"','"+password+"')";
			
			//send and execute query
			if(st!=null)
				st.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(st!=null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		pw.println("<h1 style='color:green;margin:30px auto'>Your Account has been successfully created</h1>");
		pw.println("<h1 style='color:blue;margin:30px auto'>Click here to <a href='login.html' style='text-decoration:none'>login</a></h1>");
		
		//close printwriter object
		pw.close();
		
	}
}
