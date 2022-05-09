<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">    
	<title>내정보확인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/joinForm.css">
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
        	<ul>
        		<li>아이디 : ${memberInfo.mid }
        		<li>비밀번호 : ${memberInfo.mpw }
        		<li>성함 : ${memberInfo.mname }
        		<li>생년월일 : ${memberInfo.mbirth }
        		<li>메일 : ${memberInfo.memail }
        		<li>주소 : ${memberInfo.maddress }
        	</ul>        
  		</div>
    </div>
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>
</html>