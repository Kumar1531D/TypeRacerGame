package com.serv;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		session.invalidate();
		res.sendRedirect("UserLogin.jsp");
		
	}

}
