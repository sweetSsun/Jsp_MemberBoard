<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div class="navigation">
        <ul>
            <li class="leftFloat"><a href="${pageContext.request.contextPath }/MainPage.jsp">메인페이지</a></li>
            <li class="leftFloat"><a href="${pageContext.request.contextPath }/Board/boardList">게시판</a></li>
            <li class="leftFloat"><a href="${pageContext.request.contextPath }/Board/boardListPaging">게시판(paging)</a></li>
            
            <c:choose>
            <c:when test="${sessionScope.loginId == null }">
    	        <li class="rightFloat"><a href="${pageContext.request.contextPath }/Member/MemberLoginForm.jsp">로그인</a></li>
	            <li class="rightFloat"><a href="${pageContext.request.contextPath }/Member/MemberJoinForm.jsp">회원가입</a></li>
            </c:when>
            <c:otherwise>
    	        <li class="rightFloat"><a href="${pageContext.request.contextPath }/Member/memberLogout">로그아웃</a></li>    
	            <li class="rightFloat"><a href="${pageContext.request.contextPath }/Member/memberInfo?mid=${sessionScope.loginId }">내정보확인</a></li>
	            <%-- <li><a href="${pageContext.request.contextPath }/Member/memberInfo">내정보확인</a></li> --%>
            </c:otherwise>
            </c:choose>            
        </ul>
    </div>
    