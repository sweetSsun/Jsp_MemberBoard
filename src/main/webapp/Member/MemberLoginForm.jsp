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
    <!-- jQuery -->
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"	integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<script type="text/javascript">
 	var checkMsg = "${param.checkMsg }";
	if (checkMsg.length > 0 ){
		alert(checkMsg);
	}
</script>
<body>
    <!-- Header 시작 -->
    <%@ include file="../includes/Header.jsp" %>    
    <!-- Header 끝 -->
    <!-- Navigation 시작 -->
    <%@ include file="../includes/Navigation.jsp" %>    
    <!-- Navigation 끝 -->
    <div class="contents">
        <h2>MemberLoginForm.jsp - 컨텐츠 영역</h2>
        <h2>세션값 확인 : ${sessionScope.loginId }</h2>
        <form action="memberLogin" method="post" onsubmit="return checkSubmit()">
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
                        <input type="submit" class="subBtn1" id="subBtn1" value="로그인">
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

<script type="text/javascript">
	function checkSubmit(){
		console.log("checkSubmit() 호출")
		var inputId = $("#inputId").val();
		var inputPw = $("#inputPw").val();
		if (inputId.length == 0 ){
			alert("아이디를 입력해주세요.");
			$("#inputId").focus();
			return false;
		} 
		if (inputPw.length == 0) {
			alert("비밀번호를 입력해주세요.");
			$("#inputPw").focus();
			return false;
		}
	}
</script>
</html>