<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">    
	<title>글작성</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/joinForm.css">
    <script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"	integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<script type="text/javascript">
	if (${sessionScope.loginId == null}) {
		alert("잘못된 접근입니다.");
		history.back();
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
        <h2>BoardWriteForm.jsp - 컨텐츠 영역</h2>
        <h2>세션값 확인 : ${sessionScope.loginId }</h2>
        
        <div class="content">
        <form action="boardWrite" method="post" onsubmit="return checkTitle()" enctype="multipart/form-data">
        	<%-- <input type="hidden" name="bwriter" value="${sessionScope.loginId }"> --%>
        	<table>
        		<tr>
        			<th colspan="2">글 작성</th>
        		</tr>
        		<tr>
        			<th>제목</th>
        			<td><input type="text" name="btitle" id="title" placeholder="글제목" size="30"></td>
        		</tr>
        		<tr>
        			<th>내용</th>
        			<td><textarea rows="10" cols="30" name="bcontents"></textarea></td>
        		</tr>
        		<tr>
        			<th>첨부파일</th>
        			<td><input type="file" name="bfile"></td>
        		</tr>
        		<tr>
        			<th colspan="2"><input type="submit" class="subBtn1" value="글작성"></th>
        		</tr>
        	</table>
        </form>
        </div>
    </div>
        
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>
<script type="text/javascript">
	function checkTitle(){
		console.log("checkTitle() 호출");
		var title = $("#title").val();
		if (title.length == 0){
			alert("제목을 입력해주세요.");
			$("#title").focus();
			return false;
		}
	}
</script>
</html>