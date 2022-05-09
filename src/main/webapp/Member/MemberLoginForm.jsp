<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    
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
        <h2>MemberLoginForm.jsp - 컨텐츠 영역</h2>
        <form action="Login" method="post">
        <div class="content">
            <h2>로그인 Form</h2>
            <table>
                <tr>
                    <th colspan="2">로그인</th>
                </tr>
                <tr>
                    <th>아이디</th>
                    <td><input type="text" name="userId" id="inputId"></td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td><input type="text" name="userPw" id="inputPw"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="button" class="subBtn1" id="subBtn1" value="로그인">
                    </td>
                </tr>
            </table>
        </div>
    </form>
    </div>
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>
</html>