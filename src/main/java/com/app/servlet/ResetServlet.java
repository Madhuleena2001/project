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

public class ResetServlet extends HttpServlet {
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
		String userName=hreq.getParameter("uname");
		String newpwd=hreq.getParameter("pwd");
		
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
			query="update app set password='"+newpwd+"' where username='"+userName+"'";
			
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
		pw.println("<h1 style='color:green;margin:30px auto'>Your password has been successfully changed</h1>");
		pw.println("<h1 style='color:blue;margin:30px auto'>You can login <a href='login.html' style='text-decoration:none'>here</a> again</h1>");
		
		//close printwriter object
		pw.close();
		
	}
}

