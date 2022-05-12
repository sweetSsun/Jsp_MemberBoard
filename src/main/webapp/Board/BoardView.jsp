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
        		<c:if test="${sessionScope.loginId == boardView.bwriter }">
				<tr>
					<th colspan="6">
						<input class ="subBtn1" type="button" value="글목록"
							onclick="location.href='${pageContext.request.contextPath }/Board/boardList'">
						<input class ="subBtn1" type="button" value="글수정"
							onclick="location.href='${pageContext.request.contextPath }/Board/boardModiInfo?bno=${boardView.bno }&bwriter=${boardView.bwriter }'">
						<input class ="subBtn1" type="button" value="글삭제" 
							onclick="location.href='${pageContext.request.contextPath }/Board/boardDelete?bno=${boardView.bno }&bwriter=${boardView.bwriter }'">
					</th>
				</tr>
        		</c:if>
				
        	</table>
        </div>
    </div>
    
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>

</html>