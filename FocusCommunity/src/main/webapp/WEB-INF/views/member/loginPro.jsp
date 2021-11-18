<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 로그인</title>
	<!-- Top Menu files -->
	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
</head>

<body>
	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script>
		$(document).ready(function() {
			var str = '${str}';
			if(str == ''){
				location.href="/";					
			} else {
				sweetAlert('에러','${str}','error').then((result) => {
					history.back();
				});
			}
		});
	</script>
</body>
</html>