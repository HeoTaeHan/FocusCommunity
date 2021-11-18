<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
  	<meta content="width=device-width, initial-scale=1.0" name="viewport">

  	<title>Focus - 게시글 쓰기</title>

	<jsp:include page="/WEB-INF/views/include/head.jsp" /> 
	<style>
		span.file-delete {
			background-color: yellow;
			color: red;
			font-weight: bold;
			cursor: pointer;
			border: 1px solid black;
			border-radius: 5px;
			padding: 2px;
			margin-left: 10px;
			display: inline-block;
		}
		
	</style>
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
       	Header start
   	***********************************-->
   
   	<!--**********************************
       	Header end ti-comment-alt
   	***********************************-->

   	<!--**********************************
       	Sidebar start
   	***********************************-->
	<jsp:include page="/WEB-INF/views/include/left.jsp" />
	<!-- ======= Top Bar ======= -->
<div class="content-body">
		<div class="container-fluid">
			<div class="row">
				<div class="col-lg-12">
					<div class="card">
						<div class="card-header">
							<h4 class="card-title">새글 등록</h4>
						</div>
						<form id="frm" enctype="multipart/form-data">
							<div class="card-body">
								<div class="row">
									<div class="form-group">
										<input type="hidden" name="cusid" id="id" value="${ sessionid }">
									</div>
									<div class="form-group">
										<input type="text" class="form-control" id="title" class="validate" name="csbjt" placeholder="제목을 입력해주세요.">
									</div>
									<div class="form-group note-editable">
										<textarea id="content" class="summernote form-control" name="ccont" rows="25" style="width:100%; resize: none;" placeholder="내용을 입력해주세요." ></textarea>
									</div>
									<div id="fileBox">
										<div>
											<input type="file" name="file"  class="form-control drop-zone">
										</div>
									</div>
									<div id="uploadResult">
										<ul>
										
										</ul>
									</div>
								</div>
								<br><br><br>
								<div class="d-flex justify-content-center">
									<button type="button" id="btnWrite" class="btn btn-primary btn-left btn-sl-sm mb-5">
	                                	<i class="bx bx-highlight"></i> 글쓰기
									</button>
								</div>								
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- ======= Footer ======= -->
	<jsp:include page="/WEB-INF/views/include/bottom.jsp" />
	<!-- End Footer -->

	<!-- Vendor JS Files -->
	<jsp:include page="/WEB-INF/views/include/commonJs.jsp" />
	<script src="/resources/js/jquery-3.6.0.js"></script>
	<script src="/resources/js/jquery.serializeObject.min.js"></script>
	<script>
	
		var fileCount = 1; // 파일 입력요소 갯수
		var cloneObj = $('div#fileBox').clone();
		
		// 글쓰기 버튼 클릭했을 때
		$('#btnWrite').on('click', function () {
			
			var form = $('form#frm')[0];  // jQuery 객체에서 순수 form 객체의 참조를 [0] 인덱스로 꺼냄
			
			var formData = new FormData(form); // 쿼리스트링으로 넘겨줌
			
			$.ajax({
				url: '/api/boards',
				//enctype: 'multipart/form-data',
				method: 'POST',
				data: formData,
				processData: false, // 파일전송시 false 설정 필수!
				contentType: false, // 파일전송시 false 설정 필수!
				success: function (data) {
					// 화면에 기존의 대기상태 이미지 없애기...
					
					swal('성공','새로운 글쓰기 성공!','success').then((result) => {
						$('form#frm')[0].reset();  // form.reset();
						$('div#fileBox').html(cloneObj.html());
						showUploadedFile(data.filename);
					});
				} // success
			});
			
			// 화면에 대기상태 이미지 나타나기...
			
		});
		
		
		
		function showUploadedFile(attach) {
			var str = '';
			str += `
				<li>\${attach}</li>
			`;
			
			$('div#uploadResult > ul').append(str);
			
		} // showUploadedFile
	</script>
</body>
</html>