<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" class="h-100">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 로그인</title>
	<!-- Top Menu files -->
	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
</head>

<body class="h-100">
	<!--**********************************
		content start
	***********************************-->
    <div class="authincation h-100">
        <div class="container-fluid h-100">
            <div class="row justify-content-center h-100 align-items-center">
                <div class="col-md-6">
                    <div class="authincation-content">
                        <div class="row no-gutters">
                            <div class="col-xl-12">
                                <div class="auth-form">
                                    <h4 class="text-center mb-4">로그인</h4>
                                    <form action="/member/login" method="post">
                                        <div class="form-group">
                                            <label><strong>아이디</strong></label>
                                            <input type="text" class="form-control" name="cusid">
                                        </div>
                                        <div class="form-group">
                                            <label><strong>Password</strong></label>
                                            <input type="password" class="form-control" name="paswd">
                                        </div>
                                        <div class="form-row d-flex justify-content-between mt-4 mb-2">
                                            <div class="form-group">
                                                <div class="form-check ml-2">
                                                    <input class="form-check-input" type="checkbox" id="basic_checkbox_1" name="rememberMe">
                                                    <label class="form-check-label" for="basic_checkbox_1">로그인 상태 유지</label>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <a href="/member/pwFind">비밀번호를 잊으셨나요?</a>
                                            </div>
                                        </div>
                                        <div class="text-center">
                                            <button type="submit" class="btn btn-primary btn-block">로그인</button>
                                        </div>
                                    </form>
                                    <div class="new-account mt-3">
                                        <p>회원이 아니십니까? <a class="text-primary" href="/member/join">회원가입</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	<!--**********************************
		content end
	***********************************-->
	
	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
</body>
</html>