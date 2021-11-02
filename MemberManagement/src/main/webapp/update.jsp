<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="MemberVO.MemberVO" %>
<%
	request.setCharacterEncoding("UTF-8");

	MemberVO member = (MemberVO)request.getAttribute("member");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정</title>
<script>
	
	//유효성 검사 하기
	function joinChk() {
		// 회원 성명
		if(document.join.custname.value =="") {
			alert('회원성명이 입력되지 않았습니다');
			document.update.custname.focus();
			return false;
		}
		// 회원 전화
		if(document.join.phone.value =="") {
			alert('회원전화가 입력되지 않았습니다');
			document.join.phone.focus();
			return false;
		}
		// 회원 주소
		if(document.join.address.value =="") {
			alert('회원주소가 입력되지 않았습니다');
			document.join.address.focus();
			return false;
		}
		// 가입 일자
		if(document.join.joindate.value =="") {
			alert('가입일자가 입력되지 않았습니다');
			document.join.joindate.focus();
			return false;
		}
		// 고객등급
		if(document.join.grade.value =="") {
			alert('고객등급이 입력도지 않았습니다');
			document.join.grade.focus();
			return false;
		}
		// 도시코드
		if(document.join.city.value =="") {
			alert('도시번호가 입력되지 않았습니다');
			document.join.city.focus();
			return false;
		}
		
		alert('회원정보수정이 완료되었습니다!');
		document.join.submit();
	}
	
	function memberDelete() { // 확인/ 취소 팝업창
		if(confirm("회원번호 " + <%=member.getCustno() %> + "님의 정보를 정말 삭제하시겠습니까?")) {
			alert(<%=member.getCustno() %> + '님의 정보가 삭제되었습니다.');
			location.href = "memberDelete.me?custno=" + <%=member.getCustno() %>
		}
		return false; // 취소 클릭 시 창닫음			
	}

</script>
</head>
<link rel="stylesheet" href="./css/style.css">
<body>
    <div class="join">
        <h2>홈쇼핑 회원 정보 수정</h2>
        <form name="join" action="updateAction.me" method="post">
            <table class="jointable">
				<tr>
					<td>회원번호</td>
					<td><input type="text" id="textarea-width1" name="custno" value="<%=member.getCustno()%>"></td>
				</tr>
				<tr>
					<td>회원성명</td>
					<td><input type="text" id="textarea-width1"name="custname"  value="<%=member.getCustname()%>"></td>
				</tr>
				<tr>
					<td>회원전화</td>
					<td><input type="text" id="textarea-width2" name="phone" value="<%=member.getPhone()%>"></td>
				</tr>
				<tr>
					<td>회원주소</td>
					<td><input type="text" id="textarea-width3" id="textarea-width1" name="address" value="<%=member.getAddress()%>"></td>
				</tr>
				<tr>
					<td>가입일자</td>
					<td><input type="text" id="textarea-width1" name="joindate" value="<%=member.getJoindate()%>"></td>
				</tr>
				<tr>
					<td>고객등급[A:VIP,B:일반,C:직원]</td>
					<td><input type="text" id="textarea-width1" name="grade" value="<%=member.getGrade()%>"></td>
				</tr>
				<tr>
					<td>도시코드</td>
					<td><input type="text" id="textarea-width1" name="city" value="<%=member.getCity()%>"></td>
				</tr>
				<tr>
					<td colspan="2">
					<div class = "center">
                    <input type="button" value="수정" onclick="joinChk()"> 
                    <input type="button" value="삭제" onclick="memberDelete()"> 
                    <input type="submit" formaction="joinSelectAll.me" value="조회">
				    </div>
				</tr>
			</table>
        
        </form>
    
    </div>

</body>
</html>