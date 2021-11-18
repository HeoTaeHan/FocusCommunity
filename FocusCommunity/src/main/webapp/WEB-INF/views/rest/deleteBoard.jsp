<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta content="width=device-width, initial-scale=1.0" name="viewport">
	<title>Focus - 게시글 삭제</title>
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
							<h4 class="card-title">글 삭제</h4>
						</div>
						<form id="frm" enctype="multipart/form-data">
							<div class="card-body">
								<input type="hidden" name="cusid" id="cusid" value="${sessionid}"/>
								<div class="row">
									<div class="form-group">
										<input class="form-control" type="number" name="csnum" id="bno" placeholder="글번호를 검색해주세요.">
									</div>
									<div class="form-group">
										<input class="form-control" type="text" name="csbjt" id="subject">
									</div>
									<div class="form-group note-editable">
										<textarea class="form-control" rows="25" style="width:100% resize: none;" name="ccont" id="content"></textarea>
									</div>
									<div class="form-group read-content-attachment" style="margin-left: 15px">
										<h6>
											<i class="fa fa-download mb-2"></i> 첨부파일
										</h6>
										<div id="oldFileBox">
												
										</div>
										<div id="newFileBox">
										
										</div>
									</div>
								</div>
								<br><br><br>
								<div class="d-flex justify-content-center">
									<button class="btn btn-info btn-left btn-sl-sm mb-5" type="button" id="btnDel">
	                                	<i class="bx bx-trash"></i> 글삭제
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
	<script>
	
		var fileCount = 0; // 파일 입력요소 갯수
		var fileIndex = 0;
		
		$('input#bno').on('change', function () {
			
			fileCount = 0;
			$('div#newFileBox').empty();
			$('div#oldFileBox').empty();
			
			var bno = $(this).val();
			
			$.ajax({
				url: '/api/boards/' + bno,
				method: 'GET',
				success: function (data) {
					
					showData(data);
				} // success
			});
		});
	
		function showData(obj) {
			
			// 게시글 정보 보이기
			var board = obj;
			
			$('input#id').val(board.cusid);
			$('input#subject').val(board.csbjt);
			$('textarea#content').val(board.ccont);
			
			// 첨부파일 정보 보이기
			
			
			var str = '';
			if(typeof board.uploadpath != 'undefined' && board.uploadpath != null){
				str += `
						<input type="hidden" name="oldfile" value="\${board.uuid}">
						<div class="form-group">
							<span class="filename">\${board.filename}</span>
						</div>
				
				`;
			}
			
			$('div#oldFileBox').html(str);
			
		} // showData
		
		// 글삭제 버튼 클릭했을 때
		$('#btnDel').on('click', function () {
			
			var bno = $('#bno').val();
			
			
			$.ajax({
				url: '/api/boards/' + bno,
				method: 'DELETE',
				success: function (data) {
					
					swal('성공', bno + '번 글 삭제 성공!','success').then((result) => {
						$('#bno').val('');
						$('form#frm')[0].reset();
						$('div#newFileBox').empty();
						$('div#oldFileBox').empty();
					});
				}
			});
		});
	</script>
</body>
</html>