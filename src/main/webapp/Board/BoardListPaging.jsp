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
        		<tr>
        			<th colspan="6">
						<c:choose>
							<c:when test="${pagedto.page <= 1 }">
							<%-- 1페이지일 경우 이전으로 갈 페이지가 없기 때문에 링크없이 --%>
								[이전]&nbsp;&nbsp;
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath }/Board/boardListPaging?page=${pagedto.page -1 }">[이전]&nbsp;&nbsp;</a>
							</c:otherwise>
						</c:choose>

        				<c:forEach begin="${pagedto.startPage }" end="${pagedto.endPage }" var="num" step="1">
        				<%-- startpage부터 endPage까지 --%>
        					<c:choose>
        						<c:when test="${pagedto.page == num }">
        						<%-- 현재 보고있는 페이지와 같은 페이지 번호면 링크 없이 --%>
        							<span style="font-size: 20px">${num }&nbsp;&nbsp;</span>
        						</c:when>
        						<c:otherwise>
		        					<a href="${pageContext.request.contextPath }/Board/boardListPaging?page=${num }">${num }</a>&nbsp;&nbsp;
        						</c:otherwise>
        					</c:choose>
        				</c:forEach>
        				
        				<c:choose>
							<c:when test="${pagedto.page >= pagedto.maxPage }">
							<%-- 마지막 페이지일 경우 이후로 갈 페이지가 없기 때문에 링크없이 --%>
								[다음]
							</c:when>
							<c:otherwise>
								<a href="${pageContext.request.contextPath }/Board/boardListPaging?page=${pagedto.page +1 }">[다음]&nbsp;&nbsp;</a>
							</c:otherwise>
						</c:choose>
						
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
	
</script>

</html>