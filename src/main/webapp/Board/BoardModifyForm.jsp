<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">    
	<title>글수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/joinForm.css">
    <script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"	integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>

<script type="text/javascript">
	if (${sessionScope.loginId == null}) {
		alert("로그인 후 사용 가능합니다.");
		//location.href = "${pageContext.request.contextPath }/Member/MemberLoginForm.jsp?url=Board/boardModify"
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
        <h2>BoardModifyForm.jsp - 컨텐츠 영역</h2>
        <h2>세션값 확인 : ${sessionScope.loginId }</h2>
        
        <div class="content">
        <form action="boardModify" method="post" onsubmit="return checkTitle()" enctype="multipart/form-data">
        	<%-- <input type="hidden" name="bwriter" value="${sessionScope.loginId }"> --%>
        	<table>
        		<tr>
        			<th colspan="2">글 수정</th>
        		</tr>
        		<tr>
        			<th>글 번호</th>
	        		<td><input readonly="readonly" name="bno" value="${boardView.bno }" size="45" ></td>
        		</tr>
        		<tr>
        			<th>제목</th>
        			<td><input type="text" name="btitle" id="title" size="45" value="${boardView.btitle }" ></td>
        		</tr>
        		<tr>
        			<th>작성자</th>
        			<td><input readonly="readonly" value="${boardView.bwriter }" size="45"></td>
        		</tr>
        		<tr>
        			<th>내용</th>
        			<td><textarea rows="10" cols="45" name="bcontents" class="textarea">${boardView.bcontents }</textarea></td>
        		</tr>
        		<tr>
        			<th>첨부파일</th>
        			<td>
        			<c:if test="${boardView.bfilename != null}">
        					<img art="${boardView.bfilename }" width="200px" id="orgnImg"
        						src="${pageContext.request.contextPath }/FileUpload/${boardView.bfilename }"> <br>
        			</c:if>
        				<input type="file" name="bfile" id="changeFileName" size="30" >
        				<button type="button" onclick="deleteFile()">첨부파일 삭제</button>
        				<input type="hidden" name="originalBfile" value="${boardView.bfilename }" id="orgnFile" size="45">
        				<input type="hidden" name="delOrgnBfile" value="${boardView.bfilename }" id="delOrgnFileName" size="45">
        			</td>
        		</tr>
        		<tr>
        			<th colspan="2"><input type="submit" class="subBtn1" value="글수정"></th>
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
	
	function deleteFile(){
		console.log("deleteFile() 호출");
		$("#changeFileName").val("");
		$("#delOrgnFileName").val("");
		$("#orgnImg").attr("src","");
		
		console.log("changeFileName" + $("#changeFileName").val());
		console.log("delOrgnFileName : " + $("#delOrgnFileName").val());
		console.log("orgnFile : " + $("#orgnFile").val());
	}	
</script>
</html>