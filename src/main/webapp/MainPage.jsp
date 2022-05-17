<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/main.css">
</head>

<body>
    <!-- Header 시작 -->
    <%@ include file="includes/Header.jsp" %>    
    <!-- Header 끝 -->
    <!-- Navigation 시작 -->
    <%@ include file="includes/Navigation.jsp" %>    
    <!-- Navigation 끝 -->
    <div class="contents">
        <h2>MainPage.jsp - 컨텐츠 영역</h2>
        
        <h2>세션값 확인 : ${sessionScope.loginId }</h2>
        <!--             session 영역의 Attribute -->
    </div>
    <!-- Footer 시작 -->
    <%@ include file="includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>
</html>