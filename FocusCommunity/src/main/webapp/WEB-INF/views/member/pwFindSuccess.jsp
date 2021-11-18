<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko" class="h-100">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 비밀번호찾기</title>
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
                                    <h4 class="text-center mb-4">비밀번호찾기</h4>
									<div class="form-group">
                                    	<label><strong>아이디</strong></label>
                                        <input type="text" name="cusid" id="cusid" value="${cususr.cusid}" class="form-control" readonly="readonly">
									</div>
									<div class="form-group">
                                    	<label><strong>비밀번호</strong></label>
                                        <input type="text" name="paswd" id="paswd" value="${cususr.paswd}" class="form-control" readonly="readonly">
									</div>
                                    <div class="text-center">
                                    	<button type="button" class="btn btn-dark btn-block btn" onclick="location.href='/'">돌아가기</button>
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