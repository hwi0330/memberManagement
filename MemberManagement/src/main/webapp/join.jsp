<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원등록 화면</title>

</head>
<script>	
	// 유효성 검사 하기
	function joinChk() {
		// 회원 성명
		if(document.join.custname.value =="") {
			alert('회원성명이 입력되지 않았습니다');
			document.join.custname.focus();
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
		
		alert('회원등록이 완료되었습니다!');
		document.join.submit(); // submit시 Controller로
	}

</script>
<link rel="stylesheet" href="./css/style.css">
<body>
    <div class="join">
		<h2>홈쇼핑 회원 등록</h2>
		<form name="join" action="joinAction.me" method="post">
			<table class="jointable">
				<tr>
					<td>회원번호(자동발생)</td>
					<td><input type="text" id="textarea-width1" name="custno" value="${count}"></td>
				</tr>
				<tr>
					<td>회원성명</td>
					<td><input type="text" id="textarea-width1" name="custname"></td>
				</tr>
				<tr>
					<td>회원전화</td>
					<td><input type="text" id="textarea-width2" name="phone"></td>
				</tr>
				<tr>
					<td>회원주소</td>
					<td><input type="text" id="textarea-width3" name="address"></td>
				</tr>
				<tr>
					<td>가입일자</td>
					<td><input type="text" id="textarea-width1"name="joindate"></td>
				</tr>
				<tr>
					<td>고객등급[A:VIP,B:일반,C:직원]</td>
					<td><input type="text" id="textarea-width1" name="grade"></td>
				</tr>
				<tr>
					<td>도시코드</td>
					<td><input type="text" id="textarea-width1" name="city"></td>
				</tr>
				<tr>
					<td colspan="2">
					<div class="center">
                    <input type="button" value="등록" onclick="joinChk()"> 
                    <input type="submit" formaction="joinSelectAll.me" value="조회">
				    </div>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>