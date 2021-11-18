<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 문자열 →  Date 객체 변환  -->
<fmt:parseDate value="${cususr.birth}" pattern="yyyymmdd" var="dateBirth"/>
<!-- Date 객체 → 문자열 변환  -->
<fmt:formatDate value="${pageScope.dateBirth}" pattern="yyyy-mm-dd" var="strBirth" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 회원정보 수정</title>

	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
</head>

<body>
	<!-- register start -->
	<div class="authincation h-100">
        <div class="container-fluid h-100">
            <div class="row justify-content-center h-100 align-items-center">
                <div class="col-md-6">
                    <div class="authincation-content">
                        <div class="row no-gutters">
                            <div class="col-xl-12">
                                <div class="auth-form">
                                    <h4 class="text-center mb-4">회원정보 수정</h4>
                                    <form action="/member/modify"  name="user" method="post">
                                    	<div class="form-group">
                                    		<label><strong>아이디</strong></label>
                                    		<input type="text" class="form-control" placeholder="아이디" name="cusid" id="cusid" value="${cususr.cusid}" required="required" readonly="readonly">
                                    		<p class="lead" id="pId"></p>
                                    	</div>
                                        <div class="form-group">
                                            <label><strong>비밀번호</strong></label>
                                            <input type="password" class="form-control" placeholder="비밀번호" name="paswd" id="paswd" value="${cususr.paswd }" required="required">
                                            <p class="lead" id="pPw"></p>
                                        </div>
                                        <div class="form-group">
                                        	 <label><strong>비밀번호 확인</strong></label>
                                        	<input type="password" class="form-control" id="signin-password" name="Repaswd" value="${cususr.paswd }" onblur="passwordcheck()" placeholder="비밀번호확인" required="required">
											<p class="lead" id="pRePw"></p>
                                        </div>
                                        <div class="form-group">
                                            <label><strong>이름</strong></label>
                                            <input type="text" class="form-control" placeholder="이름" name="cname" value="${cususr.cname}">
                                        </div>
                                        <div class="form-group">
                                            <label><strong>생년월일</strong></label>
                                            <input type="Date" class="form-control" placeholder="생년월일" name="birth" value="${strBirth}">
                                        </div>
                                        <div class="form-row">
                                            <label><strong>주소</strong></label>
                                            <div class="col-sm-2">
                                            	<input type="text" class="form-control" name="zcode" value="${cususr.zcode}" onkeydown="return isNumberKey2(event);">
                                            </div>
                                            <div class="col-sm-0">
                                            	<a href="#" id="zipcode" title="신주소" class="form-control"><i class="fa fa-search"></i></a>
                                            </div>
                                            <div class="col-sm-8">
                                            	<input type="text" class="form-control" name="adres" value="${cususr.adres}">
                                            </div>
                                        </div>
                                        <br/>
                                        <div class="form-row">
                                        	<label><strong>주소상세</strong></label>
                                        	<div class="col-sm-8">
                                        		<input type="text" class="form-control" name="adr02" value="${cususr.adr02}">
                                        	</div>
                                        </div>
                                        <br/>
                                        <div class="form-row">
                                            <label><strong>Email</strong></label>
                                            <div class="col-sm-4">
                                            	<input type="text" class="form-control" name="email1" id="email1" value="${email1}" onblur="emailCheckFunction();">
                                            </div>
                                            <div class="col-sm-0">
                                            	<div class="form-control">@</div>
                                            </div>
                                            <div class="col-sm-4">
												<input type="text" class="form-control" name="email2" id="email2" value="${email2}" onblur="emailCheckFunction();" >
                                            </div>
                                            <div class="col-sm-2">
												<select name="email3" class="form-control" onchange="mailchanges()" onblur="emailCheckFunction();">
													<option value="">직접입력</option>
													<option value="naver.com">naver.com</option>
													<option value="hanmail.com">hanmail.com</option>
													<option value="daum.net">daum.net</option>
													<option value="nate.com">nate.com</option>
													<option value="gmail.com">gmail.com</option>
													<option value="hotmail.com">hotmail.com</option>
												</select>
											</div>
											<p class="lead" id="pEmail"></p>
                                        </div>
                                        <br/>
                                       	<div class="text-center mt-4">
                                        	<button type="submit" class="btn btn-primary btn-block btn">정보수정</button>
										</div>
										<div class="text-center mt-4">
											<button type="button" class="btn btn-danger btn-block btn" id="btnRemove">회원정보 삭제</button>
										</div>
										<div class="text-center mt-4">
                                        	<button type="button" class="btn btn-dark btn-block btn" onclick="location.href='/'">돌아가기</button>
										</div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
	</div>
	<!-- register end -->

	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
	<script type="text/javascript">
		function isNumberKey2(evt) {
			var charCode = (evt.which) ? evt.which : evt.keyCode;
			if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
				return false;
			}
	
			var value_tmp = evt.srcElement.value;
		
	 		// 소수점(.)이 두번 이상 나오지 못하게
	    	var pattern1 = /^\d*[.]\d*$/; // 현재 value값에 소수점(.) 이 있으면 . 입력불가
	    	if (pattern1.test(value_tmp)) {
	        	if (charCode == 46) {
	            	return false;
	        	}
	    	}
	  		return true;
		}
	 
		function idCheckFunction(){ 
			var cusid = $("#cusid").val(); 
			
			
			$.ajax({ 
				type : 'POST', 
				url : '/BoardIdCheckServlet', 
				data : {cusid : cusid}, 
				success : function(result){ 
					if(result == 1){ 
						$("#pId").css("color","red");
						$("#pId").html("사용중인 아이디입니다. 다른 아이디를 입력해 주세요"); 
					} else {
						if(cusid == ''){
							$("#pId").html("");
						} else {
							$("#pId").css("color","green");
							$("#pId").html("사용할 수 있는 아이디입니다.");
						}
					} 
					
					
				} 
			}) 
		}
		
		function emailCheckFunction(){ 
			var email1 = $("#email1").val();
			var email2 = $("#email2").val();
			var email  = email1 + "@" + email2; 
			
			
			$.ajax({ 
				type : 'POST', 
				url : '/BoardEmailCheckServlet', 
				data : {email : email}, 
				success : function(result){ 
					if(result == 1){ 
						$("#pEmail").css("color","red");
						$("#pEmail").html("사용중인 이메일입니다. 다른 이메일을 입력해 주세요"); 
					} else {
						if(email1 == ''){
							$("#pEmail").html("");
						} else {
							$("#pEmail").css("color","green");
							$("#pEmail").html("사용할 수 있는 이메일입니다.");
						}
					} 
					
					
				} 
			}) 
		}
		
		function passwordcheck(){
			if(document.user.paswd.value != document.user.Repaswd.value){
				document.getElementById("pRePw").style.color = "red";
				document.getElementById("pRePw").innerText   = "비밀번호가 일치 하지 않습니다.";
			} else {
				document.getElementById("pRePw").style.color    = "green";
				document.getElementById("pRePw").style.fontSize = "20px";
				document.getElementById("pRePw").innerText      = "비밀번호가 일치 합니다!!";
			}
		}
	
		function mailchanges(){
			if(document.user.email3.value != ""){
				document.user.email2.value = document.user.email3.value; 
			} else {
				document.user.email2.value = "";
			}
		}
		
		$(function(){
			$("#zipcode").click(function(){
			    new daum.Postcode({
			        oncomplete: function(data) {
			            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
			            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
			            $("input[name=zcode]").val(data.zonecode);
			            if(data.userLanguageType == 'K' && data.userSelectedType == 'R'){
				            if(data.buildingName != ''){
								$("input[name=adres]").val(data.address);
								$("input[name=adr02]").val("("+data.buildingName+")");
				            } else {
				            	$("input[name=adres]").val(data.address);
				            }
			            } else if(data.userLanguageType == 'E' && data.userSelectedType == 'R') {
			            	$("input[name=adres]").val(data.roadAddressEnglish);
			            } else if(data.userLanguageType == 'K' && data.userSelectedType == 'J') {
			            	$("input[name=adres]").val(data.jibunAddress);
			            } else if(data.userLanguageType == 'E' && data.userSelectedType == 'J') {
			            	$("input[name=adres]").val(data.jibunAddressEnglish);
			            }
			        }
			    }).open()
		    });
	    });
		
		$('button#btnRemove').on('click', function(){
			swal({ title: "회원정보삭제", 
				text: "정말 삭제하시겠습니까?", 
				type: "warning", 
				showCancelButton: !0, 
				confirmButtonColor: "#DD6B55", 
				confirmButtonText: "예", 
				cancelButtonText: '아니요',
				closeOnConfirm: !1
			}).then((result) => {
				if (result.value == true) {
					location.href="/member/remove?cusid=${cususr.cusid}";
				}
			});
		});
	</script>
</body>
</html>