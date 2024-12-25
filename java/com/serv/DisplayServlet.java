package com.serv;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class DisplayServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		PrintWriter out = res.getWriter();
		
		String qry = "select * from RoomDetails;";
		String url = "jdbc:mysql://localhost:3306/rooms";
		String username = "root";
		String password = "sabari";
		res.setContentType("text/html");  
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con = DriverManager.getConnection(url,username,password);
			
			Statement s = con.createStatement();
			
			ResultSet rs = s.executeQuery(qry);
			
			out.print("<table><tr><th>Room Names</th></tr>");
			
			while(rs.next()) {
				out.println("<tr><td>");
				out.println(rs.getObject(2).toString());
				out.println("</td>");
			
				
			}
			
			out.println("</table>");
			
		} catch (SQLException e) {
			// TODO HANDLE
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
