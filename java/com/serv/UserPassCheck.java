package com.serv;

import java.io.IOException;



import com.serv.dao.InsertDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserPassCheck extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String uname = req.getParameter("uname");
		
//		HttpSession session = req.getSession();
//		
//		session.setAttribute("uname", uname);
		
		
		
		System.out.println(uname+"From pass check");
		
		if(uname.equalsIgnoreCase("guest")) {
			req.setAttribute("uname", uname);
			RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
			rd.forward(req, res);
		}else {
		
			String pass = req.getParameter("pass");
			
			InsertDAO in = new InsertDAO();
			
			if(in.checkPass(uname, pass)) {
				req.setAttribute("uname", uname);
				RequestDispatcher rd = req.getRequestDispatcher("index.jsp");
				rd.forward(req, res);
				System.out.println("From rd userPasscheck " + uname);
			}
			else {
				res.sendRedirect("UserLogin.jsp");
			}
		}
		
	}

}
