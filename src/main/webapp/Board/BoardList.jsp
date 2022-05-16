<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">    
	<title>게시판</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/main.css">
    <link rel="stylesheet"	href="${pageContext.request.contextPath }/CSS/joinForm.css">
	<script src="https://kit.fontawesome.com/9125416ae4.js" crossorigin="anonymous"></script>
	<script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"	integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	
</head>
<script type="text/javascript">
	// 글 삭제 성공 alert창 띄우기
	var checkMsg = "${param.checkMsg }";
	console.log(checkMsg)
	if (checkMsg.length > 0) {
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
        <h2>BoardList.jsp - 컨텐츠 영역</h2>
        <h2>세션값 확인 : ${sessionScope.loginId }</h2>
        
        <div class="content">
        	<h2>글목록</h2>
        	<table>
				<tr>
					<td colspan="5" style="text-align:right;">
        				<c:if test="${sessionScope.loginId != null }">
        					<button onclick="boardWirteForm()">글작성1</button>
        				</c:if>
        				<button onclick="boardWirteForm2('${sessionScope.loginId }')">글작성2</button>
        			</td>
        		</tr>
        		<tr>
        			<th colspan="5">
		        		<!-- 검색창 -->
		        		<select id="searchType" onchange="console.log(this.value)">
		        			<option value="btitle">글제목</option>
		        			<option value="bwriter">작성자</option>
		        			<option value="bcontents">글내용</option>
		        		</select>
        				<input type="text" size="30" id="searchText" placeholder="검색할 단어">
        				<button onclick="searchBoard()">검색</button> 
					</th>
				</tr>
        		<c:if test="${param.searchText != '' and param.searchText != null }">
				<tr>
					<th colspan="5"> [${param.searchText }] 검색결과입니다. </th>
				</tr>        		
        		</c:if>
        		<tr>
        			<th>글번호</th>
        			<th>글제목</th>
        			<th>작성자</th>
        			<th>작성일</th>
        			<th>댓글수</th>
        			<th>조회수</th>
        		</tr>
        		
        		<c:forEach items="${boardList }" var="board">
        		<tr>
        			<td>${board.bno }</td>
        			<td style="width: 40%;">
        				<a href="${pageContext.request.contextPath }/Board/boardView?bno=${board.bno }">${board.btitle }</a>
	        			<c:if test="${board.bfilename != null }">
    	    				<i class="fa-regular fa-file"></i>
        				</c:if>
        			</td>
        			<td>${board.bwriter }</td>
        			<td>${board.bdate }</td>
        			<td>${board.recount }</td>
        			<td>${board.bhits }</td>
        		</tr>        		
        		</c:forEach>
        	
        	</table>
        </div>
    </div>
    
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>
<script type="text/javascript">

	function boardWirteForm(){
		location.href="BoardWriteForm.jsp";
	}
	
	function boardWirteForm2(loginId){
		console.log(loginId);
		if(loginId == "") {
			alert("로그인 후 사용가능합니다.");
			location.href="${pageContext.request.contextPath }/Member/MemberLoginForm.jsp?url=Board/boardList";			
		} else {
			location.href="BoardWriteForm.jsp";
		}
	}
	
	function searchBoard(){
		var searchText = $("#searchText").val();
		console.log("검색할 단어 : " + searchText);
		var searchType = $("#searchType").val();
		location.href="${pageContext.request.contextPath }/Board/boardSearch?searchText=" + searchText + "&searchType=" + searchType ;
		
	}
</script>

</html>