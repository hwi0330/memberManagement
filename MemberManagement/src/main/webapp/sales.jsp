<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" import="MemberVO.MemberVO"%>
<%
	request.setCharacterEncoding("UTF-8");

//list를 불러온다.
@SuppressWarnings("unchecked")
List<MemberVO> list = (ArrayList<MemberVO>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>Hi</title>
</head>
<link rel="stylesheet" href="./css/style.css">
<body>
    <div class="sales">
	<h2>회원매출조회</h2>
	<table class="salestable">
		<tr>
			<td>회원목록</td>
		    <td>회원성명</td>
			<td>고객등급</td>
			<td>매출</td>
		</tr>
		<%
			for (int i = 0; i < list.size(); i++) {
		%>
		<tr>
			<td><%=list.get(i).getCustno()%></td>
  			<td><%=list.get(i).getCustname()%></td>
			<td><%=list.get(i).getGrade()%></td>
			<td><%=list.get(i).getSales()%></td>
		</tr>
		<%
			}
		%>
	</table>
	</div>
</body>
</html>