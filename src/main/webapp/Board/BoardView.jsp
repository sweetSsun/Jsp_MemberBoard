<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">    
	<title>글 상세페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/joinForm.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/BoardView.css">
    <script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"	integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>
<body>
    <!-- Header 시작 -->
    <%@ include file="../includes/Header.jsp" %>    
    <!-- Header 끝 -->
    
    <!-- Navigation 시작 -->
    <%@ include file="../includes/Navigation.jsp" %>    
    <!-- Navigation 끝 -->

    <div class="contents">
        <h2>BoardView.jsp - 컨텐츠 영역</h2>
        <h2>세션값 확인 : ${sessionScope.loginId }</h2>
        
  
        <div class="content">
        	<h2>글내용</h2>
        	<table>
        		<tr>
        			<th>글번호</th>
        			<td>${boardView.bno }</td>
        			<th>작성일</th>
        			<td>${boardView.bdate }</td>
        			<th>조회수</th>
        			<td>${boardView.bhits }</td>
        		</tr>
        		<tr>
        			<th>작성자</th>
        			<td colspan="5">${boardView.bwriter }</td>
        		</tr>
        		<tr>
        			<th>제목</th>
        			<td colspan="5">${boardView.btitle }</td>
        		</tr>
        		<tr>
        			<th>내용</th>
        			<td colspan="5">${boardView.bcontents }</td>
        		</tr>
        		<c:if test="${boardView.bfilename != null }">
        		<tr>
        			<th>첨부파일</th>
        			<td colspan="5">
        				<!-- 파일 있을 때만 보이도록 조건넣기 -->
        				<img alt="${boardView.bfilename }" width="200px" 
        					src="${pageContext.request.contextPath }/FileUpload/${boardView.bfilename }">
        			</td>
        		</tr>
        		</c:if>
        		<!-- 작성자가 쓴 글인지 확인 후 버튼 출력 -->
				<tr>
					<th colspan="6">
						<input class ="subBtn1" type="button" value="글목록"
							onclick="location.href='${pageContext.request.contextPath }/Board/boardList'">
        				<c:if test="${sessionScope.loginId == boardView.bwriter }">
						<input class ="subBtn1" type="button" value="글수정"
							onclick="location.href='${pageContext.request.contextPath }/Board/boardModiInfo?bno=${boardView.bno }&bwriter=${boardView.bwriter }'">
						<input class ="subBtn1" type="button" value="글삭제" 
							onclick="location.href='${pageContext.request.contextPath }/Board/boardDelete?bno=${boardView.bno }&bwriter=${boardView.bwriter }'">
		        		</c:if>				
					</th>
				</tr>
        	</table>
        </div>
        
	  <div class="content">
		<!-- 댓글목록 양식 시작 -->
		<div class="replyList" style="margin-top: 5px;">
			<h2>댓글목록</h2>
			<!-- 1. controller에서 상세보기할 때 함께 조회
			 	댓글작성 버튼 클릭 후 boardView가 다시 진행되기 때문에 조회수가 +1됨 -->
			<!-- 1. ajax 사용하여 댓글목록 조회 
				댓글작성 버튼 클릭 후 댓글목록 부분만 새롭게 받아옴-->
			<table>
			<c:forEach items="${replyList }" var="reply" >
			<c:choose>
				<c:when test="${reply.rewriter == sessionScope.loginId || boardView.bwriter == sessionScope.loginId }">
				<tr>
					<td style="font-weight:bold; text-align:left;">${reply.rewriter }</td>
					<td style="text-align:right;">${reply.redate }</td>					
				</tr>
				<tr>
					<td class="replyContent">${reply.recontents }</td>
					<td style="text-align:right;">
						<button>수정</button>
						<button>삭제</button>
					</td>
				</tr>
				</c:when>
				<c:otherwise>
					<c:if test="${reply.restate == 0 }">
					<tr>
						<td style="font-weight:bold; text-align:left;">${reply.rewriter }</td>
						<td>${reply.redate }</td>					
					</tr>
					<tr>
						<td colspan="2" class="replyContent">${reply.recontents }</td>
					</tr>
					</c:if>
				</c:otherwise>
			</c:choose>
			</c:forEach>
			</table>
		</div>
		<!-- 댓글목록 양식 끝 -->
		<hr>	
		<!-- 댓글작성 양식 시작 -->
		<c:if test="${sessionScope.loginId != null }">
		<div class="replyForm">
			<h2>댓글작성폼</h2>
			<form action="replyWrite" method="post">
			<table>
				<tr>
					<td>
						<input type="radio" name="restate" value="0" checked="checked">전체공개
						<input type="radio" name="restate" value="1">비공개
						<br>
						<input type="hidden" name="rebno" value="${boardView.bno }">
						<input type="hidden" name="rewriter" value="${sessionScope.loginId }">
						<textarea rows="2" name="recontents" placeholder="댓글내용 작성..."></textarea>
					</td>
				</tr>
				<tr>
					<th>
						<input class="subBtn1" style="font-size:medium" type="submit" value="댓글작성">
					</th>
				</tr>
			</table>
			</form>
		</div>    
		</c:if>
		<!-- 댓글작성 양식 끝 -->    
    </div>
    </div>
    
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>

</html>