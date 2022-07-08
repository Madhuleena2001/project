package com.app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogInServlet extends HttpServlet {
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
		
		String userName=hreq.getParameter("username");
		
		String password=hreq.getParameter("password");
		
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String query="";
		int flag=0;
		
		try {
			//load and register oracle driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "arjun28a");
			
			//create statement object
			if(con!=null)
				st=con.createStatement();
			
			//prepare SQL query
			query="SELECT USERNAME,PASSWORD FROM APP";
			
			//send and execute query
			if(st!=null)
				rs=st.executeQuery(query);
			
			//process the resultset object
			while(rs.next()) {
				if(rs.getString(1).equalsIgnoreCase(userName)) {
					flag++;
					if(rs.getString(2).equals(password)) {
						flag++;
						break;
					}
					break;
				}
			}
			//business logic
			if(flag==0) {
				pw.println("<h1 style='color:brown;margin:30px auto'>Seems You don't have an Account.<br>Click <a href='signup.html' style='text-decoration:none;color:orange'>here</a> to create Your Account</h1>");
			}
			if(flag==1) {
				pw.println("<h1 style='color:red;margin:30px auto'>You have entered wrong password or username!!!<br>Please check once and Click <a href='login.html' style='text-decoration:none;color:blue'>login</a> again</h1>");
			}
			if(flag==2) {
				pw.println("<h1 style='color:blue;margin:30px auto'>Congrats!! You have Successfully logged in to your account</h1>");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		//pw.println("<h1 style='color:green;margin:30px auto'>Your Account has been successfully created</h1>");
		//pw.println("<h1 style='color:blue;margin:30px auto'>Click here to <a href='login.html' style='text-decoration:none'>login</a></h1>");
		
		//close printwriter object
		pw.close();
		
	}
}

