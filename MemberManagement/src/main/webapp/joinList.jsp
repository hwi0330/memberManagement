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
<title>회원목록</title>
</head>
<link rel="stylesheet" href="./css/style.css">
<body>
    <div class="joinlist">
	<h2>회원목록조회/수정</h2>
	<form action="memberDelete.me" method="get">
	<table class="joinlisttable">
		<tr>
			<td>회원번호</td>
			<td>회원성명</td>
			<td>전화번호</td>
			<td>주소</td>
			<td>가입일자</td>
			<td>고객등급</td>
			<td>거주지역</td>
		</tr>
		<%
			for(int i = 0; i < list.size(); i++) {
		%>
		<tr>
		
		    <!-- 회원번호 클릭해서 회원정보 수정 -->
		    <td><a href='joinSelectOne.me?custno=<%=list.get(i).getCustno()%>'><%=list.get(i).getCustno()%></a></td>
			
			<td><%=list.get(i).getCustname()%></td>
			<td><%=list.get(i).getPhone()%></td>
			<td><%=list.get(i).getAddress()%></td>
			<td><%=list.get(i).getJoindate()%></td>
			<td><%=list.get(i).getGrade()%></td>
			<td><%=list.get(i).getCity()%></td>
		</tr>
		<%
			}
		%>
	</table>
	<input type="submit" value="삭제">
	</form>
	</div>
</body>
</html>