package com.serv;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class PassCheckServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		res.setContentType("text/html");
		
		PrintWriter out = res.getWriter();
		
		String rname = req.getParameter("rname");
		String pass = req.getParameter("pass");
		String uname = req.getParameter("uname");
		
		System.out.println("Passcheck servlet "+ uname);
		
		req.setAttribute("uname", uname);
		req.setAttribute("rname", rname);
		
		if(!ToStore.rd.containsKey(rname) || !ToStore.rd.get(rname).equals(pass)) {
			res.sendRedirect("index.jsp");
		}
		else {
			RequestDispatcher rd = req.getRequestDispatcher("home.jsp");
			rd.forward(req, res);
//			res.sendRedirect("home.jsp");
		}
		
		
	}

}
