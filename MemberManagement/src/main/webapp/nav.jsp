<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑몰 회원 관리 ver 1.0</title>
</head>
<link rel="stylesheet" href="./css/style.css">
<body>
    <div class="nav">
        <ul>
            <!-- 링크 클릭 시 Controller로 -->
            <li><a href="joinNum.me">회원등록</a></li>
            <li><a href="joinSelectAll.me">회원목록조회/수정</a></li>
            <li><a href="sales.me">회원매출조회</a></li>
            <li><a href="index.jsp">홈으로.</a></li>
        </ul>
    </div>
</body>
</html>