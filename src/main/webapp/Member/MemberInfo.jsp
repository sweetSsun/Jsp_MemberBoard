<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">    
	<title>내정보확인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/joinForm.css">
    <script src="https://kit.fontawesome.com/9125416ae4.js"	crossorigin="anonymous"></script>
</head>
<body>
    <!-- Header 시작 -->
    <%@ include file="../includes/Header.jsp" %>    
    <!-- Header 끝 -->
    <!-- Navigation 시작 -->
    <%@ include file="../includes/Navigation.jsp" %>    
    <!-- Navigation 끝 -->
    <div class="contents">
        <h2>MemberInfo.jsp - 컨텐츠 영역</h2>
        <h2>세션값 확인 : ${sessionScope.loginId }</h2>
        <div class="content">
        <table>
        	<tr>
        		<th colspan="5">내정보</th>
        	</tr>
        	<tr>
        		<th><i class="fa-regular fa-address-card"></i></th>
        		<th>아이디</th>
        		<td colspan="3">${memberInfo.mid }</td>
        	</tr>
        	<tr>
        		<th><i class="fa-regular fa-address-card"></i></th>
        		<th>비밀번호</th>
        		<td colspan="3">${memberInfo.mpw }</td>
        	</tr>
        	<tr>
        		<th><i class="fa-regular fa-address-card"></i></th>
        		<th>이름</th>
        		<td colspan="3">${memberInfo.mname }</td>
        	</tr>
        	<tr>
        		<th><i class="fa-regular fa-address-card"></i></th>
        		<th>생년월일</th>
        		<td colspan="3">${memberInfo.mbirth }</td>
        	</tr>
        	<tr>
        		<th><i class="fa-regular fa-address-card"></i></th>
        		<th>메일</th>
        		<td>${memberInfo.memailId }</td>
        		<td>@</td>
        		<td>${memberInfo.memailDomain }</td>
        	</tr>
        	<tr>
        		<th><i class="fa-regular fa-address-card"></i></th>
        		<th>주소</th>
        		<td colspan="3">${memberInfo.maddress }</td>
        	</tr>
        	<tr>
        		<th colspan="5">
        			<button class="subBtn1" onclick="modifyForm()">
        			정보수정</button>
        		</th>
        	</tr>
        </table>
  		</div>
    </div>
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>
<script type="text/javascript">
	function modifyForm(){
		location.href = "${pageContext.request.contextPath }/Member/memberInfo?afterUrl=MemberInfoModify.jsp";
	}
</script>
</html>