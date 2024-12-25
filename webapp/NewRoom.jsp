<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create a New Room</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
	
	<% 
		String name = request.getParameter("uname");
		System.out.println("From new room "+name);
	%>
	
    <div class="form-container">
        <form action="AddNewroomServlet">
            <h2>Create a New Room</h2>

            <label for="name">Enter the Room Name:</label>
            <input type="text" id="name" name="name" required><br/><br/>
            
            <input type = "hidden" name = "uname" value = <%=name %> >

            <label for="pass">Enter the Password:</label>
            <input type="password" id="pass" name="pass" required><br/><br/>

            <input type="submit" value="CREATE">
        </form>
    </div>

</body>
</html>
