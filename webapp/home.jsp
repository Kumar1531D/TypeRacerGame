<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="styles2.css">
  <script src="script.js" defer></script>
  <title>Begin Game</title>
</head>
<body>
<%
	//String user_name = (String)session.getAttribute("uname");
	String name = (String)request.getAttribute("uname");
	System.out.println("From home page "+name);
	String rname = (String)request.getAttribute("rname");
%>

<div class="timer" id="timer"></div>
<div id = "wpm"></div>
  
  <div class="container">
    <div class="quote-display" id="quoteDisplay"></div>
    <textarea id="quoteInput" class="quote-input" autofocus readonly></textarea>
  </div>
  
  <div><button onclick = "readyButtonClick()" id = "readyButton">Ready</button> 
  <button id = "rematchButton" style = "display: none;">Rematch</button>
  <button id = "exitButton" style = "display: none;">Exit</button>
  </div>
  
  <!-- <div id="progressBar">
        <div id="fillBar"></div>
    </div>
  <div id="progressContainer" class="progress-container"></div> -->
  
  <div id = "progressBars"></div>

<input type="hidden" id="roomName" value='<%=rname %>' >
<input type="hidden" id="user_name" value='<%=name %>' >
  <div class = "res-display" id = "res"></div>
  <div id = "d"></div>

</body>
</html>