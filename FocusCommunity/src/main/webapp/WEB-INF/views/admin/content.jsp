<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 문자열 →  Date 객체 변환  -->
<fmt:parseDate value="${cususrVO.birth}" pattern="yyyymmdd" var="dateBirth"/>
<!-- Date 객체 → 문자열 변환  -->
<fmt:formatDate value="${pageScope.dateBirth}" pattern="yyyy-mm-dd" var="strBirth" />
<fmt:formatDate value="${pageScope.dateBirth}" pattern="hh:mm a" var="strBirth2" />
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 회원정보관리</title>
	<!-- Top Menu files -->
	<jsp:include page="/WEB-INF/views/include/head.jsp" />  
</head>

<body>
	<!--**********************************
    	Nav header start
	***********************************-->
	<jsp:include page="/WEB-INF/views/include/top.jsp" />
	<!--**********************************
	Nav header end
	***********************************-->

	<!--**********************************
		Sidebar start
	***********************************-->
	<jsp:include page="/WEB-INF/views/include/left.jsp" />
	<!--**********************************
		Sidebar end
	***********************************-->
	
	<!--**********************************
		content start
	***********************************-->
	<div class="content-body">
		<div class="container-fluid">
			<!-- row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-body">
                                <div class="email-box ml-0 ml-sm-4 ml-sm-0">
                                    <div class="row">
                                        <div class="col-12">
                                            <div class="right-box-padding">
                                                <div class="read-content">
                                                    <div class="media pt-3">
                                                        <div class="media-body">
                                                            <h5 class="text-primary">${cususrVO.cname}</h5>
                                                            <p class="mb-0">${strBirth}</p>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="media mb-4 mt-5">
                                                        <div class="media-body">
                                                        	<span class="pull-right">${strBirth2}</span>
                                                        </div>
                                                    </div>
                                                    <div class="read-content-body">
                                                    	<h5 class="mb-5">아이디 :  ${cususrVO.cusid}</h5>
                                                    	<h5 class="mb-5">비밀번호 :  ${cususrVO.paswd}</h5>
                                                    	<h5 class="mb-5">아이디 :  ${cususrVO.cusid}</h5>
                                                        <h5 class="mb-5">주소 :  [${cususrVO.zcode}] ${cususrVO.adres}</h5>
                                                        <h5 class="mb-5">주소상세 : ${cususrVO.adr02} </h5>
                                                        <h5 class="mb-5">이메일 : ${cususrVO.email} </h5>
                                                    </div>
                                                    <div class="text-right">
                                                    	<button class="btn btn-info btn-sl-sm mb-5" type="button" onclick="location.href='/admin/modify?pageNum=${pageNum}&keyword=${keyword}&type=${type}&cusid=${cususrVO.cusid}'">회원정보 수정</button>
                                                    	<button class="btn btn-danger btn-sl-sm mb-5" type="button" id="btnRemove">회원정보삭제</button>
                                                    	<button class="btn btn-dark btn-sl-sm mb-5" type="button" onclick="location.href='/admin/list?pageNum=${pageNum}&keyword=${keyword}&type=${type}#board'">글목록</button>
                                                    </div>
                                                </div>
                                            </div>
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
	
	<!-- ======= Footer ======= -->
	<jsp:include page="/WEB-INF/views/include/bottom.jsp" />
	<!-- End Footer -->

	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script>
		$('#btnRemove').on('click', function(){
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
					location.href = '/admin/remove?pageNum=${pageNum}&keyword=${keyword}&type=${type}&cusid=${cususrVO.cusid}';	
				}
			});
		});
	</script>
</body>
</html>