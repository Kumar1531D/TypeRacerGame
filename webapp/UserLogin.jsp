<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel = "stylesheet" href = "styles3.css">
</head>
<body>

<script type="text/javascript">
function alertName(){
alert("If you are a Guest Just Type guest and hit enter");
} 

window.onload = alertName;
</script> 

<form action="UserPassCheck">
    <h1>Login</h1>
    <label for="uname">Enter the Name:</label>
    <input type="text" id="uname" name="uname" required>
    <label for="pass">Enter the Password:</label>
    <input type="password" id="pass" name="pass">
    <a href="signup.html">New User</a>
    <input type="submit" value="Submit">
</form>

</body>
</html>