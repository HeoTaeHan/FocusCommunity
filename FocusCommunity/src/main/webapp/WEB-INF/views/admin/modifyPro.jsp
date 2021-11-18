<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus -  회원정보 수정</title>
	<!-- Top Menu files -->
	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
</head>

<body>
	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script>
	$(document).ready(function() {
		swal('성공','회원정보수정 성공!','success').then((result) => {
			location.href="/admin/modify?pageNum=${pageNum}&keyword=${keyword}&type=${type}&cusid=${cusid}";
		});
	});
	
	</script>
</body>
</html>