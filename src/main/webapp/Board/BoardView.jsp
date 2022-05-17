<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<!-- 댓글목록 양식 1 시작 -->
		<div class="replyList" style="margin-top: 5px;">
			<h2>댓글목록</h2>
			<!-- 1. controller에서 상세보기할 때 함께 조회
			 	댓글작성 버튼 클릭 후 boardView가 다시 진행되기 때문에 조회수가 +1됨 -->
			<!-- 2. ajax 사용하여 댓글목록 조회 
				댓글작성 버튼 클릭 후 댓글목록 부분만 새롭게 받아옴-->
			<table>
				<c:forEach items="${replyList }" var="reply">
					<c:choose>
					
						<c:when test="${reply.restate == 1 }"> <%-- 비공개댓글일 때 --%>
							<c:choose>
								<%-- 댓글 작성자 or 원글 작성자일 때 --%>
								<c:when test="${reply.rewriter == sessionScope.loginId
											 or boardView.bwriter == sessionScope.loginId}">
									<tr>
										<td style="text-align:left;">
						 				<span class="rewriterSpan">${reply.rewriter }</span>
						 				${reply.redate }
									 	</td>	
									 	<td style="text-align:right;">
						 					<c:if test="${reply.rewriter == sessionScope.loginId }">
											<button onclick="modifyReplyAjax(${reply.renum })">수정</button>
											<button>삭제</button>
								 			</c:if>
										</td>				
									</tr>
									<tr>
										<td class="replyContent" colspan="2">
										[비공개 댓글] <br> ${reply.recontents }</td>
									</tr>
								</c:when>
								<%-- 제 3자일 때 --%>
								<c:otherwise> 
									<tr>
										<td class="replyContent" colspan="2">비공개 댓글입니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</c:when>

						<c:otherwise> <%-- 공개댓글일 때 --%>
							<tr>
								<td style="text-align: left;"><span class="rewriterSpan">${reply.rewriter }</span>
									${reply.redate }</td>
								<td style="text-align: right;"><c:if
										test="${reply.rewriter == sessionScope.loginId }">
										<button onclick="modifyReplyAjax(${reply.renum })">수정</button>
										<button>삭제</button>
									</c:if></td>
							</tr>
							<tr>
								<td class="replyContent" colspan="2">${reply.recontents }</td>
							</tr>
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
						<input type="radio" name="restate" id="restate" value="0" checked="checked">전체공개
						<input type="radio" name="restate" id="restate" value="1">비공개
						<br>
						<input type="hidden" name="rebno" id="rebno" value="${boardView.bno }">
						<input type="hidden" name="rewriter" id="rewriter" value="${sessionScope.loginId }">
						<textarea rows="2" name="recontents" id="recontents" placeholder="댓글내용 작성..."></textarea>
					</td>
				</tr>
				<tr>
					<th>
						<input class="subBtn1" style="font-size:medium" type="submit" value="댓글작성">
						<input class="subBtn1" style="font-size:medium" type="button" id="ajaxReply" value="ajax댓글">
					</th>
				</tr>
			</table>
			</form>
		</div>    
		</c:if>
		<!-- 댓글작성 양식 끝 -->    
    </div>
    
    
    	<!-- 댓글목록 2 시작 -->
    <div class="content">
    	<h2>댓글목록2 - ajax</h2>
    	<div id="replyList_ajax" style="border:0">

 		</div>
    	<!-- 댓글목록 2 끝 -->
    	<hr>
		<!-- 댓글작성 양식 2 시작 -->    
		<!-- /Board/replyWrite -->
    	<form action="replyWrite" method="post">
    	댓글작성자 : <input type="text" name="rewriter" value="${sessionScope.loginId }">
    	<br>
    	원본글번호 : <input type="text" name="rebno" value="${boardView.bno }">
    	<table border="1">
    		<tr>
    			<td>
    				<label><input type="radio" name="restate" value="0" checked="checked">전체공개</label>
    				<label><input type="radio" name="restate" value="1">비공개</label>
    				<br>
    				<textarea rows="" cols="" name="recontents" placeholder="댓글내용작성..."></textarea>
    			</td>
    		</tr>
    		<tr>
    			<th><button type="submit">댓글작성</button></th>
    		</tr>
    	</table>
    	</form>    	
   		<!-- 댓글작성 양식 2 끝 -->    
    </div>  
    
    
    </div>
    
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>
<script type="text/javascript">
	


	$(document).ready(function() {		
		console.log("확인!");
		getReplyList();
		
		
		/* $("#ajaxReply").click(function() {
			var restate = $("#restate").val();
			var rebno = $("#rebno").val();
			var rewriter = $("#rewriter").val();
			var recontents = $("#recontents").val();
			$.ajax({
				type : "post",
				url : "replyAjax",
				data : {"restate" : restate, "rebno" : rebno, "rewriter" : rewriter, "recontents" : recontents },
				success : function(result){
					if (result == "OK") {
						console.log("댓글 작성 성공");			
						getReplyList();
					} else {
						console.log("댓글 작성 실패");
					}
				}
			});
			
		})	 */
	});
	
	
	function getReplyList(){
		console.log("getReplyList() 호출");
		var boardNo = '${boardView.bno }';
		var loginMemberId = '${sessionScope.loginId }';
		console.log("boardNo : " + boardNo);
		console.log("loginMemberId : " + loginMemberId);
 		$.ajax({
			type : "get",
			url : "replyList",
			data : {"bno" : boardNo},
			dataType : "json",  // 컨트롤러로부터 응답받는 데이터의 형태
			async : false,
			success : function(result){
				console.log(result);
				console.log("result.length : " + result.length);
				console.log(result[0].recontents);
				
 				var output = "<table border='1'>";
				for (var i=0; result.length > i; i++) {
					output += "<tr><td>" + result[i].rewriter + "</td>";
					output += "<td>" + result[i].redate + "</td>";
					output += "<td><button>삭제</button></td></tr>";
					output += "<tr><td colspan='3'>" + result[i].recontents + "</td></tr>";
				} 
				output += "</table>";
				$("#replyList_ajax").html(output);
				
			} 
		});
	}

		
</script>

</html>