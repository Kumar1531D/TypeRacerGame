<%@ page language="java" import="java.sql.*" import="com.serv.*"
	import="java.util.*"%>
<html>
<head>
<title>Type Racer</title>
<link rel="stylesheet" href="index.css">
</head>
<body>

	<% String uname =(String) request.getAttribute("uname"); %>

	<h1>Rooms</h1>
	<form action="NewRoom.jsp" id = "newRoom">
		<input type = "hidden" value = <%=uname %> name = "uname">
		<input type = "submit" value = "New Room">
	</form>
	
	</br>
	<%
		System.out.println("from index " + uname);
		for (String key : ToStore.rd.keySet()) {
			out.println("<form action = \"PassCheckServlet\">");
			out.println("<input type = \"text\" value =" + key + " name = \"rname\">");
			out.println("<input type = \"password\" name = \"pass\" placeholder = \"Enter Room Password\">");
			out.println("<input type = \"hidden\" name = \"uname\" value =" + uname + ">");
			out.println("<input type = \"submit\" value = \"ENTER\" >");
			out.println("</form>");
		}
	%>

	<form action="History.jsp">
		<input type="hidden" name="uname" value=<%=uname %>> <input
			type="submit" value="History">
	</form>

	<button id="signOutBtn" onclick="location.href='UserLogin.jsp'">Log
		Out</button>

</body>
</html>
