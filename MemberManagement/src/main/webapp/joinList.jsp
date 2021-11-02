<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" import="MemberVO.MemberVO"%>
<%
	request.setCharacterEncoding("UTF-8");

    MemberVO member = (MemberVO)request.getAttribute("member");

    //list를 불러온다.
    @SuppressWarnings("unchecked")
    List<MemberVO> list = (ArrayList<MemberVO>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>회원목록</title>
<script>
     //var checkAll = document.querySelector('.checkAll');
     //var chkbox = document.querySelectorAll('.chkbox');     

/*      checkAll.onclick = function() {
    	 alert(list.size());
    	 if(checkAll.checked==false) { // 체크박스가 체크 안되어 있을 떄
    		 for(var i=0;i<list.size();i++) {
    			 chkbox[i].checked=false;
    		 }
    	 }
    	 else { 
    		 for(var i=0;i<list.size();i++) {
    			 chkbox[i].checked=true;
    		 }
    	 }    	 
     }; */
     
     

     function mDelete() {
    	if(confirm("정말 삭제하시겠습니까?")) {
			alert("삭제되었습니다.");
			document.multiDelete.submit();
		}
		return false;	    	
    }
     
</script>
</head>
<link rel="stylesheet" href="./css/style.css">
<body>
    <div class="joinlist">
	<h2>회원목록조회/수정</h2>
	<form name="multiDelete" action="multiDelete.me" method="post">
	<table class="joinlisttable">
		<tr>
		    <td><input type="checkbox" id="all_select"></td>
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
		    <td><input type="checkbox" class="chkbox" name="rowCheck" value="<%=list.get(i).getCustno()%>"></td>		
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
		System.out.println(list.size());
		%>
	</table>
	<input type="button" value="선택삭제" onclick="mDelete()">
	</form>
	</div>
</body>
</html>