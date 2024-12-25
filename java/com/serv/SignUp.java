package com.serv;

import java.io.IOException;

import com.serv.dao.InsertDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class SignUp extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String uname = req.getParameter("uname");
		String pass = req.getParameter("pass");
		
		InsertDAO in = new InsertDAO();
		
		if(in.checkName(uname)) {
			res.sendRedirect("signup.html");
		}
		else {
			in.insert(uname, pass);
			res.sendRedirect("UserLogin.jsp");
		}
	}

}
