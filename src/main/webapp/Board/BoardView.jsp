<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">    
	<title>글 상세페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/CSS/BoardView.css">
    <script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"	integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <style>
    	.d_none{
    		display: none;
    	}
    </style>
</head>
<script type="text/javascript">
	var checkMsg = "${param.checkMsg }";
	console.log(checkMsg);
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
												<button onclick="replyModifyForm('re${reply.renum }', 'modiContent${reply.renum }', this)">수정</button>
												<button onclick="replyDelete(${reply.renum }, ${boardView.bno })">삭제</button>
								 			</c:if>
										</td>				
									</tr>
									<tr>
										<td colspan="2">
											<textarea rows="1" class="re${reply.renum }" readonly="readonly" style="border: none">[비공개 댓글] ${reply.recontents }</textarea>								
								
										<form action="replyModify" method="post" class="d_none re${reply.renum }">
											<input type="hidden" name=bno value="${boardView.bno }">
											<input type="hidden" name="renum" value="${reply.renum }">
											<textarea name="modiContents" id="modiContent${reply.renum }">${reply.recontents }</textarea>
											<button class="subBtn1" style="text-align: right">댓글수정</button>
										</form>
										</td>
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
								<td style="text-align: right;">
									<c:if test="${reply.rewriter == sessionScope.loginId }">
										<button onclick="replyModifyForm('re${reply.renum }', 'modiContent${reply.renum }', this)">수정</button>
										<!-- this :: 버튼태그 그 자체 (object를 매개변수로 전달). 클릭된 댓글 번호의 수정 버튼태그 -->
										<button onclick="replyDelete(${reply.renum }, ${boardView.bno })">삭제</button>
									</c:if>
								</td>
							</tr>
							<tr>
								<%-- <td class="replyContent" colspan="2">${reply.recontents } --%>
								<td colspan="2">
									<textarea rows="1" class="re${reply.renum }" readonly="readonly" style="border: none">${reply.recontents }</textarea>								
								
								<form action="replyModify" method="post" class="d_none re${reply.renum }">
									<input type="hidden" name=bno value="${boardView.bno }">
									<input type="hidden" name="renum" value="${reply.renum }">
									<textarea name="modiContents" id="modiContent${reply.renum }">${reply.recontents }</textarea>
									<button class="subBtn1">수정완료</button>
								</form>
								</td>
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
	<div class="replyForm">
		<h2>댓글작성폼2</h2>
    	<table width="80%">
    		<tr>
    			<td>
    				<label><input type="radio" name="restate_ajax" value="0" checked="checked">전체공개</label>
    				<label><input type="radio" name="restate_ajax" value="1">비공개</label>
    				<br>
    				<textarea rows="2" width="100%" id="recontents_ajax" placeholder="댓글내용작성..."></textarea>
    			</td>
    		</tr>
    		<tr>
    			<th><button type="button" onclick="replyWrite_ajax()">댓글작성</button></th>
    		</tr>
    	</table>
   		<!-- 댓글작성 양식 2 끝 -->    
    </div>  
    </div>
    
    </div>
    
    <!-- Footer 시작 -->
    <%@ include file="../includes/Footer.jsp" %>    
    <!-- Footer 끝 -->
</body>

<script type="text/javascript">
	// 맨 아래 댓글작성폼 ajax 댓글 작성 함수 (선생님과 함께)
	function replyWrite_ajax(){
		console.log("replyWrite_ajax() 호출");
		var rebno = "${boardView.bno }";
		console.log("원본글번호 : " + rebno);
		var rewriter = "${sessionScope.loginId }";
		console.log("댓글작성자 : " + rewriter);
		var restate = $('input:radio[name=restate_ajax]:checked').val();
		// type이 radio인 input 태그 중 name이 restate_ajax인 체크된 value를 가져온다.
		// radio 태그는 같은 이름으로 묶여있어야 하는데, id로 하면 중복값이 되기 때문에 name으로 명시하고 jQuery로 받아온다.
		console.log("공개범위 : " + restate);
		var recontents = $("#recontents_ajax").val();
		console.log("댓글내용 : " + recontents);
		$.ajax({
			type : "post",
			url : "replyWrite_ajax",
			data : {"restate" : restate, "rebno" : rebno, "rewriter" : rewriter, "recontents" : recontents },
			async : false,
			success : function(result){
				console.log("댓글 작성 결과 : " + result);
				if (result == "OK") {
					console.log("댓글 작성 성공");			
					$("#recontents_ajax").val("");
					getReplyList();
					alert("댓글이 등록되었습니다.")
				} else {
					console.log("댓글 작성 실패");
					alert("댓글 등록에 실패했습니다.")
				} 
			}
		});
	}

	$(document).ready(function() {		
		console.log("확인!");
		getReplyList();
		
		// 내가 한 ajax 댓글 작성 함수
		$("#ajaxReply").click(function() {
			console.log("ajax 댓글 작성 요청")
			var restate = $("#restate").val();
			var rebno = $("#rebno").val();
			var rewriter = $("#rewriter").val();
			var recontents = $("#recontents").val();
			$.ajax({
				type : "post",
				url : "replyWrite_ajax",
				data : {"restate" : restate, "rebno" : rebno, "rewriter" : rewriter, "recontents" : recontents },
				success : function(result){
					if (result == "OK") {
						console.log("댓글 작성 성공");		
						getReplyList();
						alert("댓글이 등록되었습니다.")
					} else {
						console.log("댓글 작성 실패");
						alert("댓글 등록에 실패했습니다.")
					}
				}
			});
			
		});
	});
	
	// 댓글 목록을 가져오고 출력해주는 함수
	function getReplyList(){
		console.log("getReplyList() 호출");
		var boardNo = '${boardView.bno }';
 		$.ajax({
			type : "get",
			url : "replyList_ajax",
			data : {"bno" : boardNo},
			dataType : "json",  // 컨트롤러로부터 응답받는 데이터의 형태
			async : false,
			success : function(result){
				console.log(result);
				console.log("result.length : " + result.length);
				$("#recontents_ajax").val("");
				replyListPrint(result);				
			} 
		});
	}
	
	// 댓글 목록 출력을 위해 변수에 html 코드를 누적하는 함수
	function replyListPrint(result){
		var loginMemberId = '${sessionScope.loginId }';
		var boardWriter = '${boardView.bwriter }';
		
		var output = "<table border='1' width='80%'>";
		for (var i=0; result.length > i; i++) {
			if (result[i].restate == 1){
				if (result[i].rewriter == loginMemberId || boardWriter == loginMemberId){
					output += "<tr>";
					output += "<td>" + result[i].rewriter + "</td>";
					output += "<td>" + result[i].redate + "</td>";
					
					if(result[i].rewriter == loginMemberId){
						output += "<td><button onclick='replyModifyForm_ajax(\"" + result[i].renum + "\")'>수정</button>";
						output += "<button onclick='replyDelete_ajax(\"" + result[i].renum + "\")'>삭제</button></td>";					
					} else {
						output += "<td></td>";
					}
					output += "</tr>"
					output += "<tr><td colspan='3'>" + result[i].recontents + "</td></tr>";
				} else {
					output += "<tr><td colspan='3'>[비공개 댓글입니다.]</td></tr>";						
				}
			} else {
				output += "<tr>";
				output += "<td>" + result[i].rewriter + "</td>";
				output += "<td>" + result[i].redate + "</td>";
				
				if(result[i].rewriter == loginMemberId){
					output += "<td><button onclick='replyModifyForm_ajax(\"" + result[i].renum + "\")'>수정</button>";
					output += "<button onclick='replyDelete_ajax(\"" + result[i].renum + "\")'>삭제</button></td>";							
				} else {
					output += "<td></td>";
				}
				output += "</tr>"
				output += "<tr><td colspan='3'>" + result[i].recontents + "</td></tr>";
			}
		} 
		output += "</table>";
		$("#replyList_ajax").html(output);
	}

	// ajax로 댓글삭제
	function replyDelete_ajax(renum){
		console.log("replyDelete_ajax() 호출");
		console.log("삭제하고자 하는 댓글 번호 : " + renum);
		$.ajax({
			type : "get",
			url : "replyDelete_ajax",
			data : {"renum" : renum},
			success : function(result){
				console.log("result : " + result);
 				if (result == "OK") {
					console.log("댓글 삭제 성공");
					getReplyList();
					alert(renum + "번 댓글이 삭제되었습니다.");
				} else {
					console.log("댓글 삭제 실패");
					alert(renum + "번 댓글 삭제에 실패했습니다.");
				} 
			}
		});
	}
	
	// controller로 댓글삭제
	function replyDelete(renum, bno){
		console.log("replyDelete() 호출");
		console.log("renum : " + renum);
		console.log("bno : " + bno);
		location.href = "${pageContext.request.contextPath }/Board/replyDelete?renum=" + renum + "&bno=" + bno;
	}
	
	// Controller 댓글 수정폼 출력 (form 태그 숨김/출력 토글 기능으로)
	function replyModifyForm(modiRenumId, modiRecontentsId, btnObj){
		console.log( $("#"+modiRenumId) );
		//$("#"+modiRenumId).toggleClass("d_none");
		if ($(btnObj).text() == "수정"){
			$(btnObj).text("취소");
		} else {
			$(btnObj).text("수정");
		}
		
		console.log( $("."+modiRenumId) );
		$("."+modiRenumId).toggleClass("d_none");
		$("#"+modiRecontentsId).focus();		
		//textArea_Height();
	}
	
	// ajax로 댓글 수정폼
	function replyModifyForm_ajax(renum){
		var modicontents;
		console.log("수정할 댓글번호 : " + renum);
		var rebno = "${boardView.bno }";
		console.log("수정할 댓글의 글 번호 : " + rebno);
		$.ajax({
			type : "get",
			url : "replyList_ajax",
			data : {"bno" : rebno},
			dataType : "json",
			async : false,
			success : function(result){
				console.log("목록 조회 성공");
				$("#recontents_ajax").val("");
				modifyReplyList(result, renum);	
			}
		});
	}
	
	function replyModify_ajax(renum){
		console.log("renum : " + renum);
		var modiContents = $("#modiContents").val();
		console.log("modiContents : " + modiContents);
		$.ajax({
			type : "get",
			url : "replyModify_ajax",
			data : {"renum" : renum, "modiContents" : modiContents},
			success : function(result){
				console.log("result : " + result);
 				if (result == "OK") {
					console.log("댓글 수정 성공");
					getReplyList();
					alert(renum + "번 댓글이 수정되었습니다.");
				} else {
					console.log("댓글 수정 실패");
					alert(renum + "번 댓글 수정에 실패했습니다.");
				} 
			}
		});
	}
	
	// 댓글 목록과 수정 댓글 변경을 할 변수에 html 코드를 누적하는 함수
	function modifyReplyList(result, modiRenum){
		/*
		-- 처음 생각한 진행순서
		1. 수정 버튼 클릭
		2. 댓글목록 조회하는 ajax 진행 >> result 반환
		3. output 코드 작성 >> innerHTML로 입력
		4. 댓글수정 폼 출력
		5. 댓글 수정 (value 입력)
		6. 수정완료 버튼 클릭
		7. replyModify 메소드 호출 (이미 output은 들어갔고, 수정한 댓글의 value 변수로 받음)
		
		-- 고민 후 변경된 진행순서
		1. 수정 버튼 클릭
		2. 댓글목록 조회하는 ajax 진행 >> result 반환
		3. ouput 코드 작성 >> 입력 
			(이미 여기서 modicontents의 value를 검색하고 값을 넣은 채로 innerHTML 됨 >> innerTHML 되고 나서 값이 생기는 것))
		4. 댓글수정 폼 출력 (modicontents의 value는 undifined인 상태로 폼이 출력된 것)
		5. 수정완료 버튼 클릭
		*/
		
		var loginMemberId = '${sessionScope.loginId }';
		var boardWriter = '${boardView.bwriter }';
		
		var output = "<table border='1' width='80%'>";
		for (var i=0; result.length > i; i++) {
			if (result[i].renum == modiRenum){
				output += "<tr>";
				output += "<td>" + result[i].rewriter + "</td>";
				output += "<td>" + result[i].redate + "</td>";

				output += "<td><button onclick='replyModify_ajax(\"" + result[i].renum + "\")'>수정완료</button></td>";
				
				output += "</tr>";
				output += "<tr><td colspan='3'><textarea id='modiContents'>" + result[i].recontents + "</textarea></td></tr>";
				// html은 먼저 다 들어가버리고, 해당 value들은 아직 들어오지 않은 상태일수도. 그래서 해당 value를 불러도 html에서는 조회를 못하는 것
			} else if (result[i].restate == 1){
				if (result[i].rewriter == loginMemberId || boardWriter == loginMemberId){
					output += "<tr>";
					output += "<td>" + result[i].rewriter + "</td>";
					output += "<td>" + result[i].redate + "</td>";
					
					if(result[i].rewriter == loginMemberId){
						output += "<td><button onclick='replyModifyForm_ajax(\"" + result[i].renum + "\")'>수정</button>";
						output += "<button onclick='replyDelete_ajax(\"" + result[i].renum + "\")'>삭제</button></td>";					
					} else {
						output += "<td></td>";
					}
					
					
					output += "</tr>"
					output += "<tr><td colspan='3'>[비공개 댓글] " + result[i].recontents + "</td></tr>";
				} else {
					output += "<tr><td colspan='3'>[비공개 댓글입니다.]</td></tr>";						
				}
			} else {
				output += "<tr>";
				output += "<td>" + result[i].rewriter + "</td>";
				output += "<td>" + result[i].redate + "</td>";
				
				if(result[i].rewriter == loginMemberId){
					output += "<td><button onclick='replyModifyForm_ajax(\"" + result[i].renum + "\")'>수정</button>";
					output += "<button onclick='replyDelete_ajax(\"" + result[i].renum + "\")'>삭제</button></td>";							
				} else {
					output += "<td></td>";
				}
				output += "</tr>"
				output += "<tr><td colspan='3'>" + result[i].recontents + "</td></tr>";
			}
		} 
		output += "</table>";
		$("#replyList_ajax").html(output);
		
	}
		
</script>

</html>