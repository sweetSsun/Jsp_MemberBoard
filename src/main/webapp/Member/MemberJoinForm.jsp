<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원가입</title>
<link rel="stylesheet"	href="${pageContext.request.contextPath }/CSS/main.css">
<link rel="stylesheet"	href="${pageContext.request.contextPath }/CSS/joinForm.css">
<!-- 아이콘 -->
<script src="https://kit.fontawesome.com/9125416ae4.js"	crossorigin="anonymous"></script>
<!-- jQuery -->
<script	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"	integrity="sha512-894YE6QWD5I59HgZOGReFYm4dnWc1Qt5NtvYSaNcOP+u1T9qYdvdihz0PPSiiqn/+/3e7Jo4EaG7TubfWGUrMQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</head>

<body>
	<!-- Header 시작 -->
	<%@ include file="../includes/Header.jsp"%>
	<!-- Header 끝 -->
	<!-- Navigation 시작 -->
	<%@ include file="../includes/Navigation.jsp"%>
	<!-- Navigation 끝 -->
	<div class="contents">
		<h2>MemberJoinForm.jsp - 컨텐츠 영역</h2>
		<h2>세션값 확인 : ${sessionScope.loginId }</h2>
		<div class="content">
			<!-- 회원가입 양식 시작 -->
			<form action="memberJoin" method="post" onsubmit="return joinFormCheck()">
				<!-- submit 버튼을 누를 때 true이면 실행, false이면 실행X -->
				<table>
					<tr>
						<th colspan="3">회원가입</th>
					</tr>
					<tr>
						<td><i class="fa-regular fa-address-card"></i></td>
						<th>아이디</th>
						<td><input type="text" name="memberId" id="userId"
							placeholder="3~10자리" onfocusout="checkId()"> <span
							id="idCheckMsg" class="font10"></span></td>
					</tr>
					<tr>
						<td><i class="fa-regular fa-address-card"></i></td>
						<th>비밀번호</th>
						<td><input type="password" name="memberPw" id="userPw"
							placeholder="4~10자리" onfocusout="checkPw()"> <span
							id="pwCheckMsg" class="font10"></span></td>
					</tr>
					<tr>
						<td><i class="fa-regular fa-address-card"></i></td>
						<th>비밀번호 확인</th>
						<td><input type="password" id="pwConfirm"> <span
							id="pwConfirmMsg" class="font10"></span></td>
					</tr>
					<tr>
						<td><i class="fa-regular fa-address-card"></i></td>
						<th>이름</th>
						<td><input type="text" name="memberName" id="userName"></td>
						<!-- required : 입력했는지 확인해주는 속성
                        required만 단독으로 써도 되고 required="required" 라고 써도 작동 (속성값이 required 뿐) -->
					</tr>
					<tr>
						<td><i class="fa-regular fa-address-card"></i></td>
						<th>생년월일</th>
						<td><input type="date" name="memberBirth" id="userBirth" ></td>
					</tr>
					<tr>
						<td><i class="fa-regular fa-address-card"></i></td>
						<th>이메일</th>
						<td><input type="text" name="memberEmailId" id="emailId"> 
							@ <input type="text" name="memberEmailDomain" id="emailDomain" > 
							<select id="domainSelect">
								<option value="">직접입력</option>
								<option value="naver.com">네이버</option>
								<option value="daum.net">다음</option>
								<option value="gmail.com">구글</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><i class="fa-regular fa-address-card"></i></td>
						<th>주소</th>
						<td>
							<input type="text" id="sample6_postcode" name="memberPostCode" placeholder="우편번호" > 
							<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
							<input type="text"	id="sample6_address" name="memberAddress" placeholder="주소"><br>
							<input type="text"	id="sample6_detailAddress" name="memberDetailAddress"	placeholder="상세주소" >
							<input type="text" id="sample6_extraAddress" name="memberExtraAddress" placeholder="참고항목">
						</td>
					</tr>
					<tr>
						<th colspan="3"><input type="submit" class="subBtn1" value="회원가입"></th>
					</tr>
				</table>
			</form>

		</div>
	</div>
	<!-- Footer 시작 -->
	<%@ include file="../includes/Footer.jsp"%>
	<!-- Footer 끝 -->
</body>
<script type="text/javascript">
	var formIdCheck = false;
	var formPwCheck = false;
	// 아이디 규격 확인
	function checkId(){
		var inputId = document.getElementById("userId").value;
		console.log(inputId);
		if (inputId.length == 0) {
			document.getElementById("idCheckMsg").innerText = "아이디를 입력해주세요.";
			document.getElementById("idCheckMsg").style.color = "red";
			formIdCheck = false;
		} else if (inputId.length < 3 || inputId.length > 10) {
			document.getElementById("idCheckMsg").innerText = "3~10자리로 입력해주세요.";
			document.getElementById("idCheckMsg").style.color = "red";
			formIdCheck = false;
		} else {
			// 아이디 중복 체크
			$.ajax({
				type : "get",					 // 어떻게
				url : "memberIdCheck", 			 // 어디로
				data : { "inputId" : inputId },  // 무엇을. JSON 형태. 왼: key / 오: value
//				dataType : 						 // 서버에서 return 받을 데이터의 형태
				success : function(result){ 	 // 통신이 성공하면 수행할 기능. result : 서버에서 보내준 데이터
					console.log(result);
					if(result == "OK"){
						$("#idCheckMsg").css("color","green").text("사용 가능합니다.")
						formIdCheck = true;				
					} else {
						$("#idCheckMsg").css("color","red").text("중복된 아이디입니다.")
						formIdCheck = false;				
					}					
				},
				error : function(){				 // 통신이 실패하면 수행할 기능 (없어도 됨)
					alert("서버 연결실패")
				}
			});
		}
	}
	// 비밀번호 규격 확인
	function checkPw(){
		var inputPw = $("#userPw").val();
		console.log(inputPw);
		if (inputPw.length == 0) {
			$("#pwCheckMsg").css("color","red").text("비밀번호를 입력해주세요.");
			formPwCheck = false;
		} else if (inputPw.length < 4 || inputPw.length > 10) {
			$("#pwCheckMsg").css("color","red").text("4~10자리로 입력해주세요.");
			formPwCheck = false;
		} else {
			$("#pwCheckMsg").css("color","green").text("사용 가능합니다.");
		}
	}
	
	$(document).ready( function() {
		// 비밀번호 일치 확인
		$("#pwConfirm").focusout(function() {
			var pwConfirm = $("#pwConfirm").val();
			var inputPw = $("#userPw").val();
			console.log(pwConfirm);
			if (pwConfirm != inputPw) {
				$("#pwConfirmMsg").css("color","red").text("불일치");
				formPwCheck = false;
			} else {
				$("#pwConfirmMsg").css("color","green").text("일치");
				if (inputPw.length < 4 || inputPw.length > 10) {
					formPwCheck = false;					
				} else {
					formPwCheck = true;
				}
			}		
		});
		
		// 이메일도메인 value 변경
		$("#domainSelect").change(function() {
			var selDomain = $("#domainSelect").val();
			$("#emailDomain").val(selDomain);
		});
	});
	
/* 	function domainChange(){
		var domain =  document.getElementById("domainSelect").value;
		console.log(domain);
		document.getElementById("emailDomain").value = domain;
	} */
	
</script>

<script type="text/javascript">
	// 회원가입 폼 확인
	function joinFormCheck(){
		console.log("joinFormCheck() 호출");
		/* 1. 아이디 */
		if(!formIdCheck) {
			alert("아이디를 확인해주세요.");
			$("#userId").focus();
			return false;
		}
		/* 2. 비밀번호 */
		if(!formPwCheck) {
			alert("비밀번호를 확인해주세요.");
			$("#userPw").focus();
			return false;
		}
		/* 3. 이름 입력 유무 */
		var userName = $("#userName").val();
		if (userName.length == 0 || userName.length > 10) {
			alert("이름을 확인해주세요.");
			$("#userName").focus();
			return false;
		}
		/* 4. 생년월일 입력 유무 */
		if ($("#userBirth").val().length == 0) {
			alert("생년월일을 입력해주세요.");
			$("#userBirth").focus();
			return false;
		}
		/* 5. 이메일 입력 유무 */
		if ($("#emailId").val().length == 0) {
			alert("이메일을 입력해주세요.");
			$("#emailId").focus();
			return false;
		}
		if ($("#emailDomain").val().length == 0) {
			alert("이메일도메인을 입력해주세요.");
			$("#emailDomain").focus();
			return false;
		}
		/* 6. 주소 입력 유무 */
		var userPostcode = $("#sample6_postcode").val();
		var userAddress = $("#sample6_address").val();
		var userDetailAddress = $("#sample6_detailAddress").val();
		if (userPostcode.length == 0 || userAddress.length == 0 || userDetailAddress.length == 0) {
			alert("주소를 확인해주세요."); 
			$("#sample6_address").focus();
			return false;
		}
	}
</script>

<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
</script>
</html>