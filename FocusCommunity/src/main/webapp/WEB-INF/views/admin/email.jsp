<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - MultiPartEmail 전송하기</title>
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
									<h3 class="panel-title">관리자 화면 - MultiPartEmail 전송하기</h3>
								</div>
							</div>
							<form action="/admin/multipart-mail" method="post" enctype="multipart/form-data">
								받는 사람 : <input type="text" class="form-control" name="receiver" placeholder="여러명 입력시 aa@a.com, bb@b.com, ..."><br>
								메일 제목 : <input type="text" class="form-control"name="subject"><br>
								메일 내용 : <textArea name="msg" class="summernote"></textArea><br>
								첨부 파일 : <input type="file" name="file" class="form-control"><br>
								<div class="text-right">
									<button class="btn btn-success btn-sl-sm mb-5" type="submit"><i class="bx bx-highlight"></i> 메일 전송</button>
								</div>
							</form>
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
</body>
</html>