package com.serv;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AddNewroomServlet extends HttpServlet {
	
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter out = res.getWriter();
		
		String uname = req.getParameter("uname");
		
		String name = req.getParameter("name");
		String pass = req.getParameter("pass");
		
		if(!ToStore.rd.containsKey(name)) {
			ToStore.rd.put(name, pass);
		}
		else {
			out.println("<script type=\"text/javascript\">");
			   out.println("alert('User or password incorrect');");
			   out.println("location='index.jsp';");
			   out.println("</script>");
		}
		
		req.setAttribute("uname", uname);
		
		RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
		rd.forward(req, res);
		
	}

}
