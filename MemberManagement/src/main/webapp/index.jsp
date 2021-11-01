<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    
    String home = (String) request.getAttribute("home");
    if (home == null) {
    	home = "section";
    }
    
   
%>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑몰 회원 관리 ver 1.0</title>
</head>
<link rel="stylesheet" href="./css/style.css">
<body>
    <div class="index">
    <!-- 첫 화면 -->
    <jsp:include page="header.jsp"></jsp:include>
    <jsp:include page="nav.jsp"></jsp:include>
    <jsp:include page='<%=home + ".jsp"%>'></jsp:include>
    <jsp:include page="footer.jsp"></jsp:include>
    </div>
</body>
</html>