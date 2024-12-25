<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "com.serv.dao.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>History</title>
</head>
<body>
	<%
		String uname = request.getParameter("uname");
		InsertDAO in = new InsertDAO();
	
		String ans = in.getHistory(uname);
		
		int bwpm = in.getBestWPM(uname);
		
	%>
	<!-- <h1>Best WPM</h1> -->
	<p><%=ans %></p>
</body>
</html>